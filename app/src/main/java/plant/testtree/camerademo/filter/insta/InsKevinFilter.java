package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;


/* loaded from: classes.dex */
public class InsKevinFilter extends MultipleTextureFilter {
    public InsKevinFilter(Context context) {
        super(context, "filter/fsh/insta/kevin.glsl");
        this.textureSize = 1;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/kevinmap.png");
    }
}
