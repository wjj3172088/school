package com.qh.basic.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/1 17:58
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "plug.gt")
@Data
public class SystemPlugConfig {
    private String appId;
    public String appKey;
    public String masterSecret;
    public String host;
    public String offline;
    public String logo;
}
