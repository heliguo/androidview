package com.example.androidview;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author lgh on 2020/9/17:14:28
 * @description
 */
public class Main {

    static float a = 21.93f;//93
    static float b = 30.12f;//69
    static float c = 29.14f;//71
    static float d = 2051.93f;

    public static void main(String[] args) {

        for (int i = 0; i < 93; i++) {
            for (int j = 0; j < 69; j++) {
                for (int k = 0; k < 71; k++) {
                    if (a * i + b * j + c * k == d) {
                        System.out.println("a=" + i + "  b=" + j + "  c=" + k);
                    }
                }
            }
        }

//        String url = "a/b/b/bcc/dddd";
//        int index = url.lastIndexOf("/");
//        url = url.substring(0,url.length()-1);
//        System.out.println(url);
//        System.out.println(getCoinValue("92292"));
    }

    public static String getCoinValue(String v) {
        double d = Double.parseDouble(v) / 10000f;
        System.out.println(d);
        BigDecimal b = new BigDecimal(d);
        double result = b.setScale(2, 0).doubleValue();
        System.out.println("0  "+result);
        double result1 = b.setScale(2, 1).doubleValue();
        System.out.println("1  "+result1);
        double result2 = b.setScale(2, 2).doubleValue();
        System.out.println("2  "+result2);
        double result3 = b.setScale(2, 3).doubleValue();
        System.out.println("3  "+result3);
        double result4 = b.setScale(2, 4).doubleValue();
        System.out.println("4  "+result4);
        double result5 = b.setScale(2, 5).doubleValue();
        System.out.println("5  "+result5);
        double result6 = b.setScale(2, 6).doubleValue();
        System.out.println("6  "+result6);
        double result7 = b.setScale(2, 7).doubleValue();
        System.out.println("7  "+result7);;
        return result + "元";
    }

}
