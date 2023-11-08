package com.otaliastudios.cameraview.controls;


import android.content.Context;

import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Facing implements Control {
    BACK(0),
    FRONT(1);

    @NonNull
    static Facing DEFAULT(@Nullable Context context) {
        if (context == null) {
            return BACK;
        } else if (CameraUtils.hasCameraFacing(context, BACK)) {
            return BACK;
        } else if (CameraUtils.hasCameraFacing(context, FRONT)) {
            return FRONT;
        } else {
            return BACK;
        }
    }

    private int value;

    Facing(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @Nullable
    static Facing fromValue(int value) {
        Facing[] list = Facing.values();
        for (Facing action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return null;
    }
}
