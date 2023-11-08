package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum VideoCodec implements Control {

    DEVICE_DEFAULT(0),
    H_263(1),
    H_264(2);

    static final VideoCodec DEFAULT = DEVICE_DEFAULT;

    private int value;

    VideoCodec(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static VideoCodec fromValue(int value) {
        VideoCodec[] list = VideoCodec.values();
        for (VideoCodec action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
