package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Engine implements Control {
    CAMERA1(0),
    CAMERA2(1);

    final static Engine DEFAULT = CAMERA1;

    private int value;

    Engine(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Engine fromValue(int value) {
        Engine[] list = Engine.values();
        for (Engine action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
