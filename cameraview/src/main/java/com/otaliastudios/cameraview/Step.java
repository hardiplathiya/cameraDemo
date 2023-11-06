package com.otaliastudios.cameraview;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.otaliastudios.cameraview.CameraLogger;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class Step {
    static final int STATE_STARTED = 2;
    static final int STATE_STARTING = 1;
    static final int STATE_STOPPED = 0;
    static final int STATE_STOPPING = -1;
    private final Callback callback;
    private final String name;
    private int state = 0;
    private Task<Void> task = Tasks.forResult(null);
    private static final String TAG = Step.class.getSimpleName();
    private static final CameraLogger LOG = CameraLogger.create(TAG);

    /* loaded from: classes2.dex */
    public interface Callback {
        Executor getExecutor();

        void handleException(Exception exc);
    }

    public Step(String str, Callback callback) {
        this.name = str.toUpperCase();
        this.callback = callback;
    }

    public int getState() {
        return this.state;
    }

    void setState(int i) {
        this.state = i;
    }

    public String getStateName() {
        int i = this.state;
        if (i == -1) {
            return this.name + "_STATE_STOPPING";
        } else if (i == 0) {
            return this.name + "_STATE_STOPPED";
        } else if (i == 1) {
            return this.name + "_STATE_STARTING";
        } else if (i != 2) {
            return "null";
        } else {
            return this.name + "_STATE_STARTED";
        }
    }

    public boolean isStoppingOrStopped() {
        int i = this.state;
        return i == -1 || i == 0;
    }

    public boolean isStartedOrStarting() {
        int i = this.state;
        return i == 1 || i == 2;
    }

    public boolean isStarted() {
        return this.state == 2;
    }

    public Task<Void> getTask() {
        return this.task;
    }

    public Task<Void> doStart(boolean z, Callable<Task<Void>> callable) {
        return doStart(z, callable, null);
    }

    public Task<Void> doStart(final boolean z, final Callable<Task<Void>> callable, final Runnable runnable) {
        LOG.i(this.name, "doStart", "Called. Enqueuing.");
        this.task = this.task.continueWithTask(this.callback.getExecutor(), new Continuation<Void, Task<Void>>() { // from class: com.otaliastudios.cameraview.engine.Step.2
            @Override // com.google.android.gms.tasks.Continuation
            public Task<Void> then(Task<Void> task) throws Exception {
                Step.LOG.i(Step.this.name, "doStart", "About to start. Setting state to STARTING");
                Step.this.setState(1);
                return ((Task) callable.call()).addOnFailureListener(Step.this.callback.getExecutor(), new OnFailureListener() { // from class: com.otaliastudios.cameraview.engine.Step.2.1
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(Exception exc) {
                        Step.LOG.w(Step.this.name, "doStart", "Failed with error", exc, "Setting state to STOPPED");
                        Step.this.setState(0);
                        if (z) {
                            return;
                        }
                        Step.this.callback.handleException(exc);
                    }
                });
            }
        }).onSuccessTask(this.callback.getExecutor(), new SuccessContinuation<Void, Void>() { // from class: com.otaliastudios.cameraview.engine.Step.1
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public Task<Void> then(Void r4) {
                Step.LOG.i(Step.this.name, "doStart", "Succeeded! Setting state to STARTED");
                Step.this.setState(2);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                return Tasks.forResult(null);
            }
        });
        return this.task;
    }

    public Task<Void> doStop(boolean z, Callable<Task<Void>> callable) {
        return doStop(z, callable, null);
    }

    public Task<Void> doStop(final boolean z, final Callable<Task<Void>> callable, final Runnable runnable) {
        LOG.i(this.name, "doStop", "Called. Enqueuing.");
        this.task = this.task.continueWithTask(this.callback.getExecutor(), new Continuation<Void, Task<Void>>() { // from class: com.otaliastudios.cameraview.engine.Step.4
            @Override // com.google.android.gms.tasks.Continuation
            public Task<Void> then(Task<Void> task) throws Exception {
                Step.LOG.i(Step.this.name, "doStop", "About to stop. Setting state to STOPPING");
                Step.this.state = -1;
                return ((Task) callable.call()).addOnFailureListener(Step.this.callback.getExecutor(), new OnFailureListener() { // from class: com.otaliastudios.cameraview.engine.Step.4.1
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(Exception exc) {
                        Step.LOG.w(Step.this.name, "doStop", "Failed with error", exc, "Setting state to STOPPED");
                        Step.this.state = 0;
                        if (z) {
                            return;
                        }
                        Step.this.callback.handleException(exc);
                    }
                });
            }
        }).onSuccessTask(this.callback.getExecutor(), new SuccessContinuation<Void, Void>() { // from class: com.otaliastudios.cameraview.engine.Step.3
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public Task<Void> then(Void r5) {
                Step.LOG.i(Step.this.name, "doStop", "Succeeded! Setting state to STOPPED");
                Step.this.state = 0;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                return Tasks.forResult(null);
            }
        });
        return this.task;
    }
}
