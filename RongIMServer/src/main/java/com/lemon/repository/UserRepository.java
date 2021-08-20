package com.lemon.repository;

import com.lemon.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author LyuBo
 * @create 2021/8/20 16:05
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    List<User> findAll();

    /**
     * 根据name查找用户
     *
     * @param userId
     * @return
     */
    Optional<User> findByUserId(String userId);
}
