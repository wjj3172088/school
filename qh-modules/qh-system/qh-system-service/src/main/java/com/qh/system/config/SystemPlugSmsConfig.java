package com.qh.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Description: 读取nacos配置文件常量config类
 * @Author: huangdaoquan
 * @Date: 2020/12/2 13:54
 *
 * @return
 */
@Component
@ConfigurationProperties(prefix = "plug.sms")
@Data
public class SystemPlugSmsConfig {

    /**sms是否可以发送*/
    private String cansend;

    /**使用的运营商标识  1:cloopen  对应ENUM中的CODE*/
    private String defaultProvider;

    /**短信服务 account_sid*/
    private String cloopenAccountSid;

    /**账户授权令牌 */
    private String cloopenAuthToken;

    /**账户Id(appId)*/
    private String cloopenAppid;

    /**sms 短信发送接口URL %s 第一个为sms.account_sid，第二个为MD5加密（账户Id + 账户授权令牌 + 时间戳）*/
    private String cloopenSendUrl;


}
