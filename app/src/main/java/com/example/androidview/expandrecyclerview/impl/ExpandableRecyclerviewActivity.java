package com.example.androidview.expandrecyclerview.impl;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.R;
import com.example.androidview.expandrecyclerview.ExpandableAdapter;
import com.example.androidview.expandrecyclerview.ParentViewHolder;
import com.example.androidview.utils.JsonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/2/23 15:30
 * @description
 */
public class ExpandableRecyclerviewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private List<TitleBean> mData = new ArrayList<>();

    private SparseBooleanArray mSparseBooleanArray = new SparseBooleanArray();//解决数据混乱

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_recyclerview);
        mRecyclerView = findViewById(R.id.rv);
        mAdapter = new MyAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration());
        //        mAdapter.addParentExpandableStateChangeListener(new ParentExpandableStateChangeListener());
        mAdapter.addParentExpandCollapseListener(new ParentExpandCollapseListener());

        mAdapter.childClickTargets(R.id.tv_expand_child_confirm_take_medicine).listenChildClick((parent, view) -> {
            View containingItemView = parent.findContainingItemView(view);
            if (containingItemView != null) {
                int childAdapterPosition = parent.getChildAdapterPosition(containingItemView);
                Toast.makeText(ExpandableRecyclerviewActivity.this, "parent position: " +
                        mAdapter.getParentPosition(childAdapterPosition) + "child position: " +
                        childAdapterPosition, Toast.LENGTH_SHORT).show();
            }


        });
        mAdapter.parentClickTargets().listenParentClick((parent, view) -> {
            View containingItemView = parent.findContainingItemView(view);
            if (containingItemView != null) {
                int childAdapterPosition = parent.getChildAdapterPosition(containingItemView);
                TitleBean titleBean = mAdapter.getParentForAdapterPosition(childAdapterPosition);
                int size = titleBean.getMyChildren().size();
                mRecyclerView.post(() -> {
                    RecyclerView.ViewHolder holderP = mRecyclerView.findViewHolderForAdapterPosition(childAdapterPosition);
                    RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(childAdapterPosition + size);
                    if (holderP instanceof MyParentViewHolder) {
                        MyParentViewHolder holderP1 = (MyParentViewHolder) holderP;
                        mSparseBooleanArray.put(childAdapterPosition, holderP1.isExpanded());
//                        if (holderP1.isExpanded()) {
//                            holderP.itemView.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.shape_expandable_bg_up));
//                        } else {
//                            holderP.itemView.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.shape_expandable_bg));
//                        }
//                        if (holder instanceof MyChildViewHolder && holderP1.isExpanded()) {
//                            Boolean aBoolean = mSparseBooleanArray.get(childAdapterPosition);
//                            if (aBoolean == null) {
//                                return;
//                            }
//                            if (aBoolean) {
//                                holder.itemView.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.shape_expandable_bg_down));
//                                holder.itemView.findViewById(R.id.view_line).setVisibility(View.INVISIBLE);
//                            } else {
//                                holder.itemView.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.shape_expandable_bg_child));
//                                holder.itemView.findViewById(R.id.view_line).setVisibility(View.VISIBLE);
//                            }

//                        }
                    }
                });
                //                Toast.makeText(ExpandableRecyclerviewActivity.this, "parent position: " + childAdapterPosition, Toast.LENGTH_SHORT).show();
            }
        });

        initData();
    }

    private void initData() {
        Gson gson = new Gson();
        String json = JsonUtils.getJson(this, "medicine.json");
        Bean bean = gson.fromJson(json, Bean.class);
        Bean.DataBean data = bean.getData();
        List<Bean.DataBean.ReminderListBean> reminderList = data.getReminderList();
        mData.clear();
        for (int i = 0; i < reminderList.size(); i++) {
            Bean.DataBean.ReminderListBean reminderListBean = reminderList.get(i);
            TitleBean titleBean = new TitleBean();
            titleBean.setTime(reminderListBean.getTimeClass());
            List<Bean.DataBean.ReminderListBean.ListBean> list = reminderListBean.getList();
            List<ContentBean> contentBeanList = new ArrayList<>();

            for (int j = 0; j < list.size(); j++) {
                Bean.DataBean.ReminderListBean.ListBean listBean = list.get(j);
                ContentBean contentBean = new ContentBean();
                contentBean.setTitle(listBean.getMedicineName());
                contentBean.setContent(listBean.getDosageAdded() + listBean.getUnit());
                contentBean.setHas(listBean.isUsedDrug());
                contentBean.setType(R.layout.item_expandable_child);
                contentBeanList.add(contentBean);
            }
            titleBean.setMyChildren(contentBeanList);
            titleBean.setType(R.layout.item_expandable_father);
            mSparseBooleanArray.put(i, titleBean.isInitiallyExpanded());
            mData.add(titleBean);
        }
        mAdapter.invalidate(mData);
    }

    private class ParentExpandableStateChangeListener
            implements ExpandableAdapter.OnParentExpandableStateChangeListener {
        @Override
        public void onParentExpandableStateChanged(RecyclerView rv, ParentViewHolder pvh,
                                                   int position, boolean expandable) {
            if (pvh == null)
                return;
            final ImageView arrow = pvh.getView(R.id.iv_more_arrow);
            if (expandable && arrow.getVisibility() != View.VISIBLE) {
                arrow.setVisibility(View.VISIBLE);
                arrow.setRotation(pvh.isExpanded() ? 180 : 0);
            } else if (!expandable && arrow.getVisibility() == View.VISIBLE) {
                arrow.setVisibility(View.GONE);
            }
        }
    }

    private class ParentExpandCollapseListener
            implements ExpandableAdapter.OnParentExpandCollapseListener {
        @Override
        public void onParentExpanded(RecyclerView rv, ParentViewHolder pvh, int position,
                                     boolean pendingCause, boolean byUser) {
            if (pvh == null)
                return;
            ImageView arrow = pvh.getView(R.id.iv_more_arrow);
            if (arrow.getVisibility() != View.VISIBLE)
                return;
            float currRotate = arrow.getRotation();
            //重置为从0开始旋转
            if (currRotate == 360) {
                arrow.setRotation(0);
            }
            if (pendingCause) {
                arrow.setRotation(180);
            } else {
                arrow.animate()
                        .rotation(180)
                        .setDuration(300)
                        .start();
            }

            TitleBean pvhParent = (TitleBean) pvh.getParent();
            int size = pvhParent.getChildren().size();
            Log.e("===**=", "onParentExpanded: " + (position + size));


        }

        @Override
        public void onParentCollapsed(RecyclerView rv, ParentViewHolder pvh, int position,
                                      boolean pendingCause, boolean byUser) {

            if (pvh == null)
                return;
            ImageView arrow = pvh.getView(R.id.iv_more_arrow);
            if (arrow.getVisibility() != View.VISIBLE)
                return;
            float currRotate = arrow.getRotation();
            float rotate = 360;
            //未展开完全并且当前旋转角度小于180，逆转回去
            if (currRotate < 180) {
                rotate = 0;
            }
            if (pendingCause) {
                arrow.setRotation(rotate);
            } else {
                arrow.animate()
                        .rotation(rotate)
                        .setDuration(300)
                        .start();
            }
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {

        int itemOffset = 18;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            final int childAdapterPos = parent.getChildAdapterPosition(view);
            if (childAdapterPos != RecyclerView.NO_POSITION) {
                ContentBean contentBean = mAdapter.getChildForAdapterPosition(childAdapterPos);
                if (contentBean == null) {
                    outRect.set(0, itemOffset, 0, 0);
                }
            }
        }
    }

}
