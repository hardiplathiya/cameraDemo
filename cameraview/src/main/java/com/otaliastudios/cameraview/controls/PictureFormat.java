package com.otaliastudios.cameraview.controls;


import androidx.annotation.NonNull;

import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

public enum PictureFormat implements Control {

    JPEG(0),
    DNG(1);

    static final PictureFormat DEFAULT = JPEG;

    private int value;

    PictureFormat(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static PictureFormat fromValue(int value) {
        PictureFormat[] list = PictureFormat.values();
        for (PictureFormat action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
