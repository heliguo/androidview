package com.example.androidview.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.androidview.BaseActivity;
import com.example.androidview.databinding.ActivityCameraVideoBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lgh on 2021/10/21 16:14
 * @description
 */
public class CameraVideoActivity extends BaseActivity {

    ActivityCameraVideoBinding mBinding;
    private ActivityResultLauncher<Integer> mLauncher;
    private File mFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCameraVideoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initPhotoError();
        mBinding.cameraTake.setOnClickListener(v -> takeCamera());

        mLauncher = registerForActivityResult(new Contract(), result -> {
            if (!TextUtils.isEmpty(result)) {
                mBinding.cameraIv.setVideoPath(result);
                mBinding.cameraIv.start();
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int camera = checkSelfPermission(Manifest.permission.CAMERA);
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED ||
                    camera != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 300);
            }
        }

    }

    private void takeCamera() {
        mFile = createFilePath();
        mLauncher.launch(2222);
        //        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, mFile);
        //        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        //        startActivityForResult(takePhotoIntent, 1111);
    }

    private File createFilePath() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File imageFile = null;
        try {
            imageFile = File.createTempFile(generateFileName(), ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "JPEG_" + timeStamp;
    }

    class Contract extends ActivityResultContract<Integer, String> {

        @NonNull
        @NotNull
        @Override
        public Intent createIntent(@NonNull @NotNull Context context, Integer input) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            if (resultCode==RESULT_OK&&intent!=null&&intent.getData()!=null){
                Uri videoUri = intent.getData();
                mBinding.cameraIv.setVideoURI(videoUri);
                mBinding.cameraIv.start();
            }
            if (resultCode == RESULT_OK && intent != null && intent.getData() != null) {
                Uri data = intent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor query = getContentResolver().query(data, filePathColumn, null, null, null);
                query.moveToFirst();
                int columnIndex = query.getColumnIndex(filePathColumn[0]);
                String path = query.getString(columnIndex);
                query.close();
                return path;
            } else {
                return mFile.getAbsolutePath();
            }
        }
    }

    private void initPhotoError() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            try {
                String result = mFile.getAbsolutePath();
                if (!TextUtils.isEmpty(result)) {
                    //                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //                    options.inSampleSize = 4;
//                    Glide.with(this).load(result).into(mBinding.cameraIv);
                    //                    Bitmap bitmap = BitmapFactory.decodeFile(createFilePath().getAbsolutePath(), options);
                    //                    mBinding.cameraIv.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
