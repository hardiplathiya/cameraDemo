package com.otaliastudios.cameraview.controls;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.otaliastudios.cameraview.CameraView;

public enum AudioCodec implements Control {

    DEVICE_DEFAULT(0),
    AAC(1),
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    HE_AAC(2),
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    AAC_ELD(3);

    static final AudioCodec DEFAULT = DEVICE_DEFAULT;

    private int value;

    AudioCodec(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static AudioCodec fromValue(int value) {
        AudioCodec[] list = AudioCodec.values();
        for (AudioCodec action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}
