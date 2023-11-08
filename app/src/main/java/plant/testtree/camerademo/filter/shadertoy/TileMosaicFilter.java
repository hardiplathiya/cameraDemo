package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class TileMosaicFilter extends ShaderToyAbsFilter {
    public TileMosaicFilter(Context context) {
        super(context, "filter/fsh/shadertoy/tile_mosaic.glsl");
    }
}
