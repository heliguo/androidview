package com.example.androidview.bdtts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/6/17 14:13
 * @description 百度 60 阿里 300
 */
public class TextLengthUtils {

    public static List<String> getBdTexts(String text) {
        return getTexts(text, 60);
    }

    public static List<String> getTexts(String text, int split) {

        int length = text.length();
        float offset = length * 1.0f / split;
        int count = length / split;
        boolean b = false;
        if (offset - count > 0.0f) {
            count++;
            b = true;
        }
        List<String> list = new ArrayList<>(count);
        int a = 0;
        for (int i = 0; i < count; i++) {
            String c;
            if (i == count - 1 && b) {
                c = text.substring(a, length - 1);
            } else {
                c = text.substring(a, a + split);
                a += split;

            }
            list.add(c);
        }

        return list;
    }

}
