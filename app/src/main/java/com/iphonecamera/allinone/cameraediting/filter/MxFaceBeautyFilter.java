package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;
import com.iphonecamera.allinone.cameraediting.util.TextureUtils;


public class MxFaceBeautyFilter extends SimpleFragmentShaderFilter {
    public MxFaceBeautyFilter(Context context) {
        super(context, "filter/fsh/mx/mx_face_beauty.glsl");
    }

    @Override 
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "stepSizeX", 1.0f / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "stepSizeY", 1.0f / this.surfaceHeight);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
