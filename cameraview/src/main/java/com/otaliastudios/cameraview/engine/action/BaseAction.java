package com.otaliastudios.cameraview.engine.action;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class BaseAction implements Action {

    private final List<ActionCallback> callbacks = new ArrayList<>();
    private int state;
    private ActionHolder holder;
    private boolean needsOnStart;

    @Override
    public final int getState() {
        return state;
    }

    @Override
    public final void start(@NonNull ActionHolder holder) {
        this.holder = holder;
        holder.addAction(this);
        if (holder.getLastResult(this) != null) {
            onStart(holder);
        } else {
            needsOnStart = true;
        }
    }

    @Override
    public final void abort(@NonNull ActionHolder holder) {
        holder.removeAction(this);
        if (!isCompleted()) {
            onAbort(holder);
            setState(STATE_COMPLETED);
        }
        needsOnStart = false;
    }
    
    @CallSuper
    protected void onStart(@NonNull ActionHolder holder) {
        this.holder = holder;
    }
    
    @SuppressWarnings("unused")
    protected void onAbort(@NonNull ActionHolder holder) {
        
    }

    @CallSuper
    @Override
    public void onCaptureStarted(@NonNull ActionHolder holder, @NonNull CaptureRequest request) {
        if (needsOnStart) {
            onStart(holder);
            needsOnStart = false;
        }
    }

    @Override
    public void onCaptureProgressed(@NonNull ActionHolder holder,
                                    @NonNull CaptureRequest request,
                                    @NonNull CaptureResult result) {

    }

    @Override
    public void onCaptureCompleted(@NonNull ActionHolder holder,
                                   @NonNull CaptureRequest request,
                                   @NonNull TotalCaptureResult result) {
        
    }

    protected final void setState(int newState) {
        if (newState != state) {
            state = newState;
            for (ActionCallback callback : callbacks) {
                callback.onActionStateChanged(this, state);
            }
            if (state == STATE_COMPLETED) {
                holder.removeAction(this);
                onCompleted(holder);
            }
        }
    }

    public boolean isCompleted() {
        return state == STATE_COMPLETED;
    }


    protected void onCompleted(@NonNull ActionHolder holder) {
        
    }


    @NonNull
    protected ActionHolder getHolder() {
        return holder;
    }

    @NonNull
    protected <T> T readCharacteristic(@NonNull CameraCharacteristics.Key<T> key,
                                       @NonNull T fallback) {
        T value = holder.getCharacteristics(this).get(key);
        return value == null ? fallback : value;
    }

    @Override
    public void addCallback(@NonNull ActionCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
            callback.onActionStateChanged(this, getState());
        }
    }

    @Override
    public void removeCallback(@NonNull ActionCallback callback) {
        callbacks.remove(callback);
    }
}
