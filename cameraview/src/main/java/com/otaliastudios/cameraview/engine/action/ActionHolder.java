package com.otaliastudios.cameraview.engine.action;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public interface ActionHolder {
    void addAction(@NonNull Action action);
    void removeAction(@NonNull Action action);

    @NonNull
    CameraCharacteristics getCharacteristics(@NonNull Action action);
    @Nullable
    TotalCaptureResult getLastResult(@NonNull Action action);
    @NonNull
    CaptureRequest.Builder getBuilder(@NonNull Action action);
    void applyBuilder(@NonNull Action source);

    void applyBuilder(@NonNull Action source, @NonNull CaptureRequest.Builder builder)
            throws CameraAccessException;
}
