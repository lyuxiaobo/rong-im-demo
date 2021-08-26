package com.lemon.entity;

import java.io.Serializable;

/**
 * @author LyuBo
 * @create 2021/8/26 21:10
 */
public class UpdateAppBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * update : Yes
     * new_version : xxxxx
     * apk_url : http://cdn.the.url.of.apk/or/patch
     * update_log : xxxx
     * delta : false
     * new_md5 : xxxxxxxxxxxxxx
     * target_size : 601132
     */
    //是否有新版本
    private String update;
    //新版本号
    private String new_version;
    //新app下载地址
    private String apk_file_url;
    //更新日志
    private String update_log;
    //配置默认更新dialog 的title
    private String update_def_dialog_title;
    //新app大小
    private String target_size;
    //是否强制更新
    private boolean constraint;
    //md5
    private String new_md5;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getUpdate_def_dialog_title() {
        return update_def_dialog_title;
    }

    public void setUpdate_def_dialog_title(String update_def_dialog_title) {
        this.update_def_dialog_title = update_def_dialog_title;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }

    public String getNew_md5() {
        return new_md5;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }
}
