package com.lemon.repository;

import com.lemon.entity.AppVersionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author LyuBo
 * @create 2021/8/20 16:05
 */
public interface AppVersionRepository extends CrudRepository<AppVersionEntity, Long> {
    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    List<AppVersionEntity> findAll();
}
