package com.iphonecamera.allinone.cameraediting.filter.insta;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;
import com.iphonecamera.allinone.cameraediting.util.TextureUtils;


public class InsCrayonFilter extends SimpleFragmentShaderFilter {
    private float mStrength;

    public InsCrayonFilter(Context context) {
        super(context, "filter/fsh/insta/crayon.glsl");
        this.mStrength = 2.0f;
    }

    @Override 
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", this.mStrength);
        setUniform2fv(this.glSimpleProgram.getProgramId(), "singleStepOffset", new float[]{1.0f / this.surfaceWidth, 1.0f / this.surfaceHeight});
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
