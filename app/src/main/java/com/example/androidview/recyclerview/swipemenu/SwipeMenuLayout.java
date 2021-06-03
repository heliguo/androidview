/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androidview.recyclerview.swipemenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.core.view.ViewCompat;

import com.example.androidview.R;


/**
 * menu layout
 */
public class SwipeMenuLayout extends FrameLayout {

    public static final int DEFAULT_SCROLLER_DURATION = 200;

    private int mLeftViewId = 0;
    private int mContentViewId = 0;
    private int mRightViewId = 0;

    private float mOpenPercent = 0.5f;
    private int mScrollerDuration = DEFAULT_SCROLLER_DURATION;

    private final int mScaledTouchSlop;

    private int mLastX;
    private int mLastY;

    private View mContentView;

    private View mLeftMenuView;//左菜单
    private View mRightMenuView;//右菜单
    private View mCurrentMenuView;//当前打开菜单

    private boolean shouldResetSwipe;//已有菜单打开的情况需要先关闭

    private boolean swipeEnable = true;

    private final OverScroller mScroller;


    public SwipeMenuLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClickable(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout);
        mLeftViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_leftViewId, mLeftViewId);
        mContentViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_contentViewId, mContentViewId);
        mRightViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_rightViewId, mRightViewId);
        typedArray.recycle();

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mScaledTouchSlop = configuration.getScaledTouchSlop();

        mScroller = new OverScroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mLeftViewId != 0 && mLeftMenuView == null) {
            mLeftMenuView = findViewById(mLeftViewId);
        }
        if (mRightViewId != 0 && mRightMenuView == null) {
            mRightMenuView = findViewById(mRightViewId);
        }
        if (mContentViewId != 0 && mContentView == null) {
            mContentView = findViewById(mContentViewId);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(16);
            String errorText = "You may not have set the ContentView.";
            errorView.setText(errorText);
            mContentView = errorView;
            addView(mContentView);
        }
    }

    /**
     * Set whether open swipe. Default is true.
     *
     * @param swipeEnable true open, otherwise false.
     */
    public void setSwipeEnable(boolean swipeEnable) {
        this.swipeEnable = swipeEnable;
    }

    /**
     * Open the swipe function of the Item?
     *
     * @return open is true, otherwise is false.
     */
    public boolean isSwipeEnable() {
        return swipeEnable;
    }

    /**
     * Set open percentage.
     *
     * @param openPercent such as 0.5F.
     */
    public void setOpenPercent(float openPercent) {
        this.mOpenPercent = openPercent;
    }

    /**
     * Get open percentage.
     *
     * @return such as 0.5F.
     */
    public float getOpenPercent() {
        return mOpenPercent;
    }

    /**
     * The duration of the set.
     *
     * @param scrollerDuration such as 500.
     */
    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }

    private boolean remove;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isSwipeEnable()) {
            smoothCloseMenu();
            return super.onTouchEvent(ev);
        }

        int offsetX;
        int offsetY;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (SwipeMenuObserver.getInstance().closeMenu()) {
                    remove = true;
                    return true;
                } else {
                    remove = false;
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                if (remove)
                    return true;
                offsetX = (int) (mLastX - ev.getX());
                offsetY = (int) (mLastY - ev.getY());

                if (Math.abs(offsetX) > Math.abs(offsetY) && Math.abs(offsetX) > mScaledTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    return super.onTouchEvent(ev);
                }
                if (mCurrentMenuView == null || shouldResetSwipe) {
                    if (offsetX < 0) {
                        if (mLeftMenuView != null) {
                            mCurrentMenuView = mLeftMenuView;
                        } else {
                            mCurrentMenuView = mRightMenuView;
                        }
                    } else {
                        if (mRightMenuView != null) {
                            mCurrentMenuView = mRightMenuView;
                        } else {
                            mCurrentMenuView = mLeftMenuView;
                        }
                    }
                }
                scrollBy(offsetX, 0);
                shouldResetSwipe = false;
                break;
            case MotionEvent.ACTION_UP:
                if (remove)
                    return true;
                offsetX = (int) (mLastX - ev.getX());
                if (Math.abs(offsetX) > 0) {
                    if (mCurrentMenuView != null) {
                        if (mCurrentMenuView == mRightMenuView) {
                            if (offsetX < 0) {
                                smoothCloseMenu();
                            } else {
                                smoothOpenMenu();
                            }
                        } else {
                            if (offsetX > 0) {
                                smoothCloseMenu();
                            } else {
                                smoothOpenMenu();
                            }
                        }
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                } else {
                    judgeOpenClose(offsetX);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void judgeOpenClose(int dx) {
        if (Math.abs(dx) < mScaledTouchSlop) {
            return;
        }
        if (mCurrentMenuView != null) {
            if (Math.abs(getScrollX()) >= (mCurrentMenuView.getWidth() * mOpenPercent)) { // auto open
                if (isMenuOpen()) {
                    smoothCloseMenu();
                } else {
                    smoothOpenMenu();
                }
            } else { // auto closeMenu
                smoothCloseMenu();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mCurrentMenuView == null) {
            super.scrollTo(x, y);
        } else {
            int scrollX = checkX(mCurrentMenuView, x);
            shouldResetSwipe = x == 0;
            if (scrollX != getScrollX()) {
                super.scrollTo(scrollX, 0);
            }
        }
    }

    private int checkX(View currentMenuView, int x) {
        if (currentMenuView == mLeftMenuView) {
            if (x > 0) {
                x = 0;
            }
            if (x <= -currentMenuView.getWidth()) {
                x = -currentMenuView.getWidth();
            }
        } else if (currentMenuView == mRightMenuView) {
            if (x < 0) {
                x = 0;
            }
            if (x > currentMenuView.getWidth()) {
                x = currentMenuView.getWidth();
            }
        } else {
            throw new IllegalArgumentException("you should make sure the menu orientation");
        }
        return x;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset() && mCurrentMenuView != null) {
            scrollTo((mCurrentMenuView == mRightMenuView) ? Math.abs(mScroller.getCurrX()) :
                    -Math.abs(mScroller.getCurrX()), 0);
            invalidate();
        }
    }

    /**
     * 是否存在 左菜单
     */
    public boolean hasLeftMenu() {
        return mLeftMenuView != null;
    }

    /**
     * 是否存在 右菜单
     */
    public boolean hasRightMenu() {
        return mRightMenuView != null;
    }

    /**
     * 是否存在打开菜单
     */
    public boolean isMenuOpen() {
        return isLeftMenuOpen() || isRightMenuOpen();
    }

    /**
     * 左菜单是否打开
     */
    public boolean isLeftMenuOpen() {
        return isMenuOpen(mLeftMenuView);
    }

    /**
     * 右菜单是否打开
     */
    public boolean isRightMenuOpen() {
        return isMenuOpen(mRightMenuView);
    }

    private boolean isMenuOpen(View view) {
        return view != null && view.getWidth() > 0 && Math.abs(getScrollX()) >= view.getWidth();
    }

    private void smoothOpenMenu() {
        smoothOpenMenu(mScrollerDuration);
    }

    public void smoothOpenLeftMenu() {
        smoothOpenLeftMenu(mScrollerDuration);
    }

    public void smoothOpenRightMenu() {
        smoothOpenRightMenu(mScrollerDuration);
    }

    public void smoothOpenLeftMenu(int duration) {
        if (mLeftMenuView != null && !isLeftMenuOpen()) {
            if (isRightMenuOpen()) {
                smoothCloseRightMenu();
            }
            mCurrentMenuView = mLeftMenuView;
            smoothOpenMenu(duration);
        }
    }

    public void smoothOpenRightMenu(int duration) {
        if (mRightMenuView != null && !isRightMenuOpen()) {
            if (isLeftMenuOpen()) {
                smoothCloseLeftMenu();
            }
            mCurrentMenuView = mRightMenuView;
            smoothOpenMenu(duration);
        }
    }

    private void smoothOpenMenu(int duration) {
        if (mCurrentMenuView != null) {
            autoOpenMenu(mCurrentMenuView, mScroller, getScrollX(), duration);
            invalidate();
            SwipeMenuObserver.getInstance().getSwipeMenuLayouts().add(this);
        }
    }


    public void smoothCloseMenu() {
        smoothCloseMenu(mScrollerDuration);
    }

    public void smoothCloseLeftMenu() {
        if (mLeftMenuView != null && isLeftMenuOpen()) {
            mCurrentMenuView = mLeftMenuView;
            smoothCloseMenu();
        }
    }

    public void smoothCloseRightMenu() {
        if (mRightMenuView != null && isRightMenuOpen()) {
            mCurrentMenuView = mRightMenuView;
            smoothCloseMenu();
        }
    }

    public void smoothCloseMenu(int duration) {
        if (mCurrentMenuView != null) {
            autoCloseMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    private void autoOpenMenu(View currentMenuView, OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(Math.abs(scrollX), 0, currentMenuView.getWidth() - Math.abs(scrollX), 0, duration);
    }

    private void autoCloseMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(-Math.abs(scrollX), 0, Math.abs(scrollX), 0, duration);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mContentView != null) {
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            int contentViewHeight = mContentView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
            int start = getPaddingLeft() + lp.leftMargin;
            int top = getPaddingTop() + lp.topMargin;
            mContentView.layout(start, top, start + contentViewWidth, top + contentViewHeight);
        }

        if (mLeftMenuView != null) {
            int menuViewWidth = mLeftMenuView.getMeasuredWidthAndState();
            int menuViewHeight = mLeftMenuView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mLeftMenuView.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;
            mLeftMenuView.layout(-menuViewWidth, top, 0, top + menuViewHeight);
        }

        if (mRightMenuView != null) {
            int menuViewWidth = mRightMenuView.getMeasuredWidthAndState();
            int menuViewHeight = mRightMenuView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mRightMenuView.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;

            int parentViewWidth = getMeasuredWidthAndState();
            mRightMenuView.layout(parentViewWidth, top, parentViewWidth + menuViewWidth, top + menuViewHeight);
        }
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public void setContentView(@IdRes int contentViewId) {
        mContentView = findViewById(contentViewId);
    }

    public void setLeftMenuView(View leftMenuView) {
        mLeftMenuView = leftMenuView;
    }

    public void setLeftMenuView(@IdRes int leftMenuViewId) {
        mLeftMenuView = findViewById(leftMenuViewId);
    }

    public void setRightMenuView(View rightMenuView) {
        mRightMenuView = rightMenuView;
    }

    public void setRightMenuView(@IdRes int rightMenuViewId) {
        mRightMenuView = findViewById(rightMenuViewId);
    }
}