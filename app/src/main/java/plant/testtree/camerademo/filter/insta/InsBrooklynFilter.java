package plant.testtree.camerademo.filter.insta;

import android.content.Context;

import plant.testtree.camerademo.filter.MultipleTextureFilter;


/* loaded from: classes.dex */
public class InsBrooklynFilter extends MultipleTextureFilter {
    public InsBrooklynFilter(Context context) {
        super(context, "filter/fsh/insta/brooklyn.glsl");
        this.textureSize = 3;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/brooklynCurves1.png");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/filter_map_first.png");
        this.externalBitmapTextures[2].load(this.context, "filter/textures/inst/brooklynCurves2.png");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 1.0f);
    }
}
