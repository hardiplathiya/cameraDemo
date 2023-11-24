package com.iphonecamera.allinone.cameraediting.filter.insta;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;



public class InsBrooklynFilter extends MultipleTextureFilter {
    public InsBrooklynFilter(Context context) {
        super(context, "filter/fsh/insta/brooklyn.glsl");
        this.textureSize = 3;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/brooklynCurves1.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/filter_map_first.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/brooklynCurves2.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
