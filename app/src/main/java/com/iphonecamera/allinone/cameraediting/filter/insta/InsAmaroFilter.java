package com.iphonecamera.allinone.cameraediting.filter.insta;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;



public class InsAmaroFilter extends MultipleTextureFilter {
    public InsAmaroFilter(Context context) {
        super(context, "filter/fsh/insta/amaro.glsl");
        this.textureSize = 3;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/brannan_blowout.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/overlaymap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/amaromap.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
