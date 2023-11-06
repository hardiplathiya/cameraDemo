package plant.testtree.camerademo.stylableimage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public class Styler {
    private long animationDuration;
    private ValueAnimator animator;
    private int brightness;
    private float contrast;
    private DrawableHolder drawableHolder;
    private boolean enableAnimation;
    private Interpolator interpolator;
    private AnimationListener listener;
    private int mode;
    private float[] oldMatrix;
    private float saturation;

    /* loaded from: classes2.dex */
    public interface AnimationListener {
        void onAnimationEnd();

        void onAnimationStart();

        void onAnimationUpdate(float f, float f2);
    }

    /* loaded from: classes2.dex */
    public static class Mode {
        public static final int BLACK_AND_WHITE = 5;
        public static final int BRIGHT = 6;
        public static final int GREY_SCALE = 1;
        public static final int INVERT = 2;
        public static final int KODACHROME = 8;
        public static final int NONE = -1;
        public static final int RGB_TO_BGR = 3;
        public static final int SATURATION = 0;
        public static final int SEPIA = 4;
        public static final int TECHNICOLOR = 9;
        public static final int VINTAGE_PINHOLE = 7;

        public static boolean hasMode(int i) {
            switch (i) {
                case -1:
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return true;
                default:
                    return false;
            }
        }
    }

    private Styler(Builder builder) {
        this.oldMatrix = StyleMatrixs.common();
        this.enableAnimation = builder.enableAnimation;
        this.animationDuration = builder.animationDuration;
        this.brightness = builder.brightness;
        this.contrast = builder.contrast;
        this.saturation = builder.saturation;
        this.mode = builder.mode;
        this.drawableHolder = builder.drawableHolder;
        this.interpolator = builder.interpolator;
    }

    public void updateStyle() {
        if (this.drawableHolder.getDrawable() == null) {
            return;
        }
        final float[] calculateMatrix = calculateMatrix(this.mode, this.brightness, this.contrast, this.saturation);
        if (this.enableAnimation) {
            animateMatrix(this.oldMatrix, calculateMatrix, new AnimatorListenerAdapter() { // from class: it.chengdazhi.styleimageview.Styler.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    Styler.this.setDrawableStyleByMatrix(calculateMatrix);
                }
            });
        } else {
            setDrawableStyleByMatrix(calculateMatrix);
        }
    }

    public void clearStyle() {
        if (this.drawableHolder.getDrawable() == null) {
            return;
        }
        if (this.enableAnimation) {
            animateMatrix(this.oldMatrix, StyleMatrixs.common(), new AnimatorListenerAdapter() { // from class: it.chengdazhi.styleimageview.Styler.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    Styler.this.drawableHolder.getDrawable().clearColorFilter();
                    Styler.this.mode = -1;
                    Styler.this.saturation = 1.0f;
                }
            });
            return;
        }
        this.drawableHolder.getDrawable().clearColorFilter();
        this.mode = -1;
        this.saturation = 1.0f;
    }

    private void animateMatrix(final float[] fArr, final float[] fArr2, AnimatorListenerAdapter animatorListenerAdapter) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(this.animationDuration);
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: it.chengdazhi.styleimageview.Styler.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float[] fArr3 = new float[20];
                float animatedFraction = valueAnimator2.getAnimatedFraction();
                float interpolation = Styler.this.interpolator.getInterpolation(animatedFraction);
                for (int i = 0; i < 20; i++) {
                    fArr3[i] = (fArr[i] * (1.0f - interpolation)) + (fArr2[i] * interpolation);
                }
                Styler.this.setDrawableStyleByMatrix(fArr3);
                Styler.this.oldMatrix = (float[]) fArr3.clone();
                if (Styler.this.listener != null) {
                    Styler.this.listener.onAnimationUpdate(animatedFraction, interpolation);
                }
            }
        });
        this.animator.addListener(animatorListenerAdapter);
        this.animator.addListener(new AnimatorListenerAdapter() { // from class: it.chengdazhi.styleimageview.Styler.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (Styler.this.listener != null) {
                    Styler.this.listener.onAnimationStart();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (Styler.this.listener != null) {
                    Styler.this.listener.onAnimationEnd();
                }
            }
        });
        this.animator.start();
    }

    public void setDrawableStyleByMatrix(float[] fArr) {
        if (this.drawableHolder.getDrawable() == null) {
            return;
        }
        this.drawableHolder.getDrawable().setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(fArr)));
        this.oldMatrix = (float[]) fArr.clone();
    }

    private static float[] calculateMatrix(int i, int i2, float f, float f2) {
        return applyBrightnessAndContrast(getMatrixByMode(i, f2), i2, f);
    }

    private static float[] applyBrightnessAndContrast(float[] fArr, int i, float f) {
        float f2 = ((1.0f - f) / 2.0f) * 255.0f;
        for (int i2 = 0; i2 < 3; i2++) {
            int i3 = i2 * 5;
            for (int i4 = i3; i4 < i3 + 3; i4++) {
                fArr[i4] = fArr[i4] * f;
            }
            int i5 = i3 + 4;
            fArr[i5] = fArr[i5] + i + f2;
        }
        return fArr;
    }

    private static float[] getMatrixByMode(int i, float f) {
        float[] common = StyleMatrixs.common();
        switch (i) {
            case -1:
                return StyleMatrixs.common();
            case 0:
                return getSaturationMatrix(f);
            case 1:
                return StyleMatrixs.greyScale();
            case 2:
                return StyleMatrixs.invert();
            case 3:
                return StyleMatrixs.rgbToBgr();
            case 4:
                return StyleMatrixs.sepia();
            case 5:
                return StyleMatrixs.blackAndWhite();
            case 6:
                return StyleMatrixs.bright();
            case 7:
                return StyleMatrixs.vintagePinhole();
            case 8:
                return StyleMatrixs.kodachrome();
            case 9:
                return StyleMatrixs.technicolor();
            default:
                return common;
        }
    }

    private static float[] getSaturationMatrix(float f) {
        float f2 = 1.0f - f;
        float f3 = 0.3086f * f2;
        float f4 = 0.6094f * f2;
        float f5 = f2 * 0.082f;
        float[] common = StyleMatrixs.common();
        common[0] = f3 + f;
        common[1] = f4;
        common[2] = f5;
        common[5] = f3;
        common[6] = f + f4;
        common[7] = f5;
        common[10] = f3;
        common[11] = f4;
        common[12] = f + f5;
        return common;
    }

    public boolean isAnimationEnabled() {
        return this.enableAnimation;
    }

    public long getAnimationDuration() {
        return this.animationDuration;
    }

    public Styler enableAnimation(long j) {
        enableAnimation(j, new LinearInterpolator());
        return this;
    }

    public Styler enableAnimation(long j, Interpolator interpolator) {
        this.enableAnimation = true;
        this.animationDuration = j;
        this.interpolator = interpolator;
        return this;
    }

    public Styler disableAnimation() {
        this.enableAnimation = false;
        this.animationDuration = 0L;
        return this;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public Styler setBrightness(int i) {
        if (i <= 255) {
            if (i < -255) {
                throw new IllegalArgumentException("brightness can't be smaller than -255");
            }
            this.brightness = i;
            return this;
        }
        throw new IllegalArgumentException("brightness can't be bigger than 255");
    }

    public float getContrast() {
        return this.contrast;
    }

    public Styler setContrast(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("contrast can't be smaller than 0");
        }
        this.contrast = f;
        return this;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public Styler setSaturation(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("saturation can't be smaller than 0");
        }
        this.mode = 0;
        this.saturation = f;
        return this;
    }

    public int getMode() {
        return this.mode;
    }

    public Styler setMode(int i) {
        if (!Mode.hasMode(i)) {
            throw new IllegalArgumentException("Mode " + i + " not supported! Check Styler.Mode class for supported modes");
        }
        this.mode = i;
        if (i != 0) {
            this.saturation = 1.0f;
        }
        return this;
    }

    public Drawable getDrawable() {
        return this.drawableHolder.getDrawable();
    }

    public AnimationListener getAnimationListener() {
        return this.listener;
    }

    public Styler setAnimationListener(AnimationListener animationListener) {
        this.listener = animationListener;
        return this;
    }

    public AnimationListener removeAnimationListener() {
        AnimationListener animationListener = this.listener;
        this.listener = null;
        return animationListener;
    }

    public Bitmap getBitmap() {
        Drawable drawable = this.drawableHolder.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if ((intrinsicWidth == 0 || intrinsicHeight == 0) && this.drawableHolder.isView && this.drawableHolder.view != null) {
            intrinsicWidth = this.drawableHolder.view.getMeasuredWidth();
            intrinsicHeight = this.drawableHolder.view.getMeasuredHeight();
        }
        return getBitmap(intrinsicWidth, intrinsicHeight);
    }

    public Bitmap getBitmap(int i, int i2) {
        Drawable newDrawable = this.drawableHolder.getDrawable().mutate().getConstantState().newDrawable();
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        newDrawable.setBounds(0, 0, i, i2);
        newDrawable.draw(canvas);
        return createBitmap;
    }

    public static Bitmap addStyleToBitmap(Context context, Bitmap bitmap, int i) {
        return addStyleToBitmap(context, bitmap, i, 0, 1.0f, 1.0f);
    }

    public static Bitmap addStyleToBitmap(Context context, Bitmap bitmap, int i, int i2, float f, float f2) {
        if (f2 == 1.0f || (i == 0 && i == -1)) {
            if (i2 <= 255) {
                if (i2 >= -255) {
                    if (f >= 0.0f) {
                        if (f2 < 0.0f) {
                            throw new IllegalArgumentException("saturation can't be smaller than 0");
                        }
                        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getApplicationContext().getResources(), bitmap);
                        bitmapDrawable.setColorFilter(new ColorMatrixColorFilter(calculateMatrix(i, i2, f, f2)));
                        bitmapDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        bitmapDrawable.draw(canvas);
                        return createBitmap;
                    }
                    throw new IllegalArgumentException("contrast can't be smaller than 0");
                }
                throw new IllegalArgumentException("brightness can't be smaller than -255");
            }
            throw new IllegalArgumentException("brightness can't be bigger than 255");
        }
        throw new IllegalArgumentException("saturation must be 1.0 when mode is not Styler.Mode.SATURATION");
    }

    /* loaded from: classes2.dex */
    public static class DrawableHolder {
        private Drawable drawable;
        private boolean isView = false;
        private View view;

        public DrawableHolder(Drawable drawable) {
            this.drawable = drawable;
        }

        public DrawableHolder(View view) {
            this.view = view;
        }

        public Drawable getDrawable() {
            if (this.isView) {
                View view = this.view;
                if ((view instanceof ImageView) && ((ImageView) view).getDrawable() != null) {
                    return ((ImageView) this.view).getDrawable();
                }
                return this.view.getBackground();
            }
            return this.drawable;
        }
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private DrawableHolder drawableHolder;
        private Interpolator interpolator;
        private AnimationListener listener;
        private int mode;
        private boolean enableAnimation = false;
        private long animationDuration = 0;
        private int brightness = 0;
        private float contrast = 1.0f;
        private float saturation = 1.0f;

        public Styler build() {
            return new Styler(this);
        }

        public Builder(View view, int i) {
            this.mode = -1;
            if (view == null) {
                throw new NullPointerException("view can not be null");
            }
            this.mode = i;
            this.drawableHolder = new DrawableHolder(view);
        }

        public Builder(Drawable drawable, int i) {
            this.mode = -1;
            if (drawable == null) {
                throw new NullPointerException("drawable can not be null");
            }
            this.mode = i;
            this.drawableHolder = new DrawableHolder(drawable);
        }

        public Builder enableAnimation(long j) {
            enableAnimation(j, new LinearInterpolator());
            return this;
        }

        public Builder enableAnimation(long j, Interpolator interpolator) {
            this.enableAnimation = true;
            this.animationDuration = j;
            this.interpolator = interpolator;
            return this;
        }

        public Builder disableAnimation() {
            this.enableAnimation = false;
            this.animationDuration = 0L;
            this.interpolator = null;
            this.listener = null;
            return this;
        }

        public Builder setBrightness(int i) {
            if (i <= 255) {
                if (i < -255) {
                    throw new IllegalArgumentException("brightness can't be smaller than -255");
                }
                this.brightness = i;
                return this;
            }
            throw new IllegalArgumentException("brightness can't be bigger than 255");
        }

        public Builder setContrast(float f) {
            if (f < 0.0f) {
                throw new IllegalArgumentException("contrast can't be smaller than 0");
            }
            this.contrast = f;
            return this;
        }

        public Builder setSaturation(float f) {
            if (f < 0.0f) {
                throw new IllegalArgumentException("saturation can't be smaller than 0");
            }
            this.mode = 0;
            this.saturation = f;
            return this;
        }

        public Builder setAnimationListener(AnimationListener animationListener) {
            this.listener = animationListener;
            return this;
        }
    }
}
