package com.otaliastudios.cameraview.engine;

import android.content.Context;
import android.graphics.PointF;
import android.location.Location;


import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.AudioCodec;
import com.otaliastudios.cameraview.controls.PictureFormat;
import com.otaliastudios.cameraview.engine.orchestrator.CameraOrchestrator;
import com.otaliastudios.cameraview.engine.orchestrator.CameraState;
import com.otaliastudios.cameraview.engine.orchestrator.CameraStateOrchestrator;
import com.otaliastudios.cameraview.metering.MeteringRegions;
import com.otaliastudios.cameraview.overlay.Overlay;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.engine.offset.Angles;
import com.otaliastudios.cameraview.engine.offset.Reference;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameManager;
import com.otaliastudios.cameraview.internal.WorkerHandler;
import com.otaliastudios.cameraview.picture.PictureRecorder;
import com.otaliastudios.cameraview.preview.CameraPreview;
import com.otaliastudios.cameraview.controls.Audio;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.controls.VideoCodec;
import com.otaliastudios.cameraview.controls.WhiteBalance;
import com.otaliastudios.cameraview.size.Size;
import com.otaliastudios.cameraview.size.SizeSelector;
import com.otaliastudios.cameraview.video.VideoRecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.io.File;
import java.io.FileDescriptor;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class CameraEngine implements
        CameraPreview.SurfaceCallback,
        PictureRecorder.PictureResultListener,
        VideoRecorder.VideoResultListener {

    public interface Callback {
        @NonNull Context getContext();
        void dispatchOnCameraOpened(@NonNull CameraOptions options);
        void dispatchOnCameraClosed();
        void onCameraPreviewStreamSizeChanged();
        void dispatchOnPictureShutter(boolean shouldPlaySound);
        void dispatchOnVideoTaken(@NonNull VideoResult.Stub stub);
        void dispatchOnPictureTaken(@NonNull PictureResult.Stub stub);
        void dispatchOnFocusStart(@Nullable Gesture trigger, @NonNull PointF where);
        void dispatchOnFocusEnd(@Nullable Gesture trigger, boolean success, @NonNull PointF where);
        void dispatchOnZoomChanged(final float newValue, @Nullable final PointF[] fingers);
        void dispatchOnExposureCorrectionChanged(float newValue, @NonNull float[] bounds,
                                                 @Nullable PointF[] fingers);
        void dispatchFrame(@NonNull Frame frame);
        void dispatchError(CameraException exception);
        void dispatchOnVideoRecordingStart();
        void dispatchOnVideoRecordingEnd();
    }

    protected static final String TAG = CameraEngine.class.getSimpleName();
    protected static final CameraLogger LOG = CameraLogger.create(TAG);
    private static final int DESTROY_RETRIES = 2;

    private WorkerHandler mHandler;
    @VisibleForTesting Handler mCrashHandler;
    private final Callback mCallback;
    private final CameraStateOrchestrator mOrchestrator
            = new CameraStateOrchestrator(new CameraOrchestrator.Callback() {
        @Override
        @NonNull
        public WorkerHandler getJobWorker(@NonNull String job) {
            return mHandler;
        }

        @Override
        public void handleJobException(@NonNull String job, @NonNull Exception exception) {
            handleException(exception, false);
        }
    });

    protected CameraEngine(@NonNull Callback callback) {
        mCallback = callback;
        mCrashHandler = new Handler(Looper.getMainLooper());
        recreateHandler(false);
    }

    @NonNull
    protected final Callback getCallback() {
        return mCallback;
    }

    @NonNull
    protected final CameraStateOrchestrator getOrchestrator() {
        return mOrchestrator;
    }

    private class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
            handleException(throwable, true);
        }
    }

    private static class NoOpExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
            LOG.w("EXCEPTION:", "In the NoOpExceptionHandler, probably while destroying.",
                    "Thread:", thread, "Error:", throwable);
        }
    }

    private void handleException(@NonNull final Throwable throwable,
                                 final boolean isUncaught) {

        if (isUncaught) {
            LOG.e("EXCEPTION:", "Handler thread is gone. Replacing.");
            recreateHandler(false);
        }

        LOG.e("EXCEPTION:", "Scheduling on the crash handler...");
        mCrashHandler.post(new Runnable() {
            @Override
            public void run() {
                if (throwable instanceof CameraException) {
                    CameraException exception = (CameraException) throwable;
                    if (exception.isUnrecoverable()) {
                        LOG.e("EXCEPTION:", "Got CameraException. " +
                                "Since it is unrecoverable, executing destroy(false).");
                        destroy(false);
                    }
                    LOG.e("EXCEPTION:", "Got CameraException. Dispatching to callback.");
                    mCallback.dispatchError(exception);
                } else {
                    LOG.e("EXCEPTION:", "Unexpected error! Executing destroy(true).");
                    destroy(true);
                    LOG.e("EXCEPTION:", "Unexpected error! Throwing.");
                    if (throwable instanceof RuntimeException) {
                        throw (RuntimeException) throwable;
                    } else {
                        throw new RuntimeException(throwable);
                    }
                }
            }
        });
    }

    private void recreateHandler(boolean resetOrchestrator) {
        if (mHandler != null) mHandler.destroy();
        mHandler = WorkerHandler.get("CameraViewEngine");
        mHandler.getThread().setUncaughtExceptionHandler(new CrashExceptionHandler());
        if (resetOrchestrator) mOrchestrator.reset();
    }

    @NonNull
    public final CameraState getState() {
        return mOrchestrator.getCurrentState();
    }

    @NonNull
    public final CameraState getTargetState() {
        return mOrchestrator.getTargetState();
    }

    public final boolean isChangingState() {
        return mOrchestrator.hasPendingStateChange();
    }
    public void destroy(boolean unrecoverably) {
        destroy(unrecoverably, 0);
    }

    private void destroy(boolean unrecoverably, int depth) {
        LOG.i("DESTROY:", "state:", getState(),
                "thread:", Thread.currentThread(),
                "depth:", depth,
                "unrecoverably:", unrecoverably);
        if (unrecoverably) {
            mHandler.getThread().setUncaughtExceptionHandler(new NoOpExceptionHandler());
        }
        final CountDownLatch latch = new CountDownLatch(1);
        stop(true).addOnCompleteListener(
                mHandler.getExecutor(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        latch.countDown();
                    }
                });
        try {
            boolean success = latch.await(6, TimeUnit.SECONDS);
            if (!success) {
                LOG.e("DESTROY: Could not destroy synchronously after 6 seconds.",
                        "Current thread:", Thread.currentThread(),
                        "Handler thread:", mHandler.getThread());
                depth++;
                if (depth < DESTROY_RETRIES) {
                    recreateHandler(true);
                    LOG.e("DESTROY: Trying again on thread:", mHandler.getThread());
                    destroy(unrecoverably, depth);
                } else {
                    LOG.w("DESTROY: Giving up because DESTROY_RETRIES was reached.");
                }
            }
        } catch (InterruptedException ignore) {}
    }

    @SuppressWarnings("WeakerAccess")
    public void restart() {
        LOG.i("RESTART:", "scheduled. State:", getState());
        stop(false);
        start();
    }

    @NonNull
    public Task<Void> start() {
        LOG.i("START:", "scheduled. State:", getState());
        Task<Void> engine = startEngine();
        startBind();
        startPreview();
        return engine;
    }

    @NonNull
    public Task<Void> stop(final boolean swallowExceptions) {
        LOG.i("STOP:", "scheduled. State:", getState());
        stopPreview(swallowExceptions);
        stopBind(swallowExceptions);
        return stopEngine(swallowExceptions);
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    @NonNull
    protected Task<Void> restartBind() {
        LOG.i("RESTART BIND:", "scheduled. State:", getState());
        stopPreview(false);
        stopBind(false);
        startBind();
        return startPreview();
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    @NonNull
    protected Task<Void> restartPreview() {
        LOG.i("RESTART PREVIEW:", "scheduled. State:", getState());
        stopPreview(false);
        return startPreview();
    }

    @NonNull
    @EngineThread
    private Task<Void> startEngine() {
        return mOrchestrator.scheduleStateChange(CameraState.OFF, CameraState.ENGINE,
                true,
                new Callable<Task<CameraOptions>>() {
            @Override
            public Task<CameraOptions> call() {
                if (!collectCameraInfo(getFacing())) {
                    LOG.e("onStartEngine:", "No camera available for facing", getFacing());
                    throw new CameraException(CameraException.REASON_NO_CAMERA);
                }
                return onStartEngine();
            }
        }).onSuccessTask(new SuccessContinuation<CameraOptions, Void>() {
            @NonNull
            @Override
            public Task<Void> then(@Nullable CameraOptions cameraOptions) {
                if (cameraOptions == null) throw new RuntimeException("Null options!");
                mCallback.dispatchOnCameraOpened(cameraOptions);
                return Tasks.forResult(null);
            }
        });
    }

    @NonNull
    @EngineThread
    private Task<Void> stopEngine(boolean swallowExceptions) {
        return mOrchestrator.scheduleStateChange(CameraState.ENGINE, CameraState.OFF,
                !swallowExceptions,
                new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() {
                return onStopEngine();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mCallback.dispatchOnCameraClosed();
            }
        });
    }

    @EngineThread
    protected abstract boolean collectCameraInfo(@NonNull Facing facing);

    @NonNull
    @EngineThread
    protected abstract Task<CameraOptions> onStartEngine();

    @NonNull
    @EngineThread
    protected abstract Task<Void> onStopEngine();

    @NonNull
    @EngineThread
    private Task<Void> startBind() {
        return mOrchestrator.scheduleStateChange(CameraState.ENGINE, CameraState.BIND,
                true,
                new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() {
                if (getPreview() != null && getPreview().hasSurface()) {
                    return onStartBind();
                } else {
                    return Tasks.forCanceled();
                }
            }
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    @NonNull
    @EngineThread
    private Task<Void> stopBind(boolean swallowExceptions) {
        return mOrchestrator.scheduleStateChange(CameraState.BIND, CameraState.ENGINE,
                !swallowExceptions,
                new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() {
                return onStopBind();
            }
        });
    }

    @NonNull
    @EngineThread
    protected abstract Task<Void> onStartBind();

    @NonNull
    @EngineThread
    protected abstract Task<Void> onStopBind();

    @NonNull
    @EngineThread
    private Task<Void> startPreview() {
        return mOrchestrator.scheduleStateChange(CameraState.BIND, CameraState.PREVIEW,
                true,
                new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() {
                return onStartPreview();
            }
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    @NonNull
    @EngineThread
    private Task<Void> stopPreview(boolean swallowExceptions) {
        return mOrchestrator.scheduleStateChange(CameraState.PREVIEW, CameraState.BIND,
                !swallowExceptions,
                new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() {
                return onStopPreview();
            }
        });
    }

    @NonNull
    @EngineThread
    protected abstract Task<Void> onStartPreview();

    @NonNull
    @EngineThread
    protected abstract Task<Void> onStopPreview();

    @SuppressWarnings("ConstantConditions")
    @Override
    public final void onSurfaceAvailable() {
        LOG.i("onSurfaceAvailable:", "Size is", getPreview().getSurfaceSize());
        startBind();
        startPreview();
    }

    @Override
    public final void onSurfaceDestroyed() {
        LOG.i("onSurfaceDestroyed");
        stopPreview(false);
        stopBind(false);
    }

    @NonNull
    public abstract Angles getAngles();

    @NonNull
    public abstract FrameManager getFrameManager();

    @Nullable
    public abstract CameraOptions getCameraOptions();

    @Nullable
    public abstract Size getPictureSize(@NonNull Reference reference);

    @Nullable
    public abstract Size getVideoSize(@NonNull Reference reference);

    @Nullable
    public abstract Size getPreviewStreamSize(@NonNull Reference reference);

    @Nullable
    public abstract Size getUncroppedSnapshotSize(@NonNull Reference reference);

    public abstract void setPreview(@NonNull CameraPreview cameraPreview);
    @Nullable public abstract CameraPreview getPreview();

    public abstract void setOverlay(@Nullable Overlay overlay);
    @Nullable public abstract Overlay getOverlay();

    public abstract void setPreviewStreamSizeSelector(@Nullable SizeSelector selector);
    @Nullable public abstract SizeSelector getPreviewStreamSizeSelector();

    public abstract void setPictureSizeSelector(@NonNull SizeSelector selector);
    @NonNull public abstract SizeSelector getPictureSizeSelector();

    public abstract void setVideoSizeSelector(@NonNull SizeSelector selector);
    @NonNull public abstract SizeSelector getVideoSizeSelector();

    public abstract void setVideoMaxSize(long videoMaxSizeBytes);
    public abstract long getVideoMaxSize();

    public abstract void setVideoMaxDuration(int videoMaxDurationMillis);
    public abstract int getVideoMaxDuration();

    public abstract void setVideoCodec(@NonNull VideoCodec codec);
    @NonNull public abstract VideoCodec getVideoCodec();

    public abstract void setVideoBitRate(int videoBitRate);
    public abstract int getVideoBitRate();

    public abstract void setAudioBitRate(int audioBitRate);
    public abstract int getAudioBitRate();

    public abstract void setAudioCodec(@NonNull AudioCodec codec);
    @NonNull public abstract AudioCodec getAudioCodec();

    public abstract void setSnapshotMaxWidth(int maxWidth);
    public abstract int getSnapshotMaxWidth();

    public abstract void setSnapshotMaxHeight(int maxHeight);
    public abstract int getSnapshotMaxHeight();

    public abstract void setFrameProcessingMaxWidth(int maxWidth);
    public abstract int getFrameProcessingMaxWidth();

    public abstract void setFrameProcessingMaxHeight(int maxHeight);
    public abstract int getFrameProcessingMaxHeight();

    public abstract void setFrameProcessingFormat(int format);
    public abstract int getFrameProcessingFormat();

    public abstract void setFrameProcessingPoolSize(int poolSize);
    public abstract int getFrameProcessingPoolSize();

    public abstract void setAutoFocusResetDelay(long delayMillis);
    public abstract long getAutoFocusResetDelay();

    public abstract void setFacing(final @NonNull Facing facing);
    @NonNull public abstract Facing getFacing();

    public abstract void setAudio(@NonNull Audio audio);
    @NonNull public abstract Audio getAudio();

    public abstract void setMode(@NonNull Mode mode);
    @NonNull public abstract Mode getMode();

    public abstract void setZoom(float zoom, @Nullable PointF[] points, boolean notify);
    public abstract float getZoomValue();

    public abstract void setExposureCorrection(float EVvalue, @NonNull float[] bounds,
                                               @Nullable PointF[] points, boolean notify);
    public abstract float getExposureCorrectionValue();

    public abstract void setFlash(@NonNull Flash flash);
    @NonNull public abstract Flash getFlash();

    public abstract void setWhiteBalance(@NonNull WhiteBalance whiteBalance);
    @NonNull public abstract WhiteBalance getWhiteBalance();

    public abstract void setHdr(@NonNull Hdr hdr);
    @NonNull public abstract Hdr getHdr();

    public abstract void setLocation(@Nullable Location location);
    @Nullable public abstract Location getLocation();

    public abstract void setPictureFormat(@NonNull PictureFormat pictureFormat);
    @NonNull public abstract PictureFormat getPictureFormat();

    public abstract void setPreviewFrameRateExact(boolean previewFrameRateExact);
    public abstract boolean getPreviewFrameRateExact();
    public abstract void setPreviewFrameRate(float previewFrameRate);
    public abstract float getPreviewFrameRate();

    public abstract void setHasFrameProcessors(boolean hasFrameProcessors);
    public abstract boolean hasFrameProcessors();

    public abstract void setPictureMetering(boolean enable);
    public abstract boolean getPictureMetering();

    public abstract void setPictureSnapshotMetering(boolean enable);
    public abstract boolean getPictureSnapshotMetering();

    public abstract void startAutoFocus(@Nullable Gesture gesture,
                                        @NonNull MeteringRegions regions,
                                        @NonNull PointF legacyPoint);

    public abstract void setPlaySounds(boolean playSounds);

    public abstract boolean isTakingPicture();
    public abstract void takePicture(@NonNull PictureResult.Stub stub);
    public abstract void takePictureSnapshot(final @NonNull PictureResult.Stub stub);

    public abstract boolean isTakingVideo();
    public abstract void takeVideo(@NonNull VideoResult.Stub stub,
                                   @Nullable File file,
                                   @Nullable FileDescriptor fileDescriptor);
    public abstract void takeVideoSnapshot(@NonNull VideoResult.Stub stub, @NonNull File file);
    public abstract void stopVideo();

}
