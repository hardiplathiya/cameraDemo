package com.iphonecamera.allinone.cameraediting.filter.beautify;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUE extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUE(Context context) {
        super(context, "filter/fsh/beautify/beautify_e.glsl");
    }
}
