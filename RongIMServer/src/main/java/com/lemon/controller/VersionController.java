package com.lemon.controller;

import com.lemon.entity.UpdateAppBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LyuBo
 * @create 2021/8/26 21:12
 */
@RestController
public class VersionController {
    @GetMapping("/version/update")
    public UpdateAppBean update() {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        updateAppBean.setUpdate("Yes");
        updateAppBean.setUpdate_log("测试测试测试");
        updateAppBean.setApk_file_url("http://39.107.182.111/apk/im.apk");
        updateAppBean.setNew_version("1.3");
        return updateAppBean;
    }
}
