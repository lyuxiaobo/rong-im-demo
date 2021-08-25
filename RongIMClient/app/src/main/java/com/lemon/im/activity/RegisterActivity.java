package com.lemon.im.activity;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lemon.im.R;
import com.lemon.im.base.BaseActivity;
import com.lemon.im.bean.LoginRequestBean;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.utils.GsonUtils;
import com.lemon.im.utils.ToastUtils;
import com.lemon.im.utils.UrlFactory;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    private EditText id;
    private EditText password;
    private EditText name;
    private Button register;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        UpTitle("注册用户");
        mImmersionBar.titleBar(R.id.register_title).statusBarDarkFont(true, 0.2f).keyboardEnable(true).init();

        id = findViewById(R.id.et_userid);
        password = findViewById(R.id.et_password);
        name = findViewById(R.id.et_name);
        register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        register.setOnClickListener(v -> {
            if (name.getText().toString().equals("") || password.getText().toString().equals("")) {
                ToastUtils.showToast(getApplicationContext(), "id或密码不能为空！");
                return;
            }

            LoginRequestBean loginRequestBean = new LoginRequestBean(id.getText().toString().trim(),name.getText().toString().trim(), password.getText().toString().trim());
            Log.i(TAG, "initData: " + loginRequestBean);
            okPostRequest("register", UrlFactory.BaseUrl + "/user/register", GsonUtils.toJson(loginRequestBean), LoginResultBean.class, "正在……", true);
        });
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit, "register")) {
            return;
        }
        LoginResultBean datas = (LoginResultBean) t;
        if (datas.getCode() == 200) {
            new QMUITipDialog.Builder(mContext).setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("注册成功，欢迎您").create().show();
            finish();
        } else {
            ToastUtils.showCenterToast(mContext, datas.getMsg());
        }
    }
}