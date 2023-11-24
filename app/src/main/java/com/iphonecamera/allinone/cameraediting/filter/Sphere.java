package com.iphonecamera.allinone.cameraediting.filter;

import android.opengl.GLES20;
import com.drew.metadata.exif.makernotes.FujifilmMakernoteDirectory;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.iphonecamera.allinone.cameraediting.util.ShaderUtils;


public class Sphere {
    private static final int sPositionDataSize = 3;
    private static final int sTextureCoordinateDataSize = 2;
    private ShortBuffer indexBuffer;
    private int mNumIndices;
    private FloatBuffer mTexCoordinateBuffer;
    private FloatBuffer mVerticesBuffer;

    public Sphere(float f, int i, int i2) {
        float f2 = 0;
        float f3 = 1.0f / i;
        float f4 = 1.0f / i2;
        int i3 = i + 1;
        int i4 = i2 + 1;
        int i5 = i3 * i4;
        float[] fArr = new float[i5 * 3];
        float[] fArr2 = new float[i5 * 2];
        short[] sArr = new short[i5 * 6];
        short s = 0;
        int i6 = 0;
        int i7 = 0;
        while (s < i3) {
            int i8 = i6;
            short s2 = 0;
            while (s2 < i4) {
                float f5 = s2;
                int i9 = i3;
                double d = 6.2831855f * f5 * f4;
                float f6 = s;
                double d2 = 3.1415927f * f6 * f3;
                short s3 = s2;
                float[] fArr3 = fArr;
                float sin = (float) (Math.sin(d) * Math.sin(d2));
                int i10 = i7 + 1;
                fArr2[i7] = f5 * f4;
                i7 = i10 + 1;
                fArr2[i10] = f6 * f3;
                int i11 = i8 + 1;
                fArr3[i8] = ((float) (Math.cos(d) * Math.sin(d2))) * f;
                int i12 = i11 + 1;
                fArr3[i11] = ((float) Math.sin(f2 - 2.8584073f)) * f;
                i8 = i12 + 1;
                fArr3[i12] = sin * f;
                fArr = fArr3;
                i3 = i9;
                i4 = i4;
                sArr = sArr;
                s = s;
                s2 = (short) (s3 + 1);
            }
            s = (short) (s + 1);
            i6 = i8;
        }
        int i13 = i4;
        short[] sArr2 = sArr;
        float[] fArr4 = fArr;
        short s4 = 0;
        int i14 = 0;
        while (s4 < i) {
            int i15 = i14;
            short s5 = 0;
            while (s5 < i2) {
                int i16 = i15 + 1;
                int i17 = s4 * i13;
                sArr2[i15] = (short) (i17 + s5);
                int i18 = i16 + 1;
                int i19 = (s4 + 1) * i13;
                short s6 = (short) (i19 + s5);
                sArr2[i16] = s6;
                int i20 = i18 + 1;
                int i21 = s5 + 1;
                short s7 = (short) (i17 + i21);
                sArr2[i18] = s7;
                int i22 = i20 + 1;
                sArr2[i20] = s7;
                int i23 = i22 + 1;
                sArr2[i22] = s6;
                i15 = i23 + 1;
                sArr2[i23] = (short) (i19 + i21);
                s5 = (short) i21;
            }
            s4 = (short) (s4 + 1);
            i14 = i15;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr4.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr4);
        asFloatBuffer.position(0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(fArr2.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer2 = allocateDirect2.asFloatBuffer();
        asFloatBuffer2.put(fArr2);
        asFloatBuffer2.position(0);
        ByteBuffer allocateDirect3 = ByteBuffer.allocateDirect(sArr2.length * 2);
        allocateDirect3.order(ByteOrder.nativeOrder());
        this.indexBuffer = allocateDirect3.asShortBuffer();
        this.indexBuffer.put(sArr2);
        this.indexBuffer.position(0);
        this.mTexCoordinateBuffer = asFloatBuffer2;
        this.mVerticesBuffer = asFloatBuffer;
        this.mNumIndices = sArr2.length;
    }

    public void uploadVerticesBuffer(int i) {
        FloatBuffer verticesBuffer = getVerticesBuffer();
        if (verticesBuffer != null) {
            verticesBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 3, (int) FujifilmMakernoteDirectory.TAG_MAX_APERTURE_AT_MIN_FOCAL, false, 0, (Buffer) verticesBuffer);
            ShaderUtils.checkGlError("glVertexAttribPointer maPosition");
            GLES20.glEnableVertexAttribArray(i);
            ShaderUtils.checkGlError("glEnableVertexAttribArray maPositionHandle");
        }
    }

    public void uploadTexCoordinateBuffer(int i) {
        FloatBuffer texCoordinateBuffer = getTexCoordinateBuffer();
        if (texCoordinateBuffer != null) {
            texCoordinateBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 2, (int) FujifilmMakernoteDirectory.TAG_MAX_APERTURE_AT_MIN_FOCAL, false, 0, (Buffer) texCoordinateBuffer);
            ShaderUtils.checkGlError("glVertexAttribPointer maTextureHandle");
            GLES20.glEnableVertexAttribArray(i);
            ShaderUtils.checkGlError("glEnableVertexAttribArray maTextureHandle");
        }
    }

    public FloatBuffer getVerticesBuffer() {
        return this.mVerticesBuffer;
    }

    public FloatBuffer getTexCoordinateBuffer() {
        return this.mTexCoordinateBuffer;
    }

    public void draw() {
        ShortBuffer shortBuffer = this.indexBuffer;
        if (shortBuffer != null) {
            shortBuffer.position(0);
            GLES20.glDrawElements(4, this.mNumIndices, (int) FujifilmMakernoteDirectory.TAG_DEVELOPMENT_DYNAMIC_RANGE, this.indexBuffer);
            return;
        }
        GLES20.glDrawArrays(4, 0, this.mNumIndices);
    }
}
