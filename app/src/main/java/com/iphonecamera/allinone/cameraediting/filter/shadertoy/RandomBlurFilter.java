package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class RandomBlurFilter extends ShaderToyAbsFilter {
    public RandomBlurFilter(Context context) {
        super(context, "filter/fsh/shadertoy/random_blur.glsl");
    }
}
