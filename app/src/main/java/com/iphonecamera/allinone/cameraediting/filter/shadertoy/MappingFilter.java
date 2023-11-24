package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class MappingFilter extends ShaderToyAbsFilter {
    public MappingFilter(Context context) {
        super(context, "filter/fsh/shadertoy/mapping.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/mapping.jpg");
    }
}
