package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class CrackedFilter extends ShaderToyAbsFilter {
    public CrackedFilter(Context context) {
        super(context, "filter/fsh/shadertoy/cracked.glsl");
    }
}
