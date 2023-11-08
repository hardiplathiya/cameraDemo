package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUE extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUE(Context context) {
        super(context, "filter/fsh/beautify/beautify_e.glsl");
    }
}
