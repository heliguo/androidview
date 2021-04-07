package com.example.androidview.dragandslideslip.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.R;

import java.util.List;

/**
 * @author lgh on 2021/4/7 11:09
 * @description
 */
public class ItemHelperAdapter extends RecyclerView.Adapter<ItemHelperAdapter.ItemHelperViewHolder> {

    private final List<String> dataList;

    public ItemHelperAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ItemHelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_helper, parent, false);
        return new ItemHelperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHelperViewHolder holder, int position) {
        holder.mTextView.setText("item " + position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class ItemHelperViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private TextView mDeleteTv;

        public ItemHelperViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item);
            mDeleteTv = itemView.findViewById(R.id.tv_text);
        }
    }

}
