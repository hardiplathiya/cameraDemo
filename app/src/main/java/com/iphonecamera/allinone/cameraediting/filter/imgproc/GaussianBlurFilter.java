package com.iphonecamera.allinone.cameraediting.filter.imgproc;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.filter.AbsFilter;
import com.iphonecamera.allinone.cameraediting.util.GLSimpleProgram;
import com.iphonecamera.allinone.cameraediting.util.TextureUtils;


public class GaussianBlurFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public GaussianBlurFilter(Context context) {
        this.glSimpleProgram = new GLSimpleProgram(context, "filter/vsh/imgproc/gaussian_blur.glsl", "filter/fsh/imgproc/gaussian_blur.glsl");
    }

    @Override 
    public void init() {
        this.glSimpleProgram.create();
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glSimpleProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glSimpleProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glSimpleProgram.getPositionHandle());
    }

    @Override 
    public void destroy() {
        this.glSimpleProgram.onDestroy();
    }

    @Override 
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / this.surfaceHeight);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public GaussianBlurFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public GaussianBlurFilter setTexelWidthOffset(float f) {
        this.texelWidthOffset = f;
        return this;
    }

    @Override 
    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public GaussianBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }
}
