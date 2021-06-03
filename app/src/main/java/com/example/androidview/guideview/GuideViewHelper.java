package com.example.androidview.guideview;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author lgh on 2020/12/19 15:49
 * @description
 */
public class GuideViewHelper {

    private static final String TAG = "GuideViewHelper";

    private static final float defaultMultiple = 1f;

    private float widthMultiple;//宽度
    private float heightMultiple;

    private View mTargetView;//源view
    private ViewGroup mTargetParent;//源view父布局
    private ViewGroup.LayoutParams mTargetLayoutParams;
    private int mTargetIndex = -1;

    private FrameLayout mReplaceFl;//替换FrameLayout
    private FrameLayout.LayoutParams mReplaceLayoutParams;

    private View mSourceView;//tip view

    public GuideViewHelper() {
        this(defaultMultiple);
    }

    public GuideViewHelper(float multiple) {
        this(multiple, multiple);

    }

    public GuideViewHelper(float widthMultiple, float heightMultiple) {
        this.heightMultiple = heightMultiple;
        this.widthMultiple = widthMultiple;
    }


    public void show(View targetView, int drawable) {
        if (targetView == null || drawable == 0)
            return;
        ImageView imageView = new ImageView(targetView.getContext());
        imageView.setBackgroundResource(drawable);
        show(targetView, imageView);
    }

    public void show(View targetView, View sourceView) {
        if (targetView == null || sourceView == null)
            return;

        mTargetView = targetView;
        mSourceView = sourceView;

        mTargetView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            int height = mTargetView.getHeight();
            int width = mTargetView.getWidth();

            @Override
            public boolean onPreDraw() {
                if (height != 0 && width != 0) {
                    Log.d(TAG, "height: " + height + "\nwidth:  " + width);
                    ViewParent parent = mTargetView.getParent();
                    Context context = mTargetView.getContext();
                    if (parent instanceof ViewGroup) {
                        mTargetParent = (ViewGroup) parent;
                        mTargetParent.setClipChildren(false);
                        mTargetParent.setClipToPadding(false);
                        mTargetIndex = mTargetParent.indexOfChild(mTargetView);
                        mTargetLayoutParams = mTargetView.getLayoutParams();
                        mTargetLayoutParams.height = height;
                        mTargetLayoutParams.width = width;
                        mTargetParent.removeView(mTargetView);

                        mReplaceFl = new FrameLayout(context);
                        mReplaceFl.setClipChildren(false);
                        mReplaceFl.setClipToPadding(false);

                        mReplaceFl.addView(mTargetView);

                        if (mReplaceLayoutParams == null) {
                            mReplaceLayoutParams = new FrameLayout.LayoutParams(((int) (width * widthMultiple)), ((int) (height * heightMultiple)));
                            mReplaceLayoutParams.gravity = Gravity.CENTER;
                        }

                        mReplaceFl.addView(mSourceView, mReplaceLayoutParams);
                        mTargetParent.addView(mReplaceFl, mTargetIndex, mTargetLayoutParams);
                        if (mOnViewLayoutListener != null) {
                            mOnViewLayoutListener.layout();
                        }
                    }
                    mTargetView.getViewTreeObserver().removeOnPreDrawListener(this);
                } else {
                    height = mTargetView.getHeight();
                    width = mTargetView.getWidth();
                }
                return false;
            }
        });

    }

    public void remove() {
        mReplaceFl.removeAllViews();
        mTargetParent.addView(mTargetView, mTargetIndex, mTargetLayoutParams);
        mReplaceFl = null;
        mSourceView = null;
    }

    public void hide() {
        mSourceView.setVisibility(View.GONE);
    }

    public float getWidthMultiple() {
        return widthMultiple;
    }

    public void setWidthMultiple(float widthMultiple) {
        this.widthMultiple = widthMultiple;
    }

    public float getHeightMultiple() {
        return heightMultiple;
    }

    public void setHeightMultiple(float heightMultiple) {
        this.heightMultiple = heightMultiple;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public ViewGroup getTargetParent() {
        return mTargetParent;
    }

    public ViewGroup.LayoutParams getTargetLayoutParams() {
        return mTargetLayoutParams;
    }

    public void setTargetLayoutParams(ViewGroup.LayoutParams targetLayoutParams) {
        mTargetLayoutParams = targetLayoutParams;
    }

    public int getTargetIndex() {
        return mTargetIndex;
    }


    public FrameLayout getReplaceFl() {
        return mReplaceFl;
    }


    public FrameLayout.LayoutParams getReplaceLayoutParams() {
        return mReplaceLayoutParams;
    }

    public void setReplaceLayoutParams(FrameLayout.LayoutParams replaceLayoutParams) {
        mReplaceLayoutParams = replaceLayoutParams;
    }

    public View getSourceView() {
        return mSourceView;
    }

    private OnViewLayoutListener mOnViewLayoutListener;

    public void setOnViewLayoutListener(OnViewLayoutListener onViewLayoutListener) {
        mOnViewLayoutListener = onViewLayoutListener;
    }

    public interface OnViewLayoutListener {

        void layout();

    }

}
