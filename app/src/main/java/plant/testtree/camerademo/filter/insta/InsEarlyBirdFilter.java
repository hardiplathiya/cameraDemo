package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;



public class InsEarlyBirdFilter extends MultipleTextureFilter {
    public InsEarlyBirdFilter(Context context) {
        super(context, "filter/fsh/insta/early_bird.glsl");
        this.textureSize = 5;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/earlybirdcurves.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/earlybirdoverlaymap_new.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/vignettemap_new.png");
        this.externalBitmapTextures[3].load(this.context, "filter/textures/inst/earlybirdblowout.png");
        this.externalBitmapTextures[4].load(this.context, "filter/textures/inst/earlybirdmap.png");
    }
}
