package com.example.androidview.mvp.login;

import com.example.androidview.mvp.BaseEntity;

/**
 * @author lgh on 2021/10/11 19:27
 * @description
 */
public class LoginBean extends BaseEntity {

    public LoginBean() {
        this(200,true,"");
    }


    public LoginBean(int code, boolean success, String error) {
        super(code, success, error);
    }

}
