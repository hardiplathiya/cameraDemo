package plant.testtree.camerademo.filter;

import android.content.Context;

/* loaded from: classes.dex */
public class ScalingFilter extends PassThroughFilter {
    private boolean drawOnTop;

    public ScalingFilter(Context context) {
        super(context);
        this.drawOnTop = false;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.PassThroughFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
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
