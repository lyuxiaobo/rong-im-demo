package com.lemon.controller;

import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;

/**
 * @author LyuBo
 * @create 2021/8/16 21:40
 */
public class UserController {
    public static void main(String[] args) throws Exception {

        String appKey = "";
        String appSecret = "";

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        User user = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId("hHjap87")
                .setName("RongCloud")
                .setPortrait("http://www.rongcloud.cn/images/logo.png");
        TokenResult result = user.register(userModel);
        System.out.println("getToken:  " + result.toString());

    }
}
