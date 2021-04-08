package com.example.androidview.dragandslideslip;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidview.R;

/**
 * author:lgh on 2020-03-05 10:50
 * 借助ItemTouchHelper.Callback 监听recyclerview item 拖动动作
 * ItemTouchHelper itemTouchHelper = new ItemTouchHelper(commonListItemTouchCallBack);
 * itemTouchHelper.attachToRecyclerView(recyclerView);
 */
public class CommonListItemTouchCallBack extends ItemTouchHelper.Callback {

    private OnItemTouchListener onItemTouchListener;

    private OnItemSwipingListener mOnItemSwipingListener;

    private int state;

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    public void setOnItemSwipingListener(OnItemSwipingListener onItemSwipingListener) {
        mOnItemSwipingListener = onItemSwipingListener;
    }

    /**
     * 根据 RecyclerView 不同的布局管理器，设置不同的滑动、拖动方向
     * 该方法使用 makeMovementFlags(int dragFlags, int swipeFlags) 方法返回
     * 参数: dragFlags:拖动的方向
     * swipeFlags:滑动的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;//拖动
        int swipeFlags;//滑动
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager ||
                recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //此处不需要进行滑动操作，可设置为除4和8之外的整数，这里设为0
            //不支持滑动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else {
            //如果是LinearLayoutManager则只能向上向下滑动，
            //此处第二个参数设置支持向右滑动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 需支持上下移动
     * 当 ItemTouchHelper 拖动一个Item时该方法将会被回调，Item将从旧的位置移动到新的位置
     * 如果不拖动这个方法将不会调用,返回true表示已经被移动到新的位置
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (onItemTouchListener != null) {
            onItemTouchListener.onMove(fromPosition, toPosition);
        }
        return true;
    }

    /**
     * 当Item被滑动的时候被调用
     * 如果你不滑动这个方法将不会被调用
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //此处是侧滑删除的主要代码
        int position = viewHolder.getAdapterPosition();
        if (onItemTouchListener != null) {
            onItemTouchListener.onSwiped(position);
        }
    }

    /**
     * @return true 支持长按拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * @return true 左右滑动可以执行删除操作
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 当Item被滑动、拖动的时候被调用
     * 从静止状态变为拖拽或者滑动的时候会回调该方法，参数actionState表示当前的状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
            state = actionState;
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
            int startColor = Color.WHITE;
            int endColor = Color.RED;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        viewHolder.itemView.findViewById(R.id.item_helper_cardv).setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });
                v.setDuration(300);
                v.start();
            }
        }
    }

    /**
     * 当与用户交互结束或相关动画完成之后被调用
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (onItemTouchListener != null) {
            onItemTouchListener.release();
        }
        if (state == ItemTouchHelper.ACTION_STATE_DRAG) {
            int startColor = Color.RED;
            int endColor = Color.WHITE;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        viewHolder.itemView.findViewById(R.id.item_helper_cardv).setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });
                v.setDuration(300);
                v.start();
            }
        }

    }

    /**
     * 自定义交互规则或动画效果
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (mOnItemSwipingListener != null) {
                mOnItemSwipingListener.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            }
        }
    }


    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }

    private float getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }

    /**
     * 移动交换数据的更新监听
     */
    public interface OnItemTouchListener {
        //拖动Item时调用
        void onMove(int fromPosition, int toPosition);

        //滑动Item时调用
        void onSwiped(int position);

        void release();
    }

    public interface OnItemSwipingListener {

        void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive);

    }
}
