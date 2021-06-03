package com.example.androidview.smarttablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.androidview.R;

/**
 * @author lgh on 2021/3/2 17:36
 * @description
 */
public class TintableTextView extends AppCompatTextView {

    private ColorStateList tint;

    public TintableTextView(@NonNull Context context) {
        this(context,null);
    }

    public TintableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TintableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.TintableTextView, defStyle, 0);
        tint = a.getColorStateList(
                R.styleable.TintableTextView_tints);
        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (tint != null && tint.isStateful()) {
            updateTintColor();
        }
    }

    private void updateTintColor() {
        int color = tint.getColorForState(getDrawableState(), 0);
        setTextColor(color);
    }
}
