package com.otaliastudios.cameraview.frame;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.otaliastudios.cameraview.engine.offset.Angles;
import com.otaliastudios.cameraview.size.Size;

import java.util.concurrent.LinkedBlockingQueue;

public class ByteBufferFrameManager extends FrameManager<byte[]> {

    public interface BufferCallback {
        void onBufferAvailable(@NonNull byte[] buffer);
    }

    private final static int BUFFER_MODE_DISPATCH = 0;

    private final static int BUFFER_MODE_ENQUEUE = 1;

    private LinkedBlockingQueue<byte[]> mBufferQueue;
    private BufferCallback mBufferCallback;
    private final int mBufferMode;

    public ByteBufferFrameManager(int poolSize, @Nullable BufferCallback callback) {
        super(poolSize, byte[].class);
        if (callback != null) {
            mBufferCallback = callback;
            mBufferMode = BUFFER_MODE_DISPATCH;
        } else {
            mBufferQueue = new LinkedBlockingQueue<>(poolSize);
            mBufferMode = BUFFER_MODE_ENQUEUE;
        }
    }


    @Override
    public void setUp(int format, @NonNull Size size, @NonNull Angles angles) {
        super.setUp(format, size, angles);
        int bytes = getFrameBytes();
        for (int i = 0; i < getPoolSize(); i++) {
            if (mBufferMode == BUFFER_MODE_DISPATCH) {
                mBufferCallback.onBufferAvailable(new byte[bytes]);
            } else {
                mBufferQueue.offer(new byte[bytes]);
            }
        }
    }

    @Nullable
    public byte[] getBuffer() {
        if (mBufferMode != BUFFER_MODE_ENQUEUE) {
            throw new IllegalStateException("Can't call getBuffer() " +
                    "when not in BUFFER_MODE_ENQUEUE.");
        }
        return mBufferQueue.poll();
    }

    public void onBufferUnused(@NonNull byte[] buffer) {
        if (mBufferMode != BUFFER_MODE_ENQUEUE) {
            throw new IllegalStateException("Can't call onBufferUnused() " +
                    "when not in BUFFER_MODE_ENQUEUE.");
        }

        if (isSetUp()) {
            mBufferQueue.offer(buffer);
        } else {
            LOG.w("onBufferUnused: buffer was returned but we're not set up anymore.");
        }
    }

    @Override
    protected void onFrameDataReleased(@NonNull byte[] data, boolean recycled) {
        if (recycled && data.length == getFrameBytes()) {
            if (mBufferMode == BUFFER_MODE_DISPATCH) {
                mBufferCallback.onBufferAvailable(data);
            } else {
                mBufferQueue.offer(data);
            }
        }
    }

    @NonNull
    @Override
    protected byte[] onCloneFrameData(@NonNull byte[] data) {
        byte[] clone = new byte[data.length];
        System.arraycopy(data, 0, clone, 0, data.length);
        return clone;
    }


    @Override
    public void release() {
        super.release();
        if (mBufferMode == BUFFER_MODE_ENQUEUE) {
            mBufferQueue.clear();
        }
    }
}
