package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;

/* loaded from: classes.dex */
public class RefractionFilter extends ShaderToyAbsFilter {
    public RefractionFilter(Context context) {
        super(context, "filter/fsh/shadertoy/refraction.glsl");
        this.textureSize = 1;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/refraction.png");
    }
}
