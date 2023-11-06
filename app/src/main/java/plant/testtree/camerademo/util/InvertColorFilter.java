package plant.testtree.camerademo.util;

import android.content.Context;

/* loaded from: classes.dex */
public class InvertColorFilter extends SimpleFragmentShaderFilter {
    public InvertColorFilter(Context context) {
        super(context, "filter/fsh/imgproc/invert_color.glsl");
    }
}
