package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class EdgeDetectionFilter extends ShaderToyAbsFilter {
    public EdgeDetectionFilter(Context context) {
        super(context, "filter/fsh/shadertoy/edge_detection.glsl");
    }
}
