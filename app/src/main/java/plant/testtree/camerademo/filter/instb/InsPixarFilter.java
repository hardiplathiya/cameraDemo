package plant.testtree.camerademo.filter.instb;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;


public class InsPixarFilter extends MultipleTextureFilter {
    public InsPixarFilter(Context context) {
        super(context, "filter/fsh/instb/pixar.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/pixar_curves.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
