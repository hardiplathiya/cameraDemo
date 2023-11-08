package com.otaliastudios.cameraview.controls;


import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Grid implements Control {

    OFF(0),
    DRAW_3X3(1),
    DRAW_4X4(2),
    DRAW_PHI(3);

    static final Grid DEFAULT = OFF;

    private int value;

    Grid(int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @NonNull
    static Grid fromValue(int value) {
        Grid[] list = Grid.values();
        for (Grid action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return DEFAULT;
    }
}