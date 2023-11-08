package com.otaliastudios.cameraview.frame;


import android.graphics.ImageFormat;

import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.engine.offset.Angles;
import com.otaliastudios.cameraview.engine.offset.Axis;
import com.otaliastudios.cameraview.engine.offset.Reference;
import com.otaliastudios.cameraview.size.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class FrameManager<T> {

    private static final String TAG = FrameManager.class.getSimpleName();
    protected static final CameraLogger LOG = CameraLogger.create(TAG);

    private final int mPoolSize;
    private int mFrameBytes = -1;
    private Size mFrameSize = null;
    private int mFrameFormat = -1;
    private final Class<T> mFrameDataClass;
    private LinkedBlockingQueue<Frame> mFrameQueue;
    private Angles mAngles;


    protected FrameManager(int poolSize, @NonNull Class<T> dataClass) {
        mPoolSize = poolSize;
        mFrameDataClass = dataClass;
        mFrameQueue = new LinkedBlockingQueue<>(mPoolSize);
    }

    @SuppressWarnings("WeakerAccess")
    public final int getPoolSize() {
        return mPoolSize;
    }

    @SuppressWarnings("WeakerAccess")
    public final int getFrameBytes() {
        return mFrameBytes;
    }

    public final Class<T> getFrameDataClass() {
        return mFrameDataClass;
    }

    public void setUp(int format, @NonNull Size size, @NonNull Angles angles) {
        if (isSetUp()) {
            // TODO throw or just reconfigure?
        }
        mFrameSize = size;
        mFrameFormat = format;
        int bitsPerPixel = ImageFormat.getBitsPerPixel(format);
        long sizeInBits = size.getHeight() * size.getWidth() * bitsPerPixel;
        mFrameBytes = (int) Math.ceil(sizeInBits / 8.0d);
        for (int i = 0; i < getPoolSize(); i++) {
            mFrameQueue.offer(new Frame(this));
        }
        mAngles = angles;
    }

    protected boolean isSetUp() {
        return mFrameSize != null;
    }

    @Nullable
    public Frame getFrame(@NonNull T data, long time) {
        if (!isSetUp()) {
            throw new IllegalStateException("Can't call getFrame() after releasing " +
                    "or before setUp.");
        }

        Frame frame = mFrameQueue.poll();
        if (frame != null) {
            LOG.v("getFrame for time:", time, "RECYCLING.");
            int userRotation = mAngles.offset(Reference.SENSOR, Reference.OUTPUT,
                    Axis.RELATIVE_TO_SENSOR);
            int viewRotation = mAngles.offset(Reference.SENSOR, Reference.VIEW,
                    Axis.RELATIVE_TO_SENSOR);
            frame.setContent(data, time, userRotation, viewRotation, mFrameSize, mFrameFormat);
            return frame;
        } else {
            LOG.i("getFrame for time:", time, "NOT AVAILABLE.");
            onFrameDataReleased(data, false);
            return null;
        }
    }


    void onFrameReleased(@NonNull Frame frame, @NonNull T data) {
        if (!isSetUp()) return;
        // If frame queue is full, let's drop everything.
        // If frame queue accepts this frame, let's recycle the buffer as well.
        boolean recycled = mFrameQueue.offer(frame);
        onFrameDataReleased(data, recycled);
    }

    protected abstract void onFrameDataReleased(@NonNull T data, boolean recycled);

    @NonNull
    final T cloneFrameData(@NonNull T data) {
        return onCloneFrameData(data);
    }

    @NonNull
    protected abstract T onCloneFrameData(@NonNull T data);


    public void release() {
        if (!isSetUp()) {
            LOG.w("release called twice. Ignoring.");
            return;
        }

        LOG.i("release: Clearing the frame and buffer queue.");
        mFrameQueue.clear();
        mFrameBytes = -1;
        mFrameSize = null;
        mFrameFormat = -1;
        mAngles = null;
    }
}
