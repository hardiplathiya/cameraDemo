package com.iphonecamera.allinone.cameraediting.filter.instb;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;


public class InsXproIIFilter extends MultipleTextureFilter {
    public InsXproIIFilter(Context context) {
        super(context, "filter/fsh/instb/xproii.glsl");
        this.textureSize = 2;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/xpromap.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/vignettemap_new.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
