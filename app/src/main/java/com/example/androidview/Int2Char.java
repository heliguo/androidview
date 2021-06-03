package com.example.androidview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lgh on 2020/12/29 14:49
 * @description int 2 Character[]    int转Character数组
 * Character[] 2 list   Character数组转list
 * list reverse         list翻转
 * list 2 Character[]   list转Character数组
 */
public class Int2Char {


    public static void main(String[] args) {

        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println("======================");
        int c = 10;
        int stringSize = stringSize(c);
        System.out.println("stringSize: " + stringSize);
        System.out.println("======================");
        Character[] chars = toChar(new Character[stringSize], c, 0);
        List<Character> list = new ArrayList<>(chars.length);
        Collections.addAll(list, chars);
        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("======================");
        Character[] characters = list.toArray(new Character[0]);
        for (Character character : characters) {
            System.out.println(character);
        }

    }

    private static Character[] toChar(Character[] v, int src, int i) {
        if (src == 0) {
            return v;
        }
        int a = src / 10;
        int b = src % 10;
        if (a >= 0) {
            v[i] = (char) (b + '0');
            i++;
            toChar(v, a, i);
        }
        return v;
    }

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    // Requires positive x
    static int stringSize(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

}
