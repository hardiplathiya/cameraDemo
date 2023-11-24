package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class CrosshatchFilter extends ShaderToyAbsFilter {
    public CrosshatchFilter(Context context) {
        super(context, "filter/fsh/shadertoy/crosshatch.glsl");
    }
}
