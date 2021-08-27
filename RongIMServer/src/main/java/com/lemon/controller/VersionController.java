package com.lemon.controller;

import com.lemon.entity.AppVersionEntity;
import com.lemon.entity.CommonResult;
import com.lemon.entity.UpdateAppBean;
import com.lemon.service.AppVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LyuBo
 * @create 2021/8/26 21:12
 */
@RestController
public class VersionController {
    private static final Logger log = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private AppVersionService appVersionService;

    /**
     * 请求更新
     *
     * @param versionCode
     * @return
     */
    @GetMapping("/version/update")
    public CommonResult update(@RequestParam String versionCode) {
        // version只保留一条记录
        UpdateAppBean updateAppBean = new UpdateAppBean();
        if (appVersionService.findAll().size() == 0) {
            updateAppBean.setUpdate("No");
            return new CommonResult(400, "还没发布新版本", updateAppBean);
        } else {
            if (appVersionService.findAll().get(0).getVersionCode() <= Integer.parseInt(versionCode)) {
                return new CommonResult(400, "已经是最新版本", updateAppBean);
            } else {
                AppVersionEntity appVersionEntity = appVersionService.findAll().get(0);
                updateAppBean.setUpdate("Yes");
                updateAppBean.setUpdate_log(appVersionEntity.getChangeLog());
                updateAppBean.setApk_file_url(appVersionEntity.getFileUrl());
                updateAppBean.setNew_version(appVersionEntity.getVersionName());
                return new CommonResult(200, "更新的版本", updateAppBean);
            }
        }
    }

    /**
     * 更新App版本信息
     *
     * @param appVersionEntity
     * @return
     */
    @PostMapping("/version/update")
    public CommonResult register(@RequestBody AppVersionEntity appVersionEntity) {
        log.info(appVersionEntity.toString());
        if (appVersionService.findAll().size() == 0) {
            // 保存
            appVersionService.saveVersion(appVersionEntity);
            return new CommonResult(400, "已插入", appVersionEntity);
        } else {
            // 更新
            AppVersionEntity oldAppVersionEntity = appVersionService.findAll().get(0);
            if (appVersionEntity.getChangeLog() != null) {
                oldAppVersionEntity.setChangeLog(appVersionEntity.getChangeLog());
            }
            oldAppVersionEntity.setVersionCode(appVersionEntity.getVersionCode());
            oldAppVersionEntity.setVersionName(appVersionEntity.getVersionName());
            if (appVersionEntity.getFileUrl() != null) {
                oldAppVersionEntity.setFileUrl(appVersionEntity.getFileUrl());
            }
            appVersionService.saveVersion(oldAppVersionEntity);
            return new CommonResult(200, "已更新", oldAppVersionEntity);
        }
    }
}