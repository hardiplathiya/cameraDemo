package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;

/* loaded from: classes.dex */
public class NoiseWarpFilter extends ShaderToyAbsFilter {
    public NoiseWarpFilter(Context context) {
        super(context, "filter/fsh/shadertoy/noise_warp.glsl");
    }
}
