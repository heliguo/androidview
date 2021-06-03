package com.example.androidview;

/**
 * @author lgh on 2021/2/18 14:49
 * @description
 */
public class instance {

    final static instance INSTANCE = new instance();

    static {
        System.out.println(String.format("1 static initialization block invoked, instance = %s", INSTANCE));
    }

    {
        System.out.println(String.format("2 initialization block invoked, instance = %s", INSTANCE));
    }

    instance() {
        System.out.println(String.format("3 constructor invoked, instance = %s", INSTANCE));
    }
    public static void main(String[] a) {
        new instance();
    }
}
