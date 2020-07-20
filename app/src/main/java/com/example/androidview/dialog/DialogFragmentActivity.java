package com.example.androidview.dialog;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.databinding.ActivityDialogFragmentBinding;

/**
 * @author lgh
 * @description dialog fragment activity
 */
public class DialogFragmentActivity extends AppCompatActivity {

    ActivityDialogFragmentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDialogFragmentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.showFragmentDialog.setOnClickListener(v -> {
            MyDialogFragment dialogFragment = MyDialogFragment.getInstance("title");
            dialogFragment.show(getSupportFragmentManager(), "my_dialog_fragment");
        });

        mBinding.customDialog.setOnClickListener(v -> {
            CustomDialogFragment fragment2 = CustomDialogFragment.getInstance("title")
                    .setCancelListener("取消", () -> {
                        Toast.makeText(DialogFragmentActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    })
                    .setConfirmListener("确定", () -> {
                        Toast.makeText(DialogFragmentActivity.this, "confirm", Toast.LENGTH_SHORT).show();

                    });
            fragment2.setCancelable(false);
            fragment2.show(getSupportFragmentManager(), "custom_dialog_fragment");
        });

    }
}