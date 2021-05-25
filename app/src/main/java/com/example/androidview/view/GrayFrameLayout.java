package com.example.androidview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/5/25 14:31
 * @description
 */
public class GrayFrameLayout extends FrameLayout {
    private final Paint mPaint = new Paint();

    public GrayFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public GrayFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GrayFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);//置灰
        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        canvas.restore();
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

}

