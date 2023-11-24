package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class LichtensteinEsqueFilter extends ShaderToyAbsFilter {
    public LichtensteinEsqueFilter(Context context) {
        super(context, "filter/fsh/shadertoy/lichtenstein_esque.glsl");
    }
}
