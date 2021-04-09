package com.example.androidview.dragandslideslip;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.calendar.ViewUtils;
import com.example.androidview.dragandslideslip.rv.ItemHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lgh on 2021/4/7 11:07
 * @description
 */
public class ItemHelperActivity extends BaseActivity {

    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_helper);
        RecyclerView recyclerView = findViewById(R.id.item_helper_rv);
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("");
        }
        ItemHelperAdapter adapter = new ItemHelperAdapter(mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        CommonListItemTouchCallBack touchCallBack = new CommonListItemTouchCallBack();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        touchCallBack.setOnItemTouchListener(new CommonListItemTouchCallBack.OnItemTouchListener() {
            @Override
            public void onMove(int fromPosition, int toPosition) {
                Collections.swap(mList, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onSwiped(int position) {
                mList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void release() {

            }
        });
        touchCallBack.setOnItemSwipingListener((c, viewHolder, dX, dY, isCurrentlyActive) -> {
            Log.e("TAGTAGTAGTAG", "onCreate: "+dX);
            View itemView = viewHolder.itemView;
            c.save();
            if (dX > 0) {
                c.clipRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom() - ViewUtils.dpToPx(itemView.getContext(), 4));
                c.translate(itemView.getLeft()+ dX, itemView.getTop());
            } else {
                c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom() - ViewUtils.dpToPx(itemView.getContext(), 4));
                c.translate(itemView.getRight() + dX, itemView.getTop());
            }
            c.drawColor(Color.RED);
            c.restore();
        });

        touchCallBack.setOnItemDragListener(new CommonListItemTouchCallBack.OnItemDragListener() {
            @Override
            public void onDragStart(RecyclerView.ViewHolder viewHolder) {
                // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
                int startColor = Color.WHITE;
                int endColor = Color.RED;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(animation -> viewHolder.itemView.findViewById(R.id.item_helper_cardv).setBackgroundColor((int) animation.getAnimatedValue()));
                    v.setDuration(300);
                    v.start();
                }
            }

            @Override
            public void onDragEnd(RecyclerView.ViewHolder viewHolder) {
                int startColor = Color.RED;
                int endColor = Color.WHITE;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(animation -> viewHolder.itemView.findViewById(R.id.item_helper_cardv).setBackgroundColor((int) animation.getAnimatedValue()));
                    v.setDuration(300);
                    v.start();
                }
            }
        });
    }
}
