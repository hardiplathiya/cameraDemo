package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class ContrastFilter extends ShaderToyAbsFilter {
    public ContrastFilter(Context context) {
        super(context, "filter/fsh/shadertoy/contrast.glsl");
    }
}
