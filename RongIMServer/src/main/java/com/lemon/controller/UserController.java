package com.lemon.controller;

import com.lemon.entity.CommonResult;
import com.lemon.entity.User;
import com.lemon.service.UserService;
import io.rong.RongCloud;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * @author LyuBo
 * @create 2021/8/16 21:40
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public CommonResult login(@RequestBody User user) throws Exception {
        log.info(user.toString());
        if (!user.getUserId().equals("") && !user.getPassword().equals("")) {
            Optional<User> oneUser = userService.getOneUser(user.getUserId());
            log.info(oneUser.get().toString());
            if (oneUser.get().getToken() == null) {
                oneUser.get().setToken(getToken(user.getUserId(), user.getUserId()));
                oneUser.get().setUpdateTime(new Date());
                userService.saveUser(oneUser.get());
                return CommonResult.success(oneUser);
            }
            return CommonResult.success(oneUser);
        } else {
            return CommonResult.fail("空值");
        }
    }

    @PostMapping("/user/register")
    public CommonResult register(@RequestBody User user) {
        log.info(user.toString());
        if (user.getPassword() != null && user.getUserId() != null) {
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userService.saveUser(user);
            return CommonResult.success(null);
        } else {
            return CommonResult.fail(null);
        }
    }

    @GetMapping("/user/all")
    public CommonResult getAllUser() {
        return CommonResult.success(userService.findAll());
    }

    public static String getToken(String id, String name) throws Exception {
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
