package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;


public class MoneyFilter extends ShaderToyAbsFilter {
    public MoneyFilter(Context context) {
        super(context, "filter/fsh/shadertoy/money_filter.glsl");
    }
}
