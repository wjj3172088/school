package com.qh.system.api.factory;

import com.qh.system.api.OrgService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: 黄道权
 * @Date: 2020/12/28 15:45
 * @Description:
 */
@Component
public class RemoteOrgFallbackFactory implements FallbackFactory<OrgService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteOrgFallbackFactory.class);

    @Override
    public OrgService create(Throwable throwable) {
        log.error("学校服务调用失败:{}", throwable.getMessage());
        return new OrgService() {
            @Override
            public String selectOrgNameById(String orgId) {
                return null;
            }
        };
    }
}
