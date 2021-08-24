package com.lemon.controller;

import com.lemon.entity.CommonResult;
import com.lemon.entity.User;
import com.lemon.service.UserService;
import com.lemon.util.GetToken;
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
        Optional<User> oneUser = userService.getOneUser(user.getUserId());
        if (oneUser.isPresent()) {
            log.info(oneUser.get().toString());
            if (user.getPassword().equals(oneUser.get().getPassword())) {
                if (oneUser.get().getToken() == null) {
                    oneUser.get().setToken(GetToken.getToken(user.getUserId(), user.getUserId()));
                    oneUser.get().setUpdateTime(new Date());
                    userService.saveUser(oneUser.get());
                }
                return CommonResult.success(oneUser);
            } else {
                return new CommonResult(400, "密码错误", null);
            }
        } else {
            return new CommonResult(400, "该用户不存在", null);
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


}
