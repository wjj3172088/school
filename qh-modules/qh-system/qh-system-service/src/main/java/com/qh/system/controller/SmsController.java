package com.qh.system.controller;

import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.enums.SmsTemplateEnum;
import com.qh.system.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 短信发送Controller
 * @Author: huangdaoquan
 * @Date: 2020/11/30 13:15
 *
 * @return
 */
@RestController
@RequestMapping("/sms")
@CheckBackendToken
public class SmsController extends BaseController {

    @Autowired
    private ISmsService smsService;

    /**
     *后台发送短信
     * @param mobile 要发送的手机号码 手机号码用 逗号 隔开
     * @param msgContent 要发送的短信变量值
     * @param smsValue 要发送的短信模板
     * @return
     */
    @GetMapping("/smsSendMsg")
    public R<Boolean> smsSendMsg(String mobile,String msgContent,String smsValue) {
        SmsTemplateEnum smsTemplateEnum=SmsTemplateEnum.getSmsTemplateEnum(smsValue);
        if(smsTemplateEnum==null){
            return R.fail("请检查发送短信模板smsValue是否一致");
        }
        Boolean boolSms = smsService.smsSendMsg(mobile,msgContent,smsTemplateEnum);
        return R.ok(boolSms);
    }
}
