package com.iphonecamera.allinone.cameraediting.filter.instb;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;




public class InsSierraFilter extends MultipleTextureFilter {
    public InsSierraFilter(Context context) {
        super(context, "filter/fsh/instb/sierra.glsl");
        this.textureSize = 3;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/sierravignette.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/overlaymap.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/sierramap.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
