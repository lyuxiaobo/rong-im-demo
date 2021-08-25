package com.lemon.service;

import com.lemon.entity.User;
import com.lemon.entity.UserResponseBean;

import java.util.List;
import java.util.Optional;

/**
 * @author LyuBo
 * @create 2021/8/20 20:10
 */

public interface UserService {
    /**
     * 添加用户
     *
     * @param user
     */
    User saveUser(User user);

    Optional<User> getOneUser(String userId);

    List<UserResponseBean> findAll();
}
