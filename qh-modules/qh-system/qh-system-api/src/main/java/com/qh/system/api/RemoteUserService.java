package com.qh.system.api;

import com.qh.common.core.constant.ServiceNameConstants;
import com.qh.common.core.web.domain.R;
import com.qh.system.api.factory.RemoteUserFallbackFactory;
import com.qh.system.api.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务
 * 
 * @author 
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping(value = "/user/info/{username}")
    R<UserInfo> getUserInfo(@PathVariable("username") String username);
}
