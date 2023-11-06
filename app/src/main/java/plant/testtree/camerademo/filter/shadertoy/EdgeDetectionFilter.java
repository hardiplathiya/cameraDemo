package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;

/* loaded from: classes.dex */
public class EdgeDetectionFilter extends ShaderToyAbsFilter {
    public EdgeDetectionFilter(Context context) {
        super(context, "filter/fsh/shadertoy/edge_detection.glsl");
    }
}
