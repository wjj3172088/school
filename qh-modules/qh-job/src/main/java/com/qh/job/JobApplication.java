package com.qh.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.qh.common.security.annotation.EnableCustomConfig;
import com.qh.common.security.annotation.EnableRyFeignClients;
import com.qh.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringCloudApplication
public class JobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(JobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}