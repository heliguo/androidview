package com.example.androidview.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.R;

/**
 * @author lgh on 2021/4/12 17:08
 * @description
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final int[] imgs;

    public CustomAdapter() {
        imgs = new int[]{R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs, R.drawable.dlzs,
                R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs, R.drawable.dlzs,
                R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs,
                R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh,
                R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs, R.drawable.dlzs,
                R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh};

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.mImageView.setBackgroundResource(imgs[position]);
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.custom_iv);
        }
    }

}
