package com.otaliastudios.cameraview.frame;

import com.otaliastudios.cameraview.CameraView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;


public interface FrameProcessor {

    @WorkerThread
    void process(@NonNull Frame frame);
}
