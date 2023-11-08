package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUD extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUD(Context context) {
        super(context, "filter/fsh/beautify/beautify_d.glsl");
    }
}
