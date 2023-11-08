package com.otaliastudios.cameraview.frame;

import android.annotation.SuppressLint;

import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.controls.Engine;
import com.otaliastudios.cameraview.size.Size;

import androidx.annotation.NonNull;


public class Frame {

    private final static String TAG = Frame.class.getSimpleName();
    private final static CameraLogger LOG = CameraLogger.create(TAG);

    private final FrameManager mManager;
    private final Class<?> mDataClass;

    private Object mData = null;
    private long mTime = -1;
    private long mLastTime = -1;
    private int mUserRotation = 0;
    private int mViewRotation = 0;
    private Size mSize = null;
    private int mFormat = -1;

    Frame(@NonNull FrameManager manager) {
        mManager = manager;
        mDataClass = manager.getFrameDataClass();
    }

    void setContent(@NonNull Object data, long time, int userRotation, int viewRotation,
                    @NonNull Size size, int format) {
        mData = data;
        mTime = time;
        mLastTime = time;
        mUserRotation = userRotation;
        mViewRotation = viewRotation;
        mSize = size;
        mFormat = format;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasContent() {
        return mData != null;
    }

    private void ensureHasContent() {
        if (!hasContent()) {
            LOG.e("Frame is dead! time:", mTime, "lastTime:", mLastTime);
            throw new RuntimeException("You should not access a released frame. " +
                    "If this frame was passed to a FrameProcessor, you can only use its contents " +
                    "synchronously, for the duration of the process() method.");
        }
    }


    @Override
    public boolean equals(Object obj) {
        // We want a super fast implementation here, do not compare arrays.
        return obj instanceof Frame && ((Frame) obj).mTime == mTime;
    }

    @SuppressLint("NewApi")
    @NonNull
    public Frame freeze() {
        ensureHasContent();
        Frame other = new Frame(mManager);
        //noinspection unchecked
        Object data = mManager.cloneFrameData(getData());
        other.setContent(data, mTime, mUserRotation, mViewRotation, mSize, mFormat);
        return other;
    }


    public void release() {
        if (!hasContent()) return;
        LOG.v("Frame with time", mTime, "is being released.");
        Object data = mData;
        mData = null;
        mUserRotation = 0;
        mViewRotation = 0;
        mTime = -1;
        mSize = null;
        mFormat = -1;
        mManager.onFrameReleased(this, data);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T> T getData() {
        ensureHasContent();
        return (T) mData;
    }

    @NonNull
    public Class<?> getDataClass() {
        return mDataClass;
    }


    public long getTime() {
        ensureHasContent();
        return mTime;
    }


    @Deprecated
    public int getRotation() {
        return getRotationToUser();
    }

    public int getRotationToUser() {
        ensureHasContent();
        return mUserRotation;
    }


    public int getRotationToView() {
        ensureHasContent();
        return mViewRotation;
    }


    @NonNull
    public Size getSize() {
        ensureHasContent();
        return mSize;
    }


    public int getFormat() {
        ensureHasContent();
        return mFormat;
    }
}
