package com.example.androidview.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author lgh on 2021/2/23 15:23
 * @description
 */
public class JsonUtils {

    public static String getJson(Context context, String fileName) {

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            return convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * input 流转换为字符串
     */
    private static String convertStreamToString(InputStream is) {
        String s = null;
        try {
            //格式转换
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                s = scanner.next();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
