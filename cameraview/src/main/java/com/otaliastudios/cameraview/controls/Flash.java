package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Flash implements Control {

    OFF(0),
    ON(1),
    AUTO(2),
    TORCH(3);

    static final Flash DEFAULT = OFF;

    private int value;

    Flash(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Flash fromValue(int value) {
        Flash[] list = Flash.values();
        for (Flash action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
