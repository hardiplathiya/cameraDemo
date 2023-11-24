package com.iphonecamera.allinone.cameraediting.filter.beautify;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUB extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUB(Context context) {
        super(context, "filter/fsh/beautify/beautify_b.glsl");
    }
}
