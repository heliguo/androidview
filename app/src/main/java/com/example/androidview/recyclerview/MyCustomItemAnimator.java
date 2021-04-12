package com.example.androidview.recyclerview;

import android.view.animation.AnticipateOvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author lgh on 2021/4/12 17:47
 * @description 自定义recyclerview 动画
 */
public class MyCustomItemAnimator extends DefaultItemAnimator {

    //用于存储将要移动的MoveInfo对象
    private ArrayList<MyCustomItemAnimator.MoveInfo> mPendingMoves = new ArrayList();
    //MoveInfo的临时存储集合
    private ArrayList<ArrayList<MyCustomItemAnimator.MoveInfo>> mMovesList = new ArrayList();
    //用于存储正在执行移动动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList();
    //用于存储将要被添加的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList();
    //存储被添加的ViewHolder的临时集合
    private ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList();
    //用于存储正在执行添加动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList();
    //用于存储将要删除的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    //用于存储正在执行删除动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList();
    //用于存储将要改变的ViewHolder
    private ArrayList<MyCustomItemAnimator.ChangeInfo> mPendingChanges = new ArrayList();
    //存储被改变的ViewHolder的临时集合
    private ArrayList<ArrayList<MyCustomItemAnimator.ChangeInfo>> mChangesList = new ArrayList();
    //用于存储正在执行改变动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList();

    //定义一个插值器：先向相反方向改变，再加速播放，会超出目标值
    private AnticipateOvershootInterpolator accelerateDecelerateInterpolator;

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }


    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        //删除条目动画实现
        return super.animateRemove(holder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        //添加条目动画实现
        return super.animateAdd(holder);
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        //移动条目动画实现
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        //改变条目动画实现
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
    }


    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
    }

    private static class MoveInfo {
        public RecyclerView.ViewHolder holder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    private static class ChangeInfo {
        public RecyclerView.ViewHolder oldHolder;
        public RecyclerView.ViewHolder newHolder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }
    }

}
