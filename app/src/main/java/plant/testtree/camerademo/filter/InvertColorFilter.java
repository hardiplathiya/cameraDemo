package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class InvertColorFilter extends SimpleFragmentShaderFilter {
    public InvertColorFilter(Context context) {
        super(context, "filter/fsh/imgproc/invert_color.glsl");
    }
}
