package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;



public class InsFreudFilter extends MultipleTextureFilter {
    public InsFreudFilter(Context context) {
        super(context, "filter/fsh/insta/freud.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/freud_rand.png");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "inputImageTextureHeight", this.surfaceHeight);
        setUniform1f(this.glSimpleProgram.getProgramId(), "inputImageTextureWidth", this.surfaceWidth);
    }
}
