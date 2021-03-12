package com.qh.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Description: 读取nacos配置文件plug.sms的常量config类
 * @Author: huangdaoquan
 * @Date: 2020/12/2 13:54
 *
 * @return
 */
@Component
@ConfigurationProperties(prefix = "oss.images")
@Data
public class SystemOssImagesConfig {

    /**sms是否可以发送*/
    private String name;

    /**文件根目录 /jjhdata/java/qinghai */
    private String filePath;

    /**服务器根路径*/
    private String domain;

    /**ueditor 路径 */
    private String ueditor;

    /**OSS_END_POINT 路径 */
    private String point;

    /** 服务器图片 路径  */
    private String baseImgUrl;

    /**
     * 图片存放 Bucket 名称
     * Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
     * Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
     */
    private String bucketName;

    /**
     * accessKeyId
     * OSS的访问密钥 KeyId，您可以在控制台上创建和查看
     * 都没有空格，从控制台复制时请检查并去除多余的空格。
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     * OSS的访问密钥 KeySecret，您可以在控制台上创建和查看
     * 都没有空格，从控制台复制时请检查并去除多余的空格。
     */
    private String accessKeySecret;
}
