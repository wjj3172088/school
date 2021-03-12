package com.qh.basic.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/14 16:25
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "device")
@Data
public class DeviceConfig {
    /**
     * 图片前缀
     */
    private String baseImage;
}
