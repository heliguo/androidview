package com.example.androidview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidview.databinding.FragmentEditNameBinding;

/**
 * @author lgh on 2020/6/22 9:59
 * @description {@link CustomDialogFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
 */
public class CustomDialogFragment extends DialogFragment {

    private FragmentEditNameBinding mBinding;
    private CancelListener          mCancelListener;
    private ConfirmListener         mConfirmListener;
    private String                  cancelTxt;
    private String                  confirmTxt;

    private boolean isFullWindows = false;

    private CustomDialogFragment() {
    }

    public CustomDialogFragment setCancelListener(String cancelTxt, CancelListener cancelListener) {
        mCancelListener = cancelListener;
        this.cancelTxt = cancelTxt;
        return this;
    }

    public CustomDialogFragment setConfirmListener(String confirmTxt, ConfirmListener confirmListener) {
        mConfirmListener = confirmListener;
        this.confirmTxt = confirmTxt;
        return this;
    }

    public static CustomDialogFragment getInstance(String title) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEditNameBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        mBinding.dialogTitle.setText(title);

        if (confirmTxt == null) {
            mBinding.dialogConfirm.setVisibility(View.INVISIBLE);
        } else {
            mBinding.dialogConfirm.setText(confirmTxt);
        }

        if (cancelTxt == null) {
            mBinding.dialogCancel.setVisibility(View.INVISIBLE);
        } else {
            mBinding.dialogCancel.setText(cancelTxt);
        }

        mBinding.dialogCancel.setOnClickListener(v -> {
            if (mCancelListener != null) {
                mCancelListener.onCancel();
            }
            this.dismiss();
        });

        mBinding.dialogConfirm.setOnClickListener(v -> {
            if (mConfirmListener != null) {
                mConfirmListener.onConfirm();
            }
            this.dismiss();
        });

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {

        if (isFullWindows) {
            showFullWindows();
        }

        super.onResume();
    }

    private void showFullWindows() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    public interface CancelListener {

        void onCancel();

    }

    public interface ConfirmListener {

        void onConfirm();
    }

    public void setFullWindows(boolean fullWindows) {
        this.isFullWindows = fullWindows;
    }
}
