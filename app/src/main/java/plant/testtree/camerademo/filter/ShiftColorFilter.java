package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;



public class ShiftColorFilter extends SimpleFragmentShaderFilter {
    public ShiftColorFilter(Context context) {
        super(context, "filter/fsh/mx/mx_shift_color.glsl");
    }
}
