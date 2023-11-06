package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;

/* loaded from: classes.dex */
public class FastBlurFilter extends ShaderToyAbsFilter {
    private boolean scale;

    public FastBlurFilter(Context context) {
        super(context, "filter/fsh/shadertoy/fast_blur.glsl");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public FastBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }
}
