package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;


/* loaded from: classes.dex */
public class InsNashvilleFilter extends MultipleTextureFilter {
    public InsNashvilleFilter(Context context) {
        super(context, "filter/fsh/insta/nashville.glsl");
        this.textureSize = 2;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/nashvillemap.png");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
