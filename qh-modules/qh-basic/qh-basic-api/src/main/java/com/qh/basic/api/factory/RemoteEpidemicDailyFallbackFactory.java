package com.qh.basic.api.factory;

import com.qh.basic.api.EpidemicDailyService;
import com.qh.basic.api.domain.ScEpidemicDaily;
import com.qh.common.core.web.domain.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 疫情日报api异常处理
 * @Author: huangdaoquan
 * @Date: 2021/1/20 16:57
 *
 * @return
 */
@Component
public class RemoteEpidemicDailyFallbackFactory implements FallbackFactory<EpidemicDailyService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteEpidemicDailyFallbackFactory.class);

    @Override
    public EpidemicDailyService create(Throwable throwable) {
        log.error("创建疫情日报服务调用失败:{}", throwable.getMessage());
        return new EpidemicDailyService() {
            @Override
            public int batchTodayAddByOrg() {
                return 0;
            }
        };
    }
}
