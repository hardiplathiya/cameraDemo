package plant.testtree.camerademo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.OverScroller;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.List;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.activity.CameraActivity;


public class WheelView extends View implements GestureDetector.OnGestureListener {
    public static final float DEFAULT_INTERVAL_FACTOR = 1.2f;
    public static final float DEFAULT_MARK_RATIO = 0.7f;
    private boolean changed;
    private String mAdditionCenterMark;
    private float mAdditionCenterMarkWidth;
    private float mBottomSpace;
    public int mCenterIndex;
    private Path mCenterIndicatorPath;
    private float mCenterMarkWidth;
    private float mCenterTextSize;
    private RectF mContentRectF;
    private float mCursorSize;
    private int mFadeMarkColor;
    private boolean mFling;
    public GestureDetectorCompat mGestureDetectorCompat;
    private int mHeight;
    private int mHighlightColor;
    public float mIntervalDis;
    private float mIntervalFactor;
    private List<String> mItems;
    private int mLastSelectedIndex;
    private int mMarkColor;
    private int mMarkCount;
    private Paint mMarkPaint;
    private float mMarkRatio;
    private int mMarkTextColor;
    private TextPaint mMarkTextPaint;
    private float mMarkWidth;
    public float mMaxOverScrollDistance;
    private int mMaxSelectableIndex;
    private int mMinSelectableIndex;
    private float mNormalTextSize;
    private OnWheelItemSelectedListener mOnWheelItemSelectedListener;
    private OverScroller mScroller;
    private float mTopSpace;
    public int mViewScopeSize;

   
    public interface OnWheelItemSelectedListener {
        void onWheelItemChanged(WheelView wheelView, int i);

        void onWheelItemSelected(WheelView wheelView, int i);
    }

    @Override 
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override 
    public void onShowPress(MotionEvent motionEvent) {
    }

   
    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() { // from class: com.cameraediter.iphone11pro.WheelView.SavedState.1
            @Override 
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override 
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int index;
        int max;
        int min;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.index = parcel.readInt();
            this.min = parcel.readInt();
            this.max = parcel.readInt();
        }

        @Override 
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.index);
            parcel.writeInt(this.min);
            parcel.writeInt(this.max);
        }

        public String toString() {
            return "WheelView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " index=" + this.index + " min=" + this.min + " max=" + this.max + "}";
        }
    }

    public WheelView(Context context) {
        super(context);
        this.changed = false;
        this.mCenterIndex = -1;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mIntervalFactor = 1.2f;
        this.mLastSelectedIndex = -1;
        this.mMarkRatio = 0.7f;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        init(null);
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.changed = false;
        this.mCenterIndex = -1;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mIntervalFactor = 1.2f;
        this.mLastSelectedIndex = -1;
        this.mMarkRatio = 0.7f;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        init(attributeSet);
    }

    public WheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.changed = false;
        this.mCenterIndex = -1;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mIntervalFactor = 1.2f;
        this.mLastSelectedIndex = -1;
        this.mMarkRatio = 0.7f;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        init(attributeSet);
    }

    @SuppressLint("ResourceType")
    public void init(AttributeSet attributeSet) {
        float f = getResources().getDisplayMetrics().density;
        this.mCenterMarkWidth = (int) ((1.5f * f) + 0.5f);
        this.mMarkWidth = f;
        this.mHighlightColor = -145919;
        this.mMarkTextColor = -1;
        this.mMarkColor = -1118482;
        this.mCursorSize = f * 1.0f;
        float f2 = 13.0f * f;
        this.mCenterTextSize = f2;
        this.mNormalTextSize = f2;
        this.mBottomSpace = 6.0f * f;
        TypedArray obtainStyledAttributes = attributeSet == null ? null : getContext().obtainStyledAttributes(attributeSet, R.styleable.WheelView);
        if (obtainStyledAttributes != null) {
            this.mHighlightColor = obtainStyledAttributes.getColor(0, this.mHighlightColor);
            this.mMarkTextColor = obtainStyledAttributes.getColor(2, this.mMarkTextColor);
            this.mMarkColor = obtainStyledAttributes.getColor(1, this.mMarkColor);
            this.mIntervalFactor = obtainStyledAttributes.getFloat(3, this.mIntervalFactor);
            this.mMarkRatio = obtainStyledAttributes.getFloat(4, this.mMarkRatio);
            this.mAdditionCenterMark = obtainStyledAttributes.getString(8);
            this.mCenterTextSize = obtainStyledAttributes.getDimension(7, this.mCenterTextSize);
            this.mNormalTextSize = obtainStyledAttributes.getDimension(6, this.mNormalTextSize);
            this.mCursorSize = obtainStyledAttributes.getDimension(5, this.mCursorSize);
        }
        this.mFadeMarkColor = this.mHighlightColor & (-1426063361);
        this.mIntervalFactor = Math.max(1.0f, this.mIntervalFactor);
        this.mMarkRatio = Math.min(1.0f, this.mMarkRatio);
        this.mTopSpace = this.mCursorSize + (f * 2.0f);
        this.mMarkPaint = new Paint(1);
        this.mMarkTextPaint = new TextPaint(1);
        this.mMarkTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mMarkTextPaint.setColor(this.mHighlightColor);
        this.mMarkPaint.setColor(this.mMarkColor);
        this.mMarkPaint.setStrokeWidth(this.mCenterMarkWidth);
        this.mMarkTextPaint.setTextSize(this.mCenterTextSize);
        calcIntervalDis();
        this.mScroller = new OverScroller(getContext());
        this.mContentRectF = new RectF();
        this.mGestureDetectorCompat = new GestureDetectorCompat(getContext(), this);
        selectIndex(0);
    }

    private void calcIntervalDis() {
        int width;
        if (this.mMarkTextPaint != null) {
            Rect rect = new Rect();
            List<String> list = this.mItems;
            if (list == null || list.size() <= 0) {
                this.mMarkTextPaint.getTextBounds("888888", 0, 6, rect);
                width = rect.width();
            } else {
                width = 0;
                for (String str : this.mItems) {
                    this.mMarkTextPaint.getTextBounds(str, 0, str.length(), rect);
                    if (rect.width() > width) {
                        width = rect.width();
                    }
                }
            }
            if (!TextUtils.isEmpty(this.mAdditionCenterMark)) {
                this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                TextPaint textPaint = this.mMarkTextPaint;
                String str2 = this.mAdditionCenterMark;
                textPaint.getTextBounds(str2, 0, str2.length(), rect);
                this.mAdditionCenterMarkWidth = rect.width();
                width += rect.width();
            }
            if (width < CameraUtil.dpToPixel(52)) {
                width = CameraUtil.dpToPixel(52);
            }
            this.mIntervalDis = width * this.mIntervalFactor;
        }
    }

    @Override 
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureWidth(int i) {
        int mode = MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? MeasureSpec.getSize(i) : getSuggestedMinimumWidth();
    }

    private int measureHeight(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int i2 = (int) (this.mBottomSpace + (this.mTopSpace * 2.0f) + this.mCenterTextSize);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(i2, size);
        }
        return mode != 1073741824 ? i2 : Math.max(i2, size);
    }

    public void fling(int i, int i2) {
        OverScroller overScroller = this.mScroller;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i3 = (int) ((-this.mMaxOverScrollDistance) + (this.mMinSelectableIndex * this.mIntervalDis));
        float width = this.mContentRectF.width();
        float f = this.mMaxOverScrollDistance;
        overScroller.fling(scrollX, scrollY, i, i2, i3, (int) ((width - f) - (((this.mMarkCount - 1) - this.mMaxSelectableIndex) * this.mIntervalDis)), 0, 0, (int) f, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override 
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        this.mHeight = i2;
        this.mMaxOverScrollDistance = i / 2.0f;
        this.mContentRectF.set(0.0f, 0.0f, (this.mMarkCount - 1) * this.mIntervalDis, i2);
        this.mViewScopeSize = (int) Math.ceil(this.mMaxOverScrollDistance / this.mIntervalDis);
    }

    @Override 
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mMarkPaint.setColor(this.mHighlightColor);
        int i = this.mCenterIndex;
        int i2 = this.mViewScopeSize;
        int max = Math.max(i - i2, (-i2) * 2);
        int min = Math.min(i + i2 + 1, this.mMarkCount + (this.mViewScopeSize * 2));
        int i3 = this.mCenterIndex;
        if (i3 == this.mMaxSelectableIndex) {
            min += this.mViewScopeSize;
        } else if (i3 == this.mMinSelectableIndex) {
            max -= this.mViewScopeSize;
        }
        float f = max * this.mIntervalDis;
        float f2 = ((this.mHeight - this.mBottomSpace) - this.mCenterTextSize) - this.mTopSpace;
        Math.min((f2 - this.mMarkWidth) / 2.0f, ((1.0f - this.mMarkRatio) * f2) / 2.0f);
        while (max < min) {
            float f3 = this.mIntervalDis;
            for (int i4 = -2; i4 < 3; i4++) {
                if (max < 0 || max > this.mMarkCount || this.mCenterIndex != max) {
                    this.mMarkPaint.setColor(this.mMarkColor);
                } else {
                    int abs = Math.abs(i4);
                    if (abs == 0) {
                        this.mMarkPaint.setColor(this.mHighlightColor);
                    } else if (abs == 1) {
                        this.mMarkPaint.setColor(this.mFadeMarkColor);
                    } else {
                        this.mMarkPaint.setColor(this.mMarkColor);
                    }
                }
                if (i4 == 0) {
                    this.mMarkPaint.setStrokeWidth(this.mCenterMarkWidth);
                } else {
                    this.mMarkPaint.setStrokeWidth(this.mMarkWidth);
                }
            }
            int i5 = this.mMarkCount;
            if (i5 > 0 && max >= 0 && max < i5) {
                String str = this.mItems.get(max);
                if (this.mCenterIndex == max) {
                    this.mMarkTextPaint.setColor(this.mHighlightColor);
                    this.mMarkTextPaint.setTextSize(this.mCenterTextSize);
                    if (TextUtils.isEmpty(this.mAdditionCenterMark)) {
                        canvas.drawText((CharSequence) str, 0, str.length(), f, this.mHeight - this.mBottomSpace, (Paint) this.mMarkTextPaint);
                    } else {
                        float measureText = this.mMarkTextPaint.measureText((CharSequence) str, 0, str.length());
                        canvas.drawText((CharSequence) str, 0, str.length(), f - (this.mAdditionCenterMarkWidth / 2.0f), this.mHeight - this.mBottomSpace, (Paint) this.mMarkTextPaint);
                        this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                        canvas.drawText(this.mAdditionCenterMark, (measureText / 2.0f) + f, this.mHeight - this.mBottomSpace, this.mMarkTextPaint);
                    }
                } else {
                    this.mMarkTextPaint.setColor(this.mMarkTextColor);
                    this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                    canvas.drawText((CharSequence) str, 0, str.length(), f, this.mHeight - this.mBottomSpace, (Paint) this.mMarkTextPaint);
                }
            }
            f += this.mIntervalDis;
            max++;
        }
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        List<String> list = this.mItems;
        if (list == null || list.size() == 0 || !isEnabled()) {
            return false;
        }
        boolean onTouchEvent = this.mGestureDetectorCompat.onTouchEvent(motionEvent);
        if (!this.mFling && 1 == motionEvent.getAction()) {
            autoSettle();
            onTouchEvent = true;
        }
        return onTouchEvent || super.onTouchEvent(motionEvent);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            refreshCenter();
            invalidate();
        } else if (this.mFling) {
            this.mFling = false;
            autoSettle();
        }
    }

    public void setAdditionCenterMark(String str) {
        this.mAdditionCenterMark = str;
        calcIntervalDis();
        invalidate();
    }

    private void autoSettle() {
        int scrollX = getScrollX();
        this.mScroller.startScroll(scrollX, 0, (int) (((this.mCenterIndex * this.mIntervalDis) - scrollX) - this.mMaxOverScrollDistance), 0);
        postInvalidate();
        int i = this.mLastSelectedIndex;
        int i2 = this.mCenterIndex;
        if (i != i2) {
            this.changed = true;
            this.mLastSelectedIndex = i2;
            OnWheelItemSelectedListener onWheelItemSelectedListener = this.mOnWheelItemSelectedListener;
            if (onWheelItemSelectedListener != null) {
                onWheelItemSelectedListener.onWheelItemSelected(this, i2);
            }
        }
    }

    public int safeCenter(int i) {
        int i2 = this.mMinSelectableIndex;
        if (i < i2) {
            return i2;
        }
        int i3 = this.mMaxSelectableIndex;
        return i > i3 ? i3 : i;
    }

    private void refreshCenter(int i) {
        int safeCenter = safeCenter(Math.round(((int) (i + this.mMaxOverScrollDistance)) / this.mIntervalDis));
        if (this.mCenterIndex != safeCenter) {
            this.mCenterIndex = safeCenter;
            this.changed = true;
            OnWheelItemSelectedListener onWheelItemSelectedListener = this.mOnWheelItemSelectedListener;
            if (onWheelItemSelectedListener != null) {
                onWheelItemSelectedListener.onWheelItemChanged(this, this.mCenterIndex);
            }
        }
    }

    public void refreshCenter() {
        refreshCenter(getScrollX());
    }

    public void selectIndex(int i) {
        this.mCenterIndex = i;
        this.changed = true;

        post(() -> {
            WheelView wheelView = WheelView.this;
            wheelView.scrollTo((int) ((wheelView.mCenterIndex * WheelView.this.mIntervalDis) - WheelView.this.mMaxOverScrollDistance), 0);
            WheelView.this.invalidate();
            WheelView.this.refreshCenter();
        });
    }

    public void smoothSelectIndex(int i) {
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        int i2 = i - this.mCenterIndex;
        this.mCenterIndex = i;
        this.changed = true;
        this.mScroller.startScroll(getScrollX(), 0, (int) (i2 * this.mIntervalDis), 0);
        invalidate();
        autoSettle();
    }

    public int getMinSelectableIndex() {
        return this.mMinSelectableIndex;
    }

    public void setMinSelectableIndex(int i) {
        int i2 = this.mMaxSelectableIndex;
        if (i > i2) {
            i = i2;
        }
        this.mMinSelectableIndex = i;
        int safeCenter = safeCenter(this.mCenterIndex);
        if (safeCenter != this.mCenterIndex) {
            selectIndex(safeCenter);
        }
    }

    public int getMaxSelectableIndex() {
        return this.mMaxSelectableIndex;
    }

    public void setMaxSelectableIndex(int i) {
        int i2 = this.mMinSelectableIndex;
        if (i < i2) {
            i = i2;
        }
        this.mMaxSelectableIndex = i;
        int safeCenter = safeCenter(this.mCenterIndex);
        if (safeCenter != this.mCenterIndex) {
            selectIndex(safeCenter);
        }
    }

    public List<String> getItems() {
        return this.mItems;
    }

    public void setItems(List<String> list) {
        List<String> list2 = this.mItems;
        if (list2 == null) {
            this.mItems = new ArrayList();
        } else {
            list2.clear();
        }
        this.mItems.addAll(list);
        List<String> list3 = this.mItems;
        this.mMarkCount = list3 == null ? 0 : list3.size();
        if (this.mMarkCount > 0) {
            this.mMinSelectableIndex = Math.max(this.mMinSelectableIndex, 0);
            this.mMaxSelectableIndex = Math.min(this.mMaxSelectableIndex, this.mMarkCount - 1);
        }
        this.mContentRectF.set(0.0f, 0.0f, (this.mMarkCount - 1) * this.mIntervalDis, getMeasuredHeight());
        this.mCenterIndex = Math.min(this.mCenterIndex, this.mMarkCount);
        calcIntervalDis();
        invalidate();
    }

    public int getSelectedPosition() {
        return this.mCenterIndex;
    }

    public void setOnWheelItemSelectedListener(OnWheelItemSelectedListener onWheelItemSelectedListener) {
        this.mOnWheelItemSelectedListener = onWheelItemSelectedListener;
    }

    @Override 
    public boolean onDown(MotionEvent motionEvent) {
        this.changed = false;
        if (!this.mScroller.isFinished()) {
            this.mScroller.forceFinished(false);
        }
        this.mFling = false;
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }

    @Override 
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        playSoundEffect(SoundEffectConstants.CLICK);
        refreshCenter((int) ((getScrollX() + motionEvent.getX()) - this.mMaxOverScrollDistance));
        autoSettle();
        return true;
    }

    public boolean isChanged() {
        return this.changed;
    }

    @Override
    public boolean onScroll(MotionEvent r5, MotionEvent r6, float r7, float r8) {
        return false;
    }

    public void setChanged(boolean z) {
        this.changed = z;
    }

    @Override 
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.changed) {
            return true;
        }
        float scrollX = getScrollX();
        if (scrollX < (-this.mMaxOverScrollDistance) + (this.mMinSelectableIndex * this.mIntervalDis) || scrollX > (this.mContentRectF.width() - this.mMaxOverScrollDistance) - (((this.mMarkCount - 1) - this.mMaxSelectableIndex) * this.mIntervalDis)) {
            return false;
        }
        this.mFling = true;
        return true;
    }

    @Override 
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.index = getSelectedPosition();
        savedState.min = this.mMinSelectableIndex;
        savedState.max = this.mMaxSelectableIndex;
        return savedState;
    }

    @Override 
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mMinSelectableIndex = savedState.min;
        this.mMaxSelectableIndex = savedState.max;
        selectIndex(savedState.index);
        requestLayout();
    }

    public void addIndex(int i, CameraActivity cameraActivity, int i2) {
        safeCenter(i);
        smoothSelectIndex(safeCenter(i));
    }

    public void decIndex(int i, CameraActivity cameraActivity, int i2) {
        smoothSelectIndex(safeCenter(i));
    }
}
