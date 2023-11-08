package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public enum Mode implements Control {

    PICTURE(0),
    VIDEO(1);
    static final Mode DEFAULT = PICTURE;

    private int value;

    Mode(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Mode fromValue(int value) {
        Mode[] list = Mode.values();
        for (Mode action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
