package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;



public class InsLomoFilter extends MultipleTextureFilter {
    public InsLomoFilter(Context context) {
        super(context, "filter/fsh/insta/lomo.glsl");
        this.textureSize = 2;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/lomomap_new.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/vignette_map.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
