package com.lemon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LyuBo
 * @create 2021/8/27 20:49
 */
@Entity
@Table(name = "app_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppVersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //适用平台
    private String platform;
    //版本号
    private int versionCode;
    //版本名称
    private String versionName;
    //上传时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    //修改日志
    @Lob
    private String changeLog;
    //下载次数
    private long downloadCnt;
    //下载地址
    private String fileUrl;
    //文件名
    private String fileName;
    //文件大小
    private long fileSize;
    //1：正常0：锁定-1：删除
    private int status;
}
