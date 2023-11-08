package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import plant.testtree.camerademo.filter.MultipleTextureFilter;


public class InsWaldenFilter extends MultipleTextureFilter {
    public InsWaldenFilter(Context context) {
        super(context, "filter/fsh/instb/walden.glsl");
        this.textureSize = 2;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/walden_map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/vignette_map.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
