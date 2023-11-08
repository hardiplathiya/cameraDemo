package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class GrayScaleShaderFilter extends SimpleFragmentShaderFilter {
    public GrayScaleShaderFilter(Context context) {
        super(context, "filter/fsh/imgproc/gray_scale.glsl");
    }
}
