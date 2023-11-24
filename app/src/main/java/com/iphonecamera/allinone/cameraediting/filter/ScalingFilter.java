package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;


public class ScalingFilter extends PassThroughFilter {
    private boolean drawOnTop;

    public ScalingFilter(Context context) {
        super(context);
        this.drawOnTop = false;
    }

    @Override 
    public void onPreDrawElements() {
        if (this.drawOnTop) {
            return;
        }
        super.onPreDrawElements();
    }

    public ScalingFilter setScalingFactor(float f) {
        this.plane.scale(f);
        return this;
    }

    public ScalingFilter setDrawOnTop(boolean z) {
        this.drawOnTop = z;
        return this;
    }
}
