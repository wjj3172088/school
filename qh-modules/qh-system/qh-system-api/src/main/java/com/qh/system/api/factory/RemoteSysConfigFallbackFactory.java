package com.qh.system.api.factory;

import com.qh.common.core.web.domain.R;
import com.qh.system.api.RemoteSysConfigService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/25 15:53
 * @Description:
 */
@Component
public class RemoteSysConfigFallbackFactory implements FallbackFactory<RemoteSysConfigService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteSysConfigFallbackFactory.class);

    @Override
    public RemoteSysConfigService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteSysConfigService() {
            @Override
            public R<Integer> getIntConfigByKey(String configKey) {
                return null;
            }
            @Override
            public R<String> getStringConfigByKey(String configKey) {
                return null;
            }
        };
    }
}
