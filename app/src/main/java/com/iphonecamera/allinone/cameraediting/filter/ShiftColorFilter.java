package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.util.SimpleFragmentShaderFilter;



public class ShiftColorFilter extends SimpleFragmentShaderFilter {
    public ShiftColorFilter(Context context) {
        super(context, "filter/fsh/mx/mx_shift_color.glsl");
    }
}
