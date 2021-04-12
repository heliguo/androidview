package com.example.androidview.recyclerview.itemtouchhelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.R;
import com.example.androidview.recyclerview.swipemenu.SwipeMenuLayout;

import java.util.List;

/**
 * @author lgh on 2021/4/7 11:09
 * @description
 */
public class ItemHelperAdapter extends RecyclerView.Adapter<ItemHelperAdapter.ItemHelperViewHolder> {

    private final List<String> dataList;

    private Toast mToast;

    public ItemHelperAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ItemHelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_helper2, parent, false);
        return new ItemHelperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHelperViewHolder holder, int p) {
        Log.e("TAGTAGTAGTAGTAG", "onBindViewHolder: "+p );
        final int position = p % dataList.size();
        holder.mTextView.setText("item " + position);
        holder.leftDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v.getContext(), "you click left delete ");
                holder.mSwipeMenuLayout.smoothOpenLeftMenu();
            }
        });

        holder.leftCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSwipeMenuLayout.smoothCloseMenu();
            }
        });

        holder.rightDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v.getContext(), "you click right ");
                holder.mSwipeMenuLayout.smoothOpenRightMenu();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v.getContext(),"you click item in position "+position);
            }
        });

    }

    private void show(Context context, String toast) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public int getItemCount() {
        //        return dataList == null ? 0 : dataList.size();
//        return Integer.MAX_VALUE;
        Log.e("TAGTAGTAGTAGTAG", "getItemCount: ");
        return 50;
    }

    public static class ItemHelperViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private TextView leftDeleteTv;
        private TextView leftCancelTv;
        private TextView rightDeleteTv;
        private SwipeMenuLayout mSwipeMenuLayout;

        public ItemHelperViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item);
            rightDeleteTv = itemView.findViewById(R.id.tv_text);
            leftDeleteTv = itemView.findViewById(R.id.tv_left_bottom);
            leftCancelTv = itemView.findViewById(R.id.tv_left_top);
            mSwipeMenuLayout = itemView.findViewById(R.id.swipe_menu_layout);
            mSwipeMenuLayout.setContentView(R.id.swipe_content);
            mSwipeMenuLayout.setLeftMenuView(R.id.swipe_left);
            mSwipeMenuLayout.setRightMenuView(R.id.swipe_right);
        }
    }

}
