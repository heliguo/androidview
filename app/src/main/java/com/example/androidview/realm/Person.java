package com.example.androidview.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author lgh on 2021/1/18 16:22
 * @description
 */
public class Person extends RealmObject {

    @PrimaryKey//主键
    private long id;

    //只能用于Boolean, Byte, Short, Integer, Long, Float, Double, String, byte[] 和 Date
    @Required//非空字段，基本数据类型不需要使用注解 @Required
    private String name;

    private RealmList<Dog> mDogs;

    @Ignore//忽略字段，不保存到数据库
    private int ignore;

    @Index//添加索引，插入速度变慢，数据量加大，支持索引：String，byte，short，int，long，boolean和Date字段。
    private int index;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Dog> getDogs() {
        return mDogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        mDogs = dogs;
    }
}
