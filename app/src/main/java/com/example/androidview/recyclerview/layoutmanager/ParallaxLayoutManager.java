package com.example.androidview.recyclerview.layoutmanager;

import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.FloatRange;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ParallaxLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 滑动总偏移量
     */
    private int mOffsetAll = 0;

    /**
     * Item宽
     */
    private int mDecoratedChildWidth = 0;

    /**
     * Item高
     */
    private int mDecoratedChildHeight = 0;

    /**
     * Item间隔与item宽的比例
     */
    @FloatRange(from = 0.0f, to = 1.0f)
    private float mIntervalRatio = 0.65f;

    /**
     * 调节缩放比例
     */
    @FloatRange(from = 0.0f, to = 1.0f)
    private float mScaleRatio = 0f;

    /**
     * 起始ItemX坐标
     */
    private int mStartX = 0;

    /**
     * 起始Item Y坐标
     */
    private int mStartY = 0;

    /**
     * 保存所有的Item的上下左右的偏移量信息
     */
    private final SparseArray<Rect> mAllItemFrames = new SparseArray<>();

    /**
     * 记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
     */
    private final SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        new PagerSnapHelper().attachToRecyclerView(view);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //如果没有item，直接返回
        //跳过preLayout，preLayout主要用于支持动画
        if (getItemCount() <= 0 || state.isPreLayout()) {
            mOffsetAll = 0;
            return;
        }
        mAllItemFrames.clear();
        mHasAttachedItems.clear();

        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);
        //计算测量布局的宽高
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        mStartX = Math.round((getHorizontalSpace() - mDecoratedChildWidth) / 2f);
        mStartY = Math.round((getVerticalSpace() - mDecoratedChildHeight) / 2f);

        float offset = mStartX;

        /**只存{@link MAX_RECT_COUNT}个item具体位置*/
        for (int i = 0; i < getItemCount(); i++) {
            Rect frame = mAllItemFrames.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(Math.round(offset), mStartY, Math.round(offset + mDecoratedChildWidth), mStartY + mDecoratedChildHeight);
            mAllItemFrames.put(i, frame);
            mHasAttachedItems.put(i, false);
            offset = offset + getIntervalDistance(); //原始位置累加，否则越后面误差越大
        }

        detachAndScrapAttachedViews(recycler); //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        layoutItems(recycler, state, 0);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        int travel = dx;
        if (dx + mOffsetAll < 0) {
            travel = -mOffsetAll;
        } else if (dx + mOffsetAll > getMaxOffset()) {
            travel = (int) (getMaxOffset() - mOffsetAll);
        }
        mOffsetAll += travel; //累计偏移量
        layoutItems(recycler, state, dx);

        return travel;
    }

    /**
     * 布局Item
     *
     * <p>1，先清除已经超出屏幕的item
     * <p>2，再绘制可以显示在屏幕里面的item
     */
    private void layoutItems(RecyclerView.Recycler recycler,
                             RecyclerView.State state, int dx) {
        if (state == null || state.isPreLayout())
            return;

        Rect displayFrame = new Rect(mOffsetAll, 0, mOffsetAll + getHorizontalSpace(), getVerticalSpace());

        int position;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            position = getPosition(child);
            Rect rect = getFrame(position);
            if (!Rect.intersects(displayFrame, rect)) {//Item没有在显示区域，就说明需要回收
                removeAndRecycleView(child, recycler); //回收滑出屏幕的View
                mHasAttachedItems.delete(position);
            } else { //Item还在显示区域内，更新滑动后Item的位置
                layoutItem(child, rect, dx); //更新Item位置
                mHasAttachedItems.put(position, true);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = getFrame(i);
            if (Rect.intersects(displayFrame, rect) &&
                    !mHasAttachedItems.get(i)) { //重新加载可见范围内的Item
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                if (dx < 0) { //item 向右滚动，新增的Item需要添加在最前面
                    addView(scrap, 0);
                } else { //item 向左滚动，新增的item要添加在最后面
                    addView(scrap);
                }
                layoutItem(scrap, rect, dx); //将这个Item布局出来
                mHasAttachedItems.put(i, true);
            }
        }
    }

    /**
     * 布局Item位置
     *
     * @param child 要布局的Item
     * @param frame 位置信息
     */
    private void layoutItem(View child, Rect frame, int dx) {
        layoutDecorated(child,
                frame.left - mOffsetAll,
                frame.top,
                frame.right - mOffsetAll,
                frame.bottom);
        float scale = computeScale(frame.left - mOffsetAll);
        child.setScaleX(scale); //缩放
        child.setScaleY(scale); //缩放
    }

    /**
     * 动态获取Item的位置信息
     *
     * @param index item位置
     * @return item的Rect信息
     */
    private Rect getFrame(int index) {
        Rect frame = mAllItemFrames.get(index);
        if (frame == null) {
            frame = new Rect();
            float offset = mStartX + getIntervalDistance() * index; //原始位置累加（即累计间隔距离）
            frame.set(Math.round(offset), mStartY, Math.round(offset + mDecoratedChildWidth), mStartY + mDecoratedChildHeight);
        }

        return frame;
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        removeAllViews();
        mOffsetAll = 0;
        mHasAttachedItems.clear();
        mAllItemFrames.clear();
    }

    /**
     * 获取整个布局的水平空间大小
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 获取整个布局的垂直空间大小
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    /**
     * 获取最大偏移量
     */
    private float getMaxOffset() {
        return (getItemCount() - 1) * getIntervalDistance();
    }

    /**
     * 计算Item缩放系数
     *
     * @param x Item的偏移量
     * @return 缩放系数
     */
    private float computeScale(int x) {
        float scale;
        if (mScaleRatio == 0) {
            scale = 1 - Math.abs(x - mStartX) * 1.0f / Math.abs(mStartX + mDecoratedChildWidth / (1 - mIntervalRatio));
        } else {
            scale = 1 - Math.abs(x - mStartX) * 1.0f / Math.abs(mStartX + mDecoratedChildWidth / mScaleRatio);
        }
        if (scale < 0)
            scale = 0;
        if (scale > 1)
            scale = 1;
        return scale;
    }


    /**
     * 获取Item间隔
     */
    public int getIntervalDistance() {
        return Math.round(mDecoratedChildWidth * mIntervalRatio);
    }

    /**
     * 获取第一个可见的Item位置
     * <p>Note:该Item为绘制在可见区域的第一个Item，有可能被第二个Item遮挡
     */
    public int getFirstVisiblePosition() {
        Rect displayFrame = new Rect(mOffsetAll, 0, mOffsetAll + getHorizontalSpace(), getVerticalSpace());
        int cur = getCenterPosition();
        for (int i = cur - 1; ; i--) {
            Rect rect = getFrame(i);
            if (rect.left <= displayFrame.left) {
                return Math.abs(i) % getItemCount();
            }
        }
    }

    /**
     * 获取最后一个可见的Item位置
     * <p>Note:该Item为绘制在可见区域的最后一个Item，有可能被倒数第二个Item遮挡
     */
    public int getLastVisiblePosition() {
        Rect displayFrame = new Rect(mOffsetAll, 0, mOffsetAll + getHorizontalSpace(), getVerticalSpace());
        int cur = getCenterPosition();
        for (int i = cur + 1; ; i++) {
            Rect rect = getFrame(i);
            if (rect.right >= displayFrame.right) {
                return Math.abs(i) % getItemCount();
            }
        }
    }

    /**
     * @param index child 在 RecyclerCoverFlow 中的位置
     */
    public int getChildActualPos(int index) {
        View child = getChildAt(index);
        return getPosition(child);
    }

    /**
     * 获取可见范围内最大的显示Item个数
     */
    public int getMaxVisibleCount() {
        int oneSide = (getHorizontalSpace() - mStartX) / (getIntervalDistance());
        return oneSide * 2 + 1;
    }

    /**
     * 获取中间位置
     */
    int getCenterPosition() {
        int pos = mOffsetAll / getIntervalDistance();
        int more = mOffsetAll % getIntervalDistance();
        if (Math.abs(more) >= getIntervalDistance() * 0.5f) {
            if (more >= 0)
                pos++;
            else
                pos--;
        }
        return pos;
    }


}