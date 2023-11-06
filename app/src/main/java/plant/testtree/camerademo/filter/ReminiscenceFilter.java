package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;

/* loaded from: classes.dex */
public class ReminiscenceFilter extends SimpleFragmentShaderFilter {
    public ReminiscenceFilter(Context context) {
        super(context, "filter/fsh/mx/mx_reminiscence.glsl");
    }
}
