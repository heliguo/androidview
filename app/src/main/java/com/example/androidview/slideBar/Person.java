package com.example.androidview.slideBar;

/**
 * @author lgh on 2021/1/30 20:01
 * @description
 */
public class Person {

    private String name;

    private String pinyin;

    public Person(String name){
        this.name = name;
        this.pinyin = Cn2Spell.getPinYin(name);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}