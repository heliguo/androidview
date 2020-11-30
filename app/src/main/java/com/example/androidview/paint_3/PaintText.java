package com.example.androidview.paint_3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/11/30 17:21
 * @description 自定义 View drawText() 文字的绘制
 */
public class PaintText extends View {

    public PaintText(Context context) {
        super(context);
    }

    public PaintText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
