package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class PixelizeFilter extends ShaderToyAbsFilter {
    public PixelizeFilter(Context context) {
        super(context, "filter/fsh/shadertoy/pixelize.glsl");
    }
}
