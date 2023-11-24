package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.util.GLAbsProgram;
import com.iphonecamera.allinone.cameraediting.util.ShaderUtils;


public class GLPassThroughProgram extends GLAbsProgram {
    private int uMVPMatrixHandle;
    private int uTextureSamplerHandle;

    public GLPassThroughProgram(Context context) {
        super(context, "filter/vsh/base/pass_through.glsl", "filter/fsh/base/pass_through.glsl");
    }

    @Override // com.cameraediter.iphone11pro.filter.glessential.program.GLAbsProgram
    public void create() {
        super.create();
        this.uTextureSamplerHandle = GLES20.glGetUniformLocation(getProgramId(), "sTexture");
        ShaderUtils.checkGlError("glGetUniformLocation uniform sTexture");
        this.uMVPMatrixHandle = GLES20.glGetUniformLocation(getProgramId(), "uMVPMatrix");
        ShaderUtils.checkGlError("glGetUniformLocation uMVPMatrix");
    }

    public int getTextureSamplerHandle() {
        return this.uTextureSamplerHandle;
    }

    public int getMVPMatrixHandle() {
        return this.uMVPMatrixHandle;
    }
}
