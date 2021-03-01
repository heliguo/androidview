package com.example.androidview.expandrecyclerview;

import java.util.Collection;

public class Preconditions {

    public static boolean isNullOrEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isNullOrEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static <T> T checkNoNull(T obj, String msg) {
        if (obj == null)
            throw new IllegalArgumentException(msg);
        return obj;
    }

}
