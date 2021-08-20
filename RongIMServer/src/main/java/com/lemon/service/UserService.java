package com.lemon.service;

import com.lemon.entity.User;

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
    void saveUser(User user);

    Optional<User> getOneUser(String userId);

    List<User> findAll();
}
