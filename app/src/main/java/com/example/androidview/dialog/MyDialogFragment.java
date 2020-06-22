package com.example.androidview.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * @author lgh on 2020/6/22 9:27
 * @description dialog fragment
 * {@link MyDialogFragment#onCreateDialog(Bundle)
 */
public class MyDialogFragment extends DialogFragment {

    private MyDialogFragment() {
    }

    public static MyDialogFragment getInstance(String title) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage("msg")
                .setPositiveButton("确认", (dialog, which) -> {

                })
                .setNegativeButton("取消", (dialg, which) -> {

                });
        return builder.create();
    }
}
