package com.qh.basic.api;

import com.qh.basic.api.factory.RemoteEpidemicDailyFallbackFactory;
import com.qh.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description: 疫情日报API
 * @Author: huangdaoquan
 * @Date: 2021/1/20 17:31
 */
@FeignClient(contextId = "epidemicDailyService", value = ServiceNameConstants.BASIC_SERVICE, fallbackFactory = RemoteEpidemicDailyFallbackFactory.class)
public interface EpidemicDailyService {

    /**
     * 新增疫情日报
     * @return
     */
    @PostMapping(value = "/daily/batchTodayAddByOrg")
    int batchTodayAddByOrg();

}
