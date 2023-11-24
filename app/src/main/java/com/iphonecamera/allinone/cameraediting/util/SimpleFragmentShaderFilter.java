package com.iphonecamera.allinone.cameraediting.util;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.filter.AbsFilter;


public class SimpleFragmentShaderFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;

    public SimpleFragmentShaderFilter(Context context, String str) {
        this.glSimpleProgram = new GLSimpleProgram(context, "filter/vsh/base/simple.glsl", str);
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
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
