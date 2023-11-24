package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.shadertoy.FastBlurFilter;


public class BlurredFrameEffect extends FilterGroup {
    private static final int BLUR_STEP_LENGTH = 2;
    private static final float SCALING_FACTOR = 0.6f;
    private ScalingFilter scalingFilter;

    public BlurredFrameEffect(Context context) {
        addFilter(new FastBlurFilter(context).setScale(true));
        addFilter(CustomizedGaussianBlurFilter.initWithBlurRadiusInPixels(4).setTexelHeightOffset(2.0f).setScale(true));
        addFilter(CustomizedGaussianBlurFilter.initWithBlurRadiusInPixels(4).setTexelWidthOffset(2.0f).setScale(true));
        addFilter(new PassThroughFilter(context));
        this.scalingFilter = new ScalingFilter(context).setScalingFactor(SCALING_FACTOR).setDrawOnTop(true);
    }

    @Override // com.cameraediter.iphone11pro.filter.base.FilterGroup, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        this.scalingFilter.onDrawFrame(i);
    }

    @Override // com.cameraediter.iphone11pro.filter.base.FilterGroup, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        super.init();
        this.scalingFilter.init();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.FilterGroup, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
        this.scalingFilter.onFilterChanged(i, i2);
    }

    @Override // com.cameraediter.iphone11pro.filter.base.FilterGroup, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void destroy() {
        super.destroy();
        this.scalingFilter.destroy();
    }
}
