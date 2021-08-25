package com.lemon.service.impl;

import com.lemon.entity.User;
import com.lemon.entity.UserResponseBean;
import com.lemon.repository.UserRepository;
import com.lemon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author LyuBo
 * @create 2021/8/20 20:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public Optional<User> getOneUser(String userId) {
        Optional<User> byUserId = userRepository.findByUserId(userId);

        return byUserId;
    }

    @Override
    public List<UserResponseBean> findAll() {
        List<User> all = userRepository.findAll();
        List<UserResponseBean> userResponseBeanList = new ArrayList<>();
        all.forEach(user -> {
            userResponseBeanList.add(new UserResponseBean(user.getId(), user.getUserId(), user.getName(), user.getImgUrl()));
        });
        return userResponseBeanList;
    }
}
