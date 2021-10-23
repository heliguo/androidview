package com.example.androidview.mvp.login;

import com.example.androidview.mvp.base.BasePresenter;

/**
 * @author lgh on 2021/10/11 17:39
 * @description
 */
public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter<LoginBean>> {


    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }

    @Override
    public LoginContract.Presenter<LoginBean> getContract() {
        return new LoginContract.Presenter<LoginBean>() {
            @Override
            public void requestLogin(String userName, String password) {//从view层来的任务，分配给model进行网络请求
                try {
                    getModel().getContract().executeLogin(userName, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(LoginBean loginBean) {//结果回传
                LoginActivity view = getView();
                if (view != null) {
                    view.getContract().handleResult(loginBean);
                }
            }
        };
    }
}
