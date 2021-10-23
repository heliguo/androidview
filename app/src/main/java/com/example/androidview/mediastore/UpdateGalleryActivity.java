package com.example.androidview.mediastore;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.example.androidview.BaseActivity;

import java.io.FileNotFoundException;

/**
 * @author lgh on 2020/9/22:14:47
 * @description 更新图库
 */
public class UpdateGalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //插入图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    "imagePath", "imageName", "imageDescription");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //更新图库
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("imagePath")));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("imagePath", options);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.ImageColumns.DATA, "imagePath");
        values.put(MediaStore.Images.ImageColumns.TITLE, "imageName");
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "imageName");
        long currentTime = System.currentTimeMillis();
        long modified = currentTime / 1000;
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, currentTime);
        values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, modified);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, options.outMimeType);
        values.put(MediaStore.Images.ImageColumns.WIDTH, options.outWidth);
        values.put(MediaStore.Images.ImageColumns.HEIGHT, options.outHeight);
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }
}
