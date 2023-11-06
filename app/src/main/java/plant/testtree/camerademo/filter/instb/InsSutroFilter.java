package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import plant.testtree.camerademo.filter.MultipleTextureFilter;

/* loaded from: classes.dex */
public class InsSutroFilter extends MultipleTextureFilter {
    public InsSutroFilter(Context context) {
        super(context, "filter/fsh/instb/sutro.glsl");
        this.textureSize = 5;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/vignette_map.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/sutrometal.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/softlight.png");
        this.externalBitmapTextures[3].load(this.context, "filter/textures/inst/sutroedgeburn.png");
        this.externalBitmapTextures[4].load(this.context, "filter/textures/inst/sutrocurves.png");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
