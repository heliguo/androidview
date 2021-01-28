package com.example.androidview.realm;

import io.realm.RealmObject;

/**
 * @author lgh on 2021/1/18 16:21
 * @description realm dao
 */
public class Dog extends RealmObject {
    
    private String name;
    
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
