package com.qh.common.security.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;

/**
 * Feign配置注册
 *
 * @author
 **/
@Configuration
public class OAuth2FeignConfig
{
    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return new OAuth2FeignRequestInterceptor();
    }
}
