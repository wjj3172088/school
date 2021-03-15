package com.qh.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.qh.common.security.annotation.EnableCustomConfig;
import com.qh.common.security.annotation.EnableRyFeignClients;
import com.qh.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 系统模块
 * 
 * @author
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
@EnableAsync
public class BasicApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BasicApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ   基础模块启动成功    ლ(´ڡ`ლ)ﾞ ");
    }
}
