package com.iphonecamera.allinone.cameraediting.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class BufferUtils {

    public static FloatBuffer getFloatBuffer(float[] fArr, int i) {
        FloatBuffer put = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr);
        put.position(i);
        return put;
    }

    public static ShortBuffer getShortBuffer(short[] sArr, int i) {
        ShortBuffer put = ByteBuffer.allocateDirect(sArr.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
        put.position(i);
        return put;
    }
}
