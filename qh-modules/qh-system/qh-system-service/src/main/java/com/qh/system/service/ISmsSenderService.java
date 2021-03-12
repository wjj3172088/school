package com.qh.system.service;

import com.qh.system.domain.SmsInfo;
import com.qh.system.api.domain.SmsResponseResult;

/**
 * @Description: 短信发送接口
 * @Author: huangdaoquan
 * @Date: 2021/1/26 13:36
 */
public interface ISmsSenderService {

	/**
	 * 发送短信
	 * @param smsInfoBean
	 * @return
	 */
	SmsResponseResult send(SmsInfo smsInfoBean);
	
}
