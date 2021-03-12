package com.qh.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.qh.common.security.annotation.EnableCustomConfig;
import com.qh.common.security.annotation.EnableRyFeignClients;
import com.qh.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringCloudApplication
public class GenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
