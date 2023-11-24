package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class ChromaticAberrationFilter extends ShaderToyAbsFilter {
    public ChromaticAberrationFilter(Context context) {
        super(context, "filter/fsh/shadertoy/chromatic_aberration.glsl");
    }
}
