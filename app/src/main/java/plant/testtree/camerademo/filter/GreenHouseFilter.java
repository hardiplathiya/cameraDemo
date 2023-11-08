package plant.testtree.camerademo.filter;

import android.content.Context;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class GreenHouseFilter extends SimpleFragmentShaderFilter {
    public GreenHouseFilter(Context context) {
        super(context, "filter/fsh/mx/mx_green_house.glsl");
    }
}
