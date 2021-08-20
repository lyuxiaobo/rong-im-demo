package com.lemon.im.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lemon.im.R;
import com.lemon.im.base.BaseActivity;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.utils.SPUtils;


/**
 * 闪屏页
 *
 * @author Lyubo
 * @date 2021/8/14
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private TextView tvCountTime;
    private CardView cv;
    private CountDownTimer countDownTimer;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        cv = findViewById(R.id.cv);
        tvCountTime = findViewById(R.id.tvCountTime);
    }

    @Override
    protected void initData() {
        startSplash();
    }

    private void startSplash() {

        countDownTimer = new CountDownTimer(1 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountTime.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                isLogin();
            }
        };
        countDownTimer.start();

        cv.setVisibility(View.VISIBLE);
        tvCountTime.setOnClickListener(v -> {
            isLogin();
            countDownTimer.cancel();
            countDownTimer = null;
        });
    }

    public void isLogin() {
        if (SPUtils.getBean(mContext, "user", LoginResultBean.DataBean.class) == null) {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

}
