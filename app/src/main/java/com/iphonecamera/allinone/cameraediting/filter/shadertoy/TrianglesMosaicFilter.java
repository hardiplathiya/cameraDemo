package com.iphonecamera.allinone.cameraediting.filter.shadertoy;

import android.content.Context;


public class TrianglesMosaicFilter extends ShaderToyAbsFilter {
    public TrianglesMosaicFilter(Context context) {
        super(context, "filter/fsh/shadertoy/triangles_mosaic.glsl");
    }
}
