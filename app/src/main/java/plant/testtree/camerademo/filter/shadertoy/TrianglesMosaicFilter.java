package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class TrianglesMosaicFilter extends ShaderToyAbsFilter {
    public TrianglesMosaicFilter(Context context) {
        super(context, "filter/fsh/shadertoy/triangles_mosaic.glsl");
    }
}
