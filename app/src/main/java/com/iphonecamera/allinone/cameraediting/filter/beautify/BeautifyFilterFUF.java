package com.iphonecamera.allinone.cameraediting.filter.beautify;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUF extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUF(Context context) {
        super(context, "filter/fsh/beautify/beautify_f.glsl");
    }
}
