package com.otaliastudios.cameraview.gesture;


import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.filter.Filter;
import com.otaliastudios.cameraview.markers.AutoFocusMarker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum GestureAction {


    NONE(0, GestureType.ONE_SHOT),
    AUTO_FOCUS(1, GestureType.ONE_SHOT),

    TAKE_PICTURE(2, GestureType.ONE_SHOT),

    TAKE_PICTURE_SNAPSHOT(3, GestureType.ONE_SHOT),

    ZOOM(4, GestureType.CONTINUOUS),

    EXPOSURE_CORRECTION(5, GestureType.CONTINUOUS),

    FILTER_CONTROL_1(6, GestureType.CONTINUOUS),

    FILTER_CONTROL_2(7, GestureType.CONTINUOUS);

    final static GestureAction DEFAULT_PINCH = NONE;
    final static GestureAction DEFAULT_TAP = NONE;
    final static GestureAction DEFAULT_LONG_TAP = NONE;
    final static GestureAction DEFAULT_SCROLL_HORIZONTAL = NONE;
    final static GestureAction DEFAULT_SCROLL_VERTICAL = NONE;

    private int value;
    private GestureType type;

    GestureAction(int value, @NonNull GestureType type) {
        this.value = value;
        this.type = type;
    }

    int value() {
        return value;
    }

    @NonNull
    GestureType type() {
        return type;
    }

    @Nullable
    static GestureAction fromValue(int value) {
        GestureAction[] list = GestureAction.values();
        for (GestureAction action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return null;
    }
}
