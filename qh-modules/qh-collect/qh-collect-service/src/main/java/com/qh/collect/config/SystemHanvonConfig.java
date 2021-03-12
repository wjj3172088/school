package com.qh.collect.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 黄道权
 * @Date: 2020/12/24 17:58
 * @Description:汉王考勤服务参数
 */
@Component
@ConfigurationProperties(prefix = "hanvon.service")
@Data
public class SystemHanvonConfig {

    /**
     * 侦听功能是否开启总开关
     */
    private String serverRunswitch;
    /**
     * 服务端口
     */
    private String serverPort;

    /**
     * 服务器IP
     */
    public String serverIP;

    /**
     * 通讯密码
     */
    public String secretKey;

    /**
     * 配置汉王考勤机默认匹配的学校
     */
    public String defaultOrgId;
}
