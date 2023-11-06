package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;

/* loaded from: classes.dex */
public class BeautifyFilterFUC extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUC(Context context) {
        super(context, "filter/fsh/beautify/beautify_c.glsl");
    }
}
