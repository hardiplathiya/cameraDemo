package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class GreenHouseFilter extends SimpleFragmentShaderFilter {
    public GreenHouseFilter(Context context) {
        super(context, "filter/fsh/mx/mx_green_house.glsl");
    }
}
