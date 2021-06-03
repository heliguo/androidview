package com.example.androidview.toobar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/4/16 15:18
 * @description
 */
public class StatusBarVIew extends View {

    public StatusBarVIew(Context context) {
        this(context,null);
    }

    public StatusBarVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatusBarVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
