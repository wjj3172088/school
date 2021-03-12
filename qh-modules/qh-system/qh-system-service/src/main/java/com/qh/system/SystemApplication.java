package com.qh.system;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.qh.common.security.annotation.EnableCustomConfig;
import com.qh.common.security.annotation.EnableRyFeignClients;
import com.qh.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 * 
 * @author
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
public class SystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
