package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;

/* loaded from: classes.dex */
public class BeautifyFilterFUF extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUF(Context context) {
        super(context, "filter/fsh/beautify/beautify_f.glsl");
    }
}
