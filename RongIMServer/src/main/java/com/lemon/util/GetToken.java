package com.lemon.util;

import io.rong.RongCloud;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;

/**
 * @author LyuBo
 * @create 2021/8/24 10:15
 */
public class GetToken {

    public static String getToken(String id, String name) throws Exception {
        // 这里需要补充你的appKey和appSecret
        String appKey = "";
        String appSecret = "";

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        io.rong.methods.user.User user = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId(id)
                .setName(name)
                .setPortrait("http://www.rongcloud.cn/images/logo.png");
        TokenResult result = user.register(userModel);
        System.out.println("getToken:  " + result.toString());
        return result.getToken();
    }
}
