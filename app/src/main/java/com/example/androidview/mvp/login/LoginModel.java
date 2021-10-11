package com.example.androidview.mvp.login;

import com.example.androidview.mvp.base.BaseModel;

/**
 * @author lgh on 2021/10/11 17:38
 * @description
 */
public class LoginModel extends BaseModel<LoginPresenter, LoginContract.Model> {

    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return (userName, password) -> {//发起网络请求
            if ("11".equals(userName) && "22".equals(password)) {
                p.getContract().responseResult(new LoginBean());
            } else {
                p.getContract().responseResult(null);
            }
        };
    }
}
