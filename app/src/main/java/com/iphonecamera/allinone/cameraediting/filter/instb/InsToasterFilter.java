package com.iphonecamera.allinone.cameraediting.filter.instb;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;


public class InsToasterFilter extends MultipleTextureFilter {
    public InsToasterFilter(Context context) {
        super(context, "filter/fsh/instb/toaster2.glsl");
        this.textureSize = 5;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/toastermetal.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/toastersoftlight.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/toastercurves.png");
        this.externalBitmapTextures[3].load(this.context, "filter/textures/inst/toasteroverlaymapwarm.png");
        this.externalBitmapTextures[4].load(this.context, "filter/textures/inst/toastercolorshift.png");
    }
}
