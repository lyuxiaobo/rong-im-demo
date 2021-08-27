package com.lemon.service.impl;

import com.lemon.entity.AppVersionEntity;
import com.lemon.repository.AppVersionRepository;
import com.lemon.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LyuBo
 * @create 2021/8/27 21:05
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Autowired
    private AppVersionRepository appVersionRepository;

    @Override
    public List<AppVersionEntity> findAll() {
        return appVersionRepository.findAll();
    }

    @Override
    public AppVersionEntity saveVersion(AppVersionEntity appVersionEntity) {
        return appVersionRepository.save(appVersionEntity);
    }
}
