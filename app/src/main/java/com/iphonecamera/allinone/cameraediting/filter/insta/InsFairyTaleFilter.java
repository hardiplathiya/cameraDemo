package com.iphonecamera.allinone.cameraediting.filter.insta;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;



public class InsFairyTaleFilter extends MultipleTextureFilter {
    public InsFairyTaleFilter(Context context) {
        super(context, "filter/fsh/insta/look_up.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/fairy_tale.png");
    }
}
