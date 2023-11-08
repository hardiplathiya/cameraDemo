package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Preview implements Control {

    SURFACE(0),
    TEXTURE(1),
    GL_SURFACE(2);

    final static Preview DEFAULT = GL_SURFACE;

    private int value;

    Preview(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Preview fromValue(int value) {
        Preview[] list = Preview.values();
        for (Preview action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
