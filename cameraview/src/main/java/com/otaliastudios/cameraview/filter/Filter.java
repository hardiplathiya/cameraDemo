package com.otaliastudios.cameraview.filter;

import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;

import java.io.File;


public interface Filter {

    @NonNull
    String getVertexShader();

    @NonNull
    String getFragmentShader();

    void onCreate(int programHandle);

    void onDestroy();

    void draw(long timestampUs, @NonNull float[] transformMatrix);

    void setSize(int width, int height);

    @NonNull
    Filter copy();
}
