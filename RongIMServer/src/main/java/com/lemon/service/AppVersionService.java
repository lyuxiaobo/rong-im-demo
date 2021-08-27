package com.lemon.service;

import com.lemon.entity.AppVersionEntity;

import java.util.List;

/**
 * @author LyuBo
 * @create 2021/8/20 20:10
 */

public interface AppVersionService {


    List<AppVersionEntity> findAll();
    AppVersionEntity saveVersion(AppVersionEntity appVersionEntity);
}
