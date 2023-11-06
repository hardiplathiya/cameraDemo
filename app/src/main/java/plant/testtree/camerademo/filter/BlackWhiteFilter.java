package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;

/* loaded from: classes.dex */
public class BlackWhiteFilter extends SimpleFragmentShaderFilter {
    public BlackWhiteFilter(Context context) {
        super(context, "filter/fsh/mx/mx_black_white.glsl");
    }
}
