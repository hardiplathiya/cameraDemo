package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;


public class ReminiscenceFilter extends SimpleFragmentShaderFilter {
    public ReminiscenceFilter(Context context) {
        super(context, "filter/fsh/mx/mx_reminiscence.glsl");
    }
}
