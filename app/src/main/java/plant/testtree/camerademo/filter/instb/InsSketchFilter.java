package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import plant.testtree.camerademo.filter.MultipleTextureFilter;

/* loaded from: classes.dex */
public class InsSketchFilter extends MultipleTextureFilter {
    public InsSketchFilter(Context context) {
        super(context, "filter/fsh/instb/sketch.glsl");
        this.textureSize = 0;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.MultipleTextureFilter, com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "strength", 0.9f);
        setUniform2fv(this.glSimpleProgram.getProgramId(), "singleStepOffset", new float[]{1.0f / this.surfaceWidth, 1.0f / this.surfaceHeight});
    }
}
