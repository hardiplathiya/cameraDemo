package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


/* loaded from: classes.dex */
public class ShiftColorFilter extends SimpleFragmentShaderFilter {
    public ShiftColorFilter(Context context) {
        super(context, "filter/fsh/mx/mx_shift_color.glsl");
    }
}
