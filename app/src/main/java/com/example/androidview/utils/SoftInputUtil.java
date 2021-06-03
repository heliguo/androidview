package com.example.androidview.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author lgh on 2021/3/9 10:16
 * @description 软键盘工具类
 * WindowManager.LayoutParams.softInputMode 控制键盘dialog 是否将activity布局顶上去
 * android:windowSoftInputMode="adjustResize"
 * <p>
 * SOFT_INPUT_ADJUST_UNSPECIFIED 布局被顶上去
 * 不指定调整方式，系统自行决定使用哪种调整方式
 * <p>
 * SOFT_INPUT_ADJUST_RESIZE 布局不动
 * 调整方式为布局需要重新计算大小适配当前可见区域
 * <p>
 * SOFT_INPUT_ADJUST_PAN 布局被顶上去
 * 调整方式为布局需要整体移动
 * <p>
 * SOFT_INPUT_ADJUST_NOTHING
 * 不做任何操作
 */
public class SoftInputUtil {

    /**
     * 需要传入的View是EditText 类型的。
     * 如果传入其它View，需要进行额外的操作才能弹出键盘。
     * view必须是可以获取焦点  view.requestFocus()获取焦点
     *
     * @param view EditText
     */
    public static void showSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            //flag 判断弹出键盘的类型
            //SHOW_IMPLICIT表示本次显示软键盘的请求不是来自用户的直接请求，而是隐式的请求
            //这个参数影响的并不是软键盘的显示，而是软键盘的隐藏
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    /**
     * 新创建的view，必须将其加入到当前布局中，否则获取不到windowToken
     * <p>
     * 参数	               0	SHOW_IMPLICIT	SHOW_FORCED （show）
     * 0	               T	     T           	T
     * HIDE_IMPLICIT_ONLY  F	     T	            F
     * HIDE_NOT_ALWAYS	   T	     T	            F
     * （hide）
     * </p>
     *
     * @param view
     */
    public static void hideSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 与showSoftInput()方法不同的是，使用toggleSoftInput()显示软键盘时，并不要求当前界面布局中有一个已经获取焦点的EditText，
     * 即使当前布局是完全空白的，一个View也没有（除了最外层的Layout），
     * toggleSoftInput也能够显示软键盘。不过如果没有一个已经获取焦点的EditText，那么软键盘中的按键输入都是无效的
     *
     * @param view
     */
    public static void toggleSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(0, 0);
        }
    }

}
