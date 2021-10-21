package com.example.androidview;

import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        for (int i = 0; i < 6; i++) {
            System.out.println("i   666666");
            for (int i1 = 0; i1 < 6; i1++) {
                if (i1 == 2) {
                    break;
                }
                System.out.println(i1);
            }
        }
        int a = 5;
        if (a < 6) {
            System.out.println("***********");
        } else if (a < 10) {
            System.out.println("---------");
        }

        System.out.println("dkdkdk " + (8 << 1));//左移 *2
        System.out.println("dkdkdk " + (8 >> 1));//右移 /2
        System.out.println("dkdkdk " + (0 % 8));//0的余数为0

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit());
        fruits.add(new Fruit());
        fruits.add(new Fruit());
        examples(fruits);

        String s = "1112223333444";
        System.out.println(s.substring(0,4));
        System.out.println(s.substring(4));
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
        System.out.println("0  " + result);
        double result1 = b.setScale(2, 1).doubleValue();
        System.out.println("1  " + result1);
        double result2 = b.setScale(2, 2).doubleValue();
        System.out.println("2  " + result2);
        double result3 = b.setScale(2, 3).doubleValue();
        System.out.println("3  " + result3);
        double result4 = b.setScale(2, 4).doubleValue();
        System.out.println("4  " + result4);
        double result5 = b.setScale(2, 5).doubleValue();
        System.out.println("5  " + result5);
        double result6 = b.setScale(2, 6).doubleValue();
        System.out.println("6  " + result6);
        double result7 = b.setScale(2, 7).doubleValue();
        System.out.println("7  " + result7);
        return result + "元";
    }


    /**
     * https://www.cnblogs.com/dengchengchao/p/9717097.html
     */
    public static void example() {
        List<Apple> fruits = new ArrayList<>();
        fruits.add(new Apple());
        fruits.add(new Apple());
        fruits.add(new Apple());
        examples(fruits);


        List<? super Fruit> list1 = new ArrayList<>();
        list1.add(new Fruit());
        list1.add(new Apple());
        //        Fruit object = list1.get(0);//编译错误
        Object object = list1.get(0);//编译错误
    }

    public static void examples(List<? extends Fruit> list) {
        //                list.add(new Fruit());//编译错误
        Fruit fruit = list.get(0);
        System.out.println(list.size());

        List<? super Fruit> exampless = exampless();
        for (Object o : exampless) {
            System.out.println("dddddd   " + new TypeToken<Fruit>(){}.getType().getTypeName());
        }
        //        ThreadLocalRandom.current().nextInt(63);
    }

    public static List<? super Fruit> exampless() {
        List<? super Fruit> list = new ArrayList<>();
        list.add(new Apple());//编译错误
        list.add(new Fruit());//编译错误
        list.add(new Apple());//编译错误
        list.add(new Apple());//编译错误
        return list;
        //        ThreadLocalRandom.current().nextInt(63);
    }


    public static class Fruit {


    }

    public static class Apple extends Fruit {

    }

}
