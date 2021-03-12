package com.qh.system.api;

import com.qh.common.core.constant.ServiceNameConstants;
import com.qh.common.core.web.domain.R;
import com.qh.system.api.factory.RemoteDictDataFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 黄道权
 * @Date: 2020/11/30 21:44
 * @Description:
 */
@FeignClient(contextId = "smsService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDictDataFallbackFactory.class)
public interface SmsService {


    /**
     *后台发送短信
     * @param mobile 要发送的手机号码 手机号码用 逗号 隔开
     * @param msgContent 要发送的短信变量值
     * @param smsValue 要发送的短信模板
     * @return
     */
    @GetMapping(value = "/sms/smsSendMsg")
    R<Boolean> smsSendMsg(@RequestParam("mobile") String mobile, @RequestParam("msgContent") String msgContent, @RequestParam("smsValue") String smsValue);
}
