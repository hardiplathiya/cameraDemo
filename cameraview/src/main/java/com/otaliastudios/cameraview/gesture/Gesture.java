package com.otaliastudios.cameraview.gesture;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;

public enum Gesture {

    PINCH(GestureType.CONTINUOUS),

    TAP(GestureType.ONE_SHOT),

    LONG_TAP(GestureType.ONE_SHOT),

    SCROLL_HORIZONTAL(GestureType.CONTINUOUS),

    SCROLL_VERTICAL(GestureType.CONTINUOUS);

    Gesture(@NonNull GestureType type) {
        this.type = type;
    }

    private GestureType type;

    public boolean isAssignableTo(@NonNull GestureAction action) {
        return action == GestureAction.NONE || action.type() == type;
    }

}
