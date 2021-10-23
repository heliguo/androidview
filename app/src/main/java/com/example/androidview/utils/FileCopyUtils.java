package com.example.androidview.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lgh on 2021/10/22 15:52
 * @description assert to sd
 */
public class FileCopyUtils {

    public static boolean copyDBToSD(Context context, String path, String filename) {
        //获取资产管理者
        AssetManager am = context.getAssets();
        FileOutputStream fos = null;
        try {
            //打开资产目录下的文件
            InputStream inputStream = am.open(filename);
            File file = new File(path, filename);
            fos = new FileOutputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
