package com.iphonecamera.allinone.cameraediting.filter.instb;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;


public class InsRiseFilter extends MultipleTextureFilter {
    public InsRiseFilter(Context context) {
        super(context, "filter/fsh/instb/rise.glsl");
        this.textureSize = 3;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/blackboard1024.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/overlaymap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/risemap.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
