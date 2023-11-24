package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class AscIIArtFilter extends ShaderToyAbsFilter {
    public AscIIArtFilter(Context context) {
        super(context, "filter/fsh/shadertoy/ascii_art.glsl");
    }
}
