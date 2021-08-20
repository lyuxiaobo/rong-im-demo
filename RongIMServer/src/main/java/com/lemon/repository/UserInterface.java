package com.lemon.repository;

import com.lemon.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author LyuBo
 * @create 2021/8/20 16:05
 */
public interface UserInterface extends CrudRepository<User, Long> {
    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    Iterable<User> findAll();

}
