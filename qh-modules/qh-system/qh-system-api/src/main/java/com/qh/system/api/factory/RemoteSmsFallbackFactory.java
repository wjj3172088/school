package com.qh.system.api.factory;

import com.qh.common.core.web.domain.R;
import com.qh.system.api.SmsService;
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
public class RemoteSmsFallbackFactory implements FallbackFactory<SmsService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteSmsFallbackFactory.class);

    @Override
    public SmsService create(Throwable throwable) {
        log.error("短信服务调用失败:{}", throwable.getMessage());
        return new SmsService() {
            @Override
            public R<Boolean> smsSendMsg(String mobile, String msgContent,  String smsValue) {
                return null;
            }
        };
    }
}
