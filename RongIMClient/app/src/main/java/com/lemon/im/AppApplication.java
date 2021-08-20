package com.lemon.im;

/**
 * @author lyubo
 * @date 2021/8/16
 */

import android.app.Application;

import com.lemon.im.activity.HomeActivity;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.RouteUtils;

/**
 * 应用启动时，判断用户是否已接受隐私协议，如果已接受，正常初始化；否则跳转到隐私授权页面请求用户授权。
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String appKey = "sfci50a7sjbli";
        //第一个参数必须传应用上下文
        RongIM.init(this, appKey);

        /**
         * 注册自定义 Activity， 注册之后将替换 SDK 默认 Activity。
         * @param activityType {@link RongActivityType} SDK 内置各 activity 类型。
         * @param activity 自定义 activity.
         */
        RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationListActivity, HomeActivity.class);

    }
}

