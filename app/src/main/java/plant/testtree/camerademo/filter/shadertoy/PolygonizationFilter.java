package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class PolygonizationFilter extends ShaderToyAbsFilter {
    public PolygonizationFilter(Context context) {
        super(context, "filter/fsh/shadertoy/polygonization.glsl");
    }
}
