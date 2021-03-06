package com.lemon.im.activity;

import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.lemon.im.R;
import com.lemon.im.base.BaseActivity;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.fragment.AddressBookFragment;
import com.lemon.im.fragment.MyConversationFragment;
import com.lemon.im.fragment.MyFragment;
import com.lemon.im.utils.SPUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private FrameLayout fl;
    private RadioGroup rg;
    private Fragment myFm = null, addressBookFm = null;
    private MyConversationFragment myConversationFragment;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        fl = findViewById(R.id.fl);
        rg = findViewById(R.id.rg);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        rg.setOnCheckedChangeListener(new RadioGroupOnCheckedChangeListener());
        rg.check(R.id.rb_home);
        LoginResultBean.DataBean user = SPUtils.getBean(mContext, "user", LoginResultBean.DataBean.class);

        String token = user.getToken();
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                //消息数据库打开，可以进入到主页面
                Log.i(TAG, "onDatabaseOpened: ");
            }

            @Override
            public void onSuccess(String s) {
                //连接成功
                Log.i(TAG, "onSuccess: " + s);
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                Log.i(TAG, "onError: " + errorCode);
                if (errorCode.equals(RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT)) {
                    //从 APP 服务获取新 token，并重连
                } else {
                    //无法连接 IM 服务器，请根据相应的错误码作出对应处理
                }
            }
        });


    }

    private final class RadioGroupOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            if (myConversationFragment != null) {
                getSupportFragmentManager().beginTransaction().hide(myConversationFragment).commit();
            }
            if (addressBookFm != null) {
                getSupportFragmentManager().beginTransaction().hide(addressBookFm).commit();
            }
            if (myFm != null) {
                getSupportFragmentManager().beginTransaction().hide(myFm).commit();
            }
            switch (checkedId) {
                case R.id.rb_home:
                    if (myConversationFragment == null) {
                        myConversationFragment = new MyConversationFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, myConversationFragment).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(myConversationFragment).commit();
                    }
                    break;
                case R.id.rb_statistic:
                    if (addressBookFm == null) {
                        addressBookFm = new AddressBookFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, addressBookFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(addressBookFm).commit();
                    }
                    break;
                case R.id.rb_my:
                    if (myFm == null) {
                        myFm = new MyFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, myFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(myFm).commit();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}