package com.iphonecamera.allinone.cameraediting.filter.beautify;

import android.content.Context;
import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class BeautifyFilterFUD extends SimpleFragmentShaderFilter {
    public BeautifyFilterFUD(Context context) {
        super(context, "filter/fsh/beautify/beautify_d.glsl");
    }
}
