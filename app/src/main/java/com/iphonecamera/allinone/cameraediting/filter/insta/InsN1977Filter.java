package com.iphonecamera.allinone.cameraediting.filter.insta;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;



public class InsN1977Filter extends MultipleTextureFilter {
    public InsN1977Filter(Context context) {
        super(context, "filter/fsh/insta/n1977.glsl");
        this.textureSize = 2;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/n1977map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/n1977blowout.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
