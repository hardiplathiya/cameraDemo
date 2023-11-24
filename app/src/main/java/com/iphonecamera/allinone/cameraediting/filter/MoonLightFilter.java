package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class MoonLightFilter extends SimpleFragmentShaderFilter {
    public MoonLightFilter(Context context) {
        super(context, "filter/fsh/mx/mx_moon_light.glsl");
    }
}
