package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUB extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUB(Context context) {
        super(context, "filter/fsh/beautify/beautify_b.glsl");
    }
}
