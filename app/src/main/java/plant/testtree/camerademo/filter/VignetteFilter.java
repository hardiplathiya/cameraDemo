package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class VignetteFilter extends SimpleFragmentShaderFilter {
    public VignetteFilter(Context context) {
        super(context, "filter/fsh/mx/mx_vignette.glsl");
    }
}
