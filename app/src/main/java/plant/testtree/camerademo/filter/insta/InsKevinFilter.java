package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;



public class InsKevinFilter extends MultipleTextureFilter {
    public InsKevinFilter(Context context) {
        super(context, "filter/fsh/insta/kevin.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/kevinmap.png");
    }
}
