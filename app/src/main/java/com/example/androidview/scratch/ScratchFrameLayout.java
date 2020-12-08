package com.example.androidview.scratch;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.androidview.R;

/**
 * @author lgh on 2020/12/7 19:11
 * @description
 */
public class ScratchFrameLayout extends FrameLayout {

    private ScratchView mScratchView;

    public ScratchFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public ScratchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchFrameLayout, defStyleAttr, 0);
        initScratch(typedArray);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScratchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchFrameLayout, defStyleAttr, defStyleRes);
        initScratch(typedArray);
    }

    private void initScratch(TypedArray typedArray) {

        int maskColor = typedArray.getColor(R.styleable.ScratchFrameLayout_maskColor, -1);
        int watermarkResId = typedArray.getResourceId(R.styleable.ScratchFrameLayout_watermark, -1);
        float eraseSize = typedArray.getFloat(R.styleable.ScratchFrameLayout_eraseSize, 0);
        int maxPercent = typedArray.getInt(R.styleable.ScratchFrameLayout_maxPercent, 0);
        typedArray.recycle();

        mScratchView = new ScratchView(getContext());
        post(() -> {
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                int measuredHeight = childAt.getMeasuredHeight();
                int measuredWidth = childAt.getMeasuredWidth();
                mScratchView.setSize(measuredWidth, measuredHeight);
            }
            if (maskColor != -1) {
                mScratchView.setMaskColor(maskColor);
            }
            if (watermarkResId != -1) {
                mScratchView.setWatermark(watermarkResId);
            }
            if (eraseSize != 0) {
                mScratchView.setEraserSize(eraseSize);
            }
            if (maxPercent != 0) {
                mScratchView.setMaxPercent(maxPercent);
            }
            addView(mScratchView);
        });
    }

    public void setOnEraseStatusListener(ScratchView.OnEraseStatusListener listener) {
        if (mScratchView != null) {
            mScratchView.setOnEraseStatusListener(listener);
        }
    }

    public void setCompleted(boolean completed){
        if (mScratchView != null) {
            mScratchView.setCompleted(completed);
        }
    }

    public void clear(){
        if (mScratchView != null) {
            mScratchView.clear();
        }
    }
}
