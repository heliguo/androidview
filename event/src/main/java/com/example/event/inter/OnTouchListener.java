package com.example.event.inter;

import com.example.event.MotionEvent;
import com.example.event.View;

/**
 * @author lgh on 2021/4/14 17:15
 * @description
 */
public interface OnTouchListener {

    boolean onTouch(View view, MotionEvent event);

}
