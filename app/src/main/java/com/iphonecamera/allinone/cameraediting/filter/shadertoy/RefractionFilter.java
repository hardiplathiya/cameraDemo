package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class RefractionFilter extends ShaderToyAbsFilter {
    public RefractionFilter(Context context) {
        super(context, "filter/fsh/shadertoy/refraction.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/refraction.png");
    }
}
