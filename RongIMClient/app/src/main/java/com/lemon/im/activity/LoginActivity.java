package com.lemon.im.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lemon.im.R;
import com.lemon.im.base.BaseActivity;
import com.lemon.im.bean.LoginRequestBean;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.utils.GsonUtils;
import com.lemon.im.utils.SPUtils;
import com.lemon.im.utils.ToastUtils;
import com.lemon.im.utils.UrlFactory;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private Button login;
    private EditText name;
    private EditText password;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        login = findViewById(R.id.btn_login);
        name = findViewById(R.id.et_name);
        password = findViewById(R.id.et_password);
        mImmersionBar.titleBar(R.id.toolbar).keyboardEnable(true).init();
        login.setOnClickListener(v -> {
            if (name.getText().toString().equals("") || password.getText().toString().equals("")) {
                ToastUtils.showToast(getApplicationContext(), "id或密码不能为空！");
                return;
            }
            LoginRequestBean user = new LoginRequestBean(name.getText().toString(), password.getText().toString());
            Log.i(TAG, "initView: " + user);
            okPostRequest("login", UrlFactory.BaseUrl + "/user/login", GsonUtils.toJson(user), LoginResultBean.class, "正在登录……", true);

        });
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);

        if (!TextUtils.equals(whit, "login")) {
            return;
        }
        try {
            LoginResultBean datas = (LoginResultBean) t;
            if (datas.getCode() == 200) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                SPUtils.saveBean(mContext, "user", datas.getData());
            } else {
                ToastUtils.showCenterToast(mContext, datas.getMsg());
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void okResponseError(String whit, String body) {
        super.okResponseError(whit, body);
        Log.i(TAG, "okResponseError: " + body);
    }
}