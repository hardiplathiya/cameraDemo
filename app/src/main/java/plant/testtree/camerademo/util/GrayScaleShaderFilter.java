package plant.testtree.camerademo.util;

import android.content.Context;

/* loaded from: classes.dex */
public class GrayScaleShaderFilter extends SimpleFragmentShaderFilter {
    public GrayScaleShaderFilter(Context context) {
        super(context, "filter/fsh/imgproc/gray_scale.glsl");
    }
}
