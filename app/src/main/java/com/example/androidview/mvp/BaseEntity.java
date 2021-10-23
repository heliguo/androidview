package com.example.androidview.mvp;

/**
 * @author lgh on 2021/10/11 17:15
 * @description
 */
public class BaseEntity {

    private int code;
    private boolean success;
    private String error;

    public BaseEntity(int code, boolean success, String error) {
        this.code = code;
        this.success = success;
        this.error = error;
    }
}
