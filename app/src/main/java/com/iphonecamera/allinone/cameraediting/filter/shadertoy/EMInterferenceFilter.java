package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class EMInterferenceFilter extends ShaderToyAbsFilter {
    public EMInterferenceFilter(Context context) {
        super(context, "filter/fsh/shadertoy/em_interference.glsl");
    }
}
