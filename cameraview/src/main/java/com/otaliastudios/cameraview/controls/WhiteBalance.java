package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum WhiteBalance implements Control {

    AUTO(0),
    INCANDESCENT(1),
    FLUORESCENT(2),
    DAYLIGHT(3),
    CLOUDY(4);

    static final WhiteBalance DEFAULT = AUTO;

    private int value;

    WhiteBalance(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static WhiteBalance fromValue(int value) {
        WhiteBalance[] list = WhiteBalance.values();
        for (WhiteBalance action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}