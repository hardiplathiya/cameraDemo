package com.otaliastudios.cameraview.engine.action;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Arrays;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class Actions {

    @NonNull
    public static BaseAction together(@NonNull BaseAction... actions) {
        return new TogetherAction(Arrays.asList(actions));
    }

    @NonNull
    public static BaseAction sequence(@NonNull BaseAction... actions) {
        return new SequenceAction(Arrays.asList(actions));
    }
    @NonNull
    public static BaseAction timeout(long timeoutMillis, @NonNull BaseAction action) {
        return new TimeoutAction(timeoutMillis, action);
    }

}
