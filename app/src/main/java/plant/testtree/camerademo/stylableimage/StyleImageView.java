package plant.testtree.camerademo.stylableimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import plant.testtree.camerademo.R;

/* loaded from: classes2.dex */
public class StyleImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Styler styler;

    public StyleImageView(Context context) {
        super(context);
        init();
    }

    public StyleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
       // readAttrFromXml(attributeSet, 0);
    }

    public StyleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
       // readAttrFromXml(attributeSet, i);
    }

    private void init() {
        this.styler = new Styler.Builder(this, -1).build();
    }

//    private void readAttrFromXml(AttributeSet attributeSet, int i) {
//        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.StyleImageView, i, 0);
//        this.styler.setMode(obtainStyledAttributes.getInt(R.styleable.StyleImageView_style, -1));
//        this.styler.setBrightness(obtainStyledAttributes.getInt(R.styleable.StyleImageView_brightness, 0));
//        this.styler.setContrast(obtainStyledAttributes.getFloat(R.styleable.StyleImageView_contrast, 1.0f));
//        float f = obtainStyledAttributes.getFloat(R.styleable.StyleImageView_saturation, 1.0f);
//        if (this.styler.getMode() != 0 && f != 1.0f) {
//            if (this.styler.getMode() != -1) {
//                throw new IllegalStateException("Mode must be SATURATION when saturation is set in xml");
//            }
//            this.styler.setMode(0);
//            this.styler.setSaturation(f);
//        }
//        boolean z = obtainStyledAttributes.getBoolean(R.styleable.StyleImageView_enable_animation, false);
//        long j = obtainStyledAttributes.getInt(R.styleable.StyleImageView_animation_duration, 0);
//        if (!z && j != 0) {
//            throw new IllegalStateException("Animate can't be false when animation_duration is set");
//        }
//        if (z) {
//            this.styler.enableAnimation(j);
//        }
//        obtainStyledAttributes.recycle();
//        updateStyle();
//    }

    public void updateStyle() {
        this.styler.updateStyle();
    }

    public void clearStyle() {
        this.styler.clearStyle();
    }

    public boolean isAnimationEnabled() {
        return this.styler.isAnimationEnabled();
    }

    public long getAnimationDuration() {
        return this.styler.getAnimationDuration();
    }

    public StyleImageView enableAnimation(long j) {
        this.styler.enableAnimation(j);
        return this;
    }

    public StyleImageView enableAnimation(long j, Interpolator interpolator) {
        this.styler.enableAnimation(j, interpolator);
        return this;
    }

    public StyleImageView disableAnimation() {
        this.styler.disableAnimation();
        return this;
    }

    public int getBrightness() {
        return this.styler.getBrightness();
    }

    public StyleImageView setBrightness(int i) {
        if (i <= 255) {
            if (i < -255) {
                throw new IllegalArgumentException("brightness can't be smaller than -255");
            }
            this.styler.setBrightness(i);
            return this;
        }
        throw new IllegalArgumentException("brightness can't be bigger than 255");
    }

    public float getContrast() {
        return this.styler.getContrast();
    }

    public StyleImageView setContrast(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("contrast can't be smaller than 0");
        }
        this.styler.setContrast(f);
        return this;
    }

    public float getSaturation() {
        return this.styler.getSaturation();
    }

    public StyleImageView setSaturation(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("saturation can't be smaller than 0");
        }
        this.styler.setSaturation(f);
        return this;
    }

    public int getMode() {
        return this.styler.getMode();
    }

    public StyleImageView setMode(int i) {
        if (!Styler.Mode.hasMode(i)) {
            throw new IllegalArgumentException("Mode " + i + " not supported! Check Styler.Mode class for supported modes");
        }
        this.styler.setMode(i);
        return this;
    }

    public Styler.AnimationListener getAnimationListener() {
        return this.styler.getAnimationListener();
    }

    public StyleImageView setAnimationListener(Styler.AnimationListener animationListener) {
        this.styler.setAnimationListener(animationListener);
        return this;
    }

    public Styler.AnimationListener removeAnimationListener() {
        return this.styler.removeAnimationListener();
    }

    public Bitmap getBitmap() {
        return this.styler.getBitmap();
    }

    public Bitmap getBitmap(int i, int i2) {
        return this.styler.getBitmap(i, i2);
    }
}
