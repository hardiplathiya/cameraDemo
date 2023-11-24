package com.iphonecamera.allinone.cameraediting.filter.beautify;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUC extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUC(Context context) {
        super(context, "filter/fsh/beautify/beautify_c.glsl");
    }
}
