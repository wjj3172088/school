package com.qh.system.api;

import com.qh.common.core.constant.ServiceNameConstants;
import com.qh.system.api.factory.RemoteDictDataFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 黄道权
 * @Date: 2020/11/17 21:44
 * @Description:
 */
@FeignClient(contextId = "orgService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDictDataFallbackFactory.class)
public interface OrgService {

    /**
     * 根据OrgId获取学校名称 OrgName
     * @param orgId
     * @return
     */
    @GetMapping(value = "/school/selectOrgNameById")
    String selectOrgNameById(@RequestParam("orgId") String orgId);
}
