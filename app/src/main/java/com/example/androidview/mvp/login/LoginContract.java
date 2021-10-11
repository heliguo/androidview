package com.example.androidview.mvp.login;

import com.example.androidview.mvp.BaseEntity;

/**
 * @author lgh on 2021/10/11 17:08
 * @description 契约类
 * 将model层、view层、presenter层协商的共同业务，封装成接口
 */
public interface LoginContract {

    interface Model {
        void executeLogin(String userName, String password) throws Exception;
    }

    interface View<T extends BaseEntity> {
        void handleResult(T t);
    }

    interface Presenter<T extends BaseEntity> {

        //接收view层指令，可以自己处理或者model层处理
        void requestLogin(String userName, String password);

        //结果响应，接收model层结果，通知view层处理
        void responseResult(T t);
    }
}
