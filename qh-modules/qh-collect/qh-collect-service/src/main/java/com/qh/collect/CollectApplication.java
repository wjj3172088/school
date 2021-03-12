package com.qh.collect;

import com.qh.common.security.annotation.EnableCustomConfig;
import com.qh.common.security.annotation.EnableRyFeignClients;
import com.qh.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 硬件设备数据采集模块
 * 
 * @author
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
@EnableAsync
public class CollectApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CollectApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  硬件设备数据采集模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
