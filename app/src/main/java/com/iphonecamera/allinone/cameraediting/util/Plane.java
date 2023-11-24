package com.iphonecamera.allinone.cameraediting.util;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;


public class Plane {
    private FloatBuffer mTexCoordinateBuffer;
    private final float[] TRIANGLES_DATA = {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
    private FloatBuffer mVerticesBuffer = BufferUtils.getFloatBuffer(this.TRIANGLES_DATA, 0);

    public Plane(boolean z) {
        if (z) {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.getRotation(Rotation.NORMAL, false, true), 0);
        } else {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.TEXTURE_NO_ROTATION, 0);
        }
    }

    public void uploadVerticesBuffer(int i) {
        FloatBuffer verticesBuffer = getVerticesBuffer();
        if (verticesBuffer != null) {
            verticesBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 3, (int) 5126, false, 0, (Buffer) verticesBuffer);
            ShaderUtils.checkGlError("glVertexAttribPointer maPosition");
            GLES20.glEnableVertexAttribArray(i);
            ShaderUtils.checkGlError("glEnableVertexAttribArray maPositionHandle");
        }
    }

    public void uploadTexCoordinateBuffer(int i) {
        FloatBuffer texCoordinateBuffer = getTexCoordinateBuffer();
        if (texCoordinateBuffer != null) {
            texCoordinateBuffer.position(0);
            GLES20.glVertexAttribPointer(i, 2, (int) 5126, false, 0, (Buffer) texCoordinateBuffer);
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

    public void setTexCoordinateBuffer(FloatBuffer floatBuffer) {
        this.mTexCoordinateBuffer = floatBuffer;
    }

    public void setVerticesBuffer(FloatBuffer floatBuffer) {
        this.mVerticesBuffer = floatBuffer;
    }

    public void resetTextureCoordinateBuffer(boolean z) {
        this.mTexCoordinateBuffer = null;
        if (z) {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.getRotation(Rotation.NORMAL, false, true), 0);
        } else {
            this.mTexCoordinateBuffer = BufferUtils.getFloatBuffer(PlaneTextureRotationUtils.TEXTURE_NO_ROTATION, 0);
        }
    }

    public void draw() {
        GLES20.glDrawArrays(5, 0, 4);
    }

    public Plane scale(float f) {
        float[] fArr = this.TRIANGLES_DATA;
        float[] fArr2 = new float[fArr.length];
        System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
        for (int i = 0; i < fArr2.length; i++) {
            fArr2[i] = fArr2[i] * f;
        }
        this.mVerticesBuffer = BufferUtils.getFloatBuffer(fArr2, 0);
        return this;
    }
}
