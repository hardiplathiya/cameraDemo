package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Audio implements Control {
    OFF(0),
    ON(1),
    MONO(2),
    STEREO(3);
    final static Audio DEFAULT = ON;
    private int value;

    Audio(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Audio fromValue(int value) {
        Audio[] list = Audio.values();
        for (Audio action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
