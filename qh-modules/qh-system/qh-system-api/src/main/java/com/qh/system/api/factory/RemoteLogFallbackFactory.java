package com.qh.system.api.factory;

import com.qh.common.core.web.domain.R;
import com.qh.system.api.RemoteLogService;
import com.qh.system.api.domain.SysOperLog;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 *
 * @author
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService() {
            @Override
            public R saveLog(SysOperLog sysOperLog) {
                return null;
            }

            @Override
            public R saveLogininfor(String orgId, String username, String status, String message) {
                return null;
            }
        };

    }
}
