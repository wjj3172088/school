package com.qh.system.api;

import com.qh.common.core.constant.ServiceNameConstants;
import com.qh.common.core.web.domain.R;
import com.qh.system.api.factory.RemoteSysConfigFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/25 15:55
 * @Description:
 */
@FeignClient(contextId = "remoteSysConfigService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory =
        RemoteSysConfigFallbackFactory.class)
public interface RemoteSysConfigService {
    /**
     * 根据参数键名查询参数值
     *
     * @param configKey 参数键名
     * @return 结果
     */
    @GetMapping(value = "/config/sysConfig/Integer/{configKey}")
    R<Integer> getIntConfigByKey(@PathVariable("configKey") String configKey);
    /**
     * 根据参数键名查询参数值
     *
     * @param configKey 参数键名
     * @return 结果
     */
    @GetMapping(value = "/config/sysConfig/String/{configKey}")
    R<String> getStringConfigByKey(@PathVariable("configKey") String configKey);
}
