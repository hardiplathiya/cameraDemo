package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class InvertColorFilter extends SimpleFragmentShaderFilter {
    public InvertColorFilter(Context context) {
        super(context, "filter/fsh/imgproc/invert_color.glsl");
    }
}
