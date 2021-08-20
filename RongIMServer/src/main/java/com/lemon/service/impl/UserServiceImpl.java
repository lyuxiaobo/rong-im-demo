package com.lemon.service.impl;

import com.lemon.entity.User;
import com.lemon.repository.UserRepository;
import com.lemon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getOneUser(String userId) {
        Optional<User> byUserId = userRepository.findByUserId(userId);

        return byUserId;
    }

    @Override
    public List<User> findAll() {
        List<User> all = userRepository.findAll();
        return all;
    }
}
