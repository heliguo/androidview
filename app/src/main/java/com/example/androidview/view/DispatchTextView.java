package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author lgh on 2020/8/25:23:22
 * @description
 */
public class DispatchTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = "DispatchTextView";

    public DispatchTextView(Context context) {
        super(context);
    }

    public DispatchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
