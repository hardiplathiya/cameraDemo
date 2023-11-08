package com.otaliastudios.cameraview.engine.offset;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.controls.Facing;

public class Angles {

    private final static String TAG = Angles.class.getSimpleName();
    private final static CameraLogger LOG = CameraLogger.create(TAG);

    private Facing mSensorFacing;
    @VisibleForTesting int mSensorOffset = 0;
    @VisibleForTesting int mDisplayOffset = 0;
    @VisibleForTesting int mDeviceOrientation = 0;


    public void setSensorOffset(@NonNull Facing sensorFacing, int sensorOffset) {
        sanitizeInput(sensorOffset);
        mSensorFacing = sensorFacing;
        mSensorOffset = sensorOffset;
        if (mSensorFacing == Facing.FRONT) {
            mSensorOffset = sanitizeOutput(360 - mSensorOffset);
        }
        print();
    }

    public void setDisplayOffset(int displayOffset) {
        sanitizeInput(displayOffset);
        mDisplayOffset = displayOffset;
        print();
    }

    public void setDeviceOrientation(int deviceOrientation) {
        sanitizeInput(deviceOrientation);
        mDeviceOrientation = deviceOrientation;
        print();
    }

    private void print() {
        LOG.i("Angles changed:",
                "sensorOffset:", mSensorOffset,
                "displayOffset:", mDisplayOffset,
                "deviceOrientation:", mDeviceOrientation);
    }

    public int offset(@NonNull Reference from, @NonNull Reference to, @NonNull Axis axis) {
        int offset = absoluteOffset(from, to);
        if (axis == Axis.RELATIVE_TO_SENSOR) {
            if (mSensorFacing == Facing.FRONT) {
                offset = sanitizeOutput(360 - offset);
            }
        }
        return offset;
    }

    private int absoluteOffset(@NonNull Reference from, @NonNull Reference to) {
        if (from == to) {
            return 0;
        } else if (to == Reference.BASE) {
            return sanitizeOutput(360 - absoluteOffset(to, from));
        } else if (from == Reference.BASE) {
            switch (to) {
                case VIEW: return sanitizeOutput(360 - mDisplayOffset);
                case OUTPUT: return sanitizeOutput(mDeviceOrientation);
                case SENSOR: return sanitizeOutput(360 - mSensorOffset);
                default: throw new RuntimeException("Unknown reference: " + to);
            }
        } else {
            return sanitizeOutput(
                    absoluteOffset(Reference.BASE, to)
                    - absoluteOffset(Reference.BASE, from));
        }
    }

    public boolean flip(@NonNull Reference from, @NonNull Reference to) {
        return offset(from, to, Axis.ABSOLUTE) % 180 != 0;
    }

    private void sanitizeInput(int value) {
        if (value != 0
                && value != 90
                && value != 180
                && value != 270) {
            throw new IllegalStateException("This value is not sanitized: " + value);
        }
    }

    private int sanitizeOutput(int value) {
        return (value + 360) % 360;
    }
}
