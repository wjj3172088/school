package com.qh.system.service;

import com.qh.system.domain.SmsSenderRequestParameter;

public interface ISmsProcessorService {

	/**
	 * 推送短信信息
	 * @param smsReq
	 */
	void pushSms(SmsSenderRequestParameter smsReq);
	
}
