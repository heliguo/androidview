package com.example.androidview.mvp.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidview.R;
import com.example.androidview.mvp.base.BaseView;

/**
 * @author lgh on 2021/10/11 17:40
 * @description
 */
public class LoginActivity extends BaseView<LoginPresenter, LoginContract.View<LoginBean>> {

    private EditText mNameEt;
    private EditText mPwdEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initView();
    }


    private void initView() {
        mNameEt = findViewById(R.id.et_user_name);
        mPwdEt = findViewById(R.id.et_user_password);
        Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String name = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        mP.getContract().requestLogin(name, pwd);
    }

    @Override
    public LoginContract.View<LoginBean> getContract() {
        return loginBean -> {//结果
            if (loginBean != null) {
                Toast.makeText(getApplicationContext(), "登录成功: " + loginBean, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
            }

        };
    }


    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
}
