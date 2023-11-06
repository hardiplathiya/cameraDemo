package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import plant.testtree.camerademo.filter.MultipleTextureFilter;

/* loaded from: classes.dex */
public class InsWaldenFilter extends MultipleTextureFilter {
    public InsWaldenFilter(Context context) {
        super(context, "filter/fsh/instb/walden.glsl");
        this.textureSize = 2;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/walden_map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/vignette_map.png");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
