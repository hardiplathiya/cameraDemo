package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;

/* loaded from: classes.dex */
public class MoonLightFilter extends SimpleFragmentShaderFilter {
    public MoonLightFilter(Context context) {
        super(context, "filter/fsh/mx/mx_moon_light.glsl");
    }
}
