package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class NoiseWarpFilter extends ShaderToyAbsFilter {
    public NoiseWarpFilter(Context context) {
        super(context, "filter/fsh/shadertoy/noise_warp.glsl");
    }
}
