package com.qh.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.qh.common.core.utils.Base64Utils;
import com.qh.common.core.utils.Bases;
import com.qh.common.core.utils.ListUtils;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.core.web.domain.HttpResponseResult;
import com.qh.system.config.SystemPlugSmsConfig;
import com.qh.system.domain.SmsInfo;
import com.qh.system.domain.vo.SmsCloopenResponseResult;
import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.enums.SmsCloopenTemplateEnum;
import com.qh.system.service.ISmsSenderService;
import com.qh.system.utils.RequestHelperUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 容联短信发送实现类
 * @Author: huangdaoquan
 * @Date: 2020/12/1 10:27
 * 官方接口对接文档地址：https://doc.yuntongxun.com/p/5a533de33b8496dd00dce07c
 * @return
 */
@Component("smsCloopenSenderServiceImpl")
public class SmsCloopenSenderServiceImpl implements ISmsSenderService {

	@Autowired
	SystemPlugSmsConfig systemPlugSmsConfig;

	/**
	 * 发送短信
	 * @param smsInfo
	 * @return
	 */
	@Override
	public SmsResponseResult send(SmsInfo smsInfo) {
		try {
			HttpResponseResult result = this.sendMsm(smsInfo);
			if (result == null) {
				return new SmsResponseResult(false, "sendMsm return null");
			}
			if (result.getStatusCode() != HttpStatus.SC_OK) {
				return new SmsResponseResult(false, "status code:" + result.getStatusCode());
			}
			SmsCloopenResponseResult response = this.response(result.getResponseBody());
			if (response == null) {
				return new SmsResponseResult(false, "init response faid:" + result.getResponseBody());
			}

			return "000000".equals(response.getStatusCode()) ? new SmsResponseResult(true, response.getTemplateSMS()) : new SmsResponseResult(false, response.getStatusMsg());
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new SmsResponseResult(false, e.getMessage());
		}
	}

	/**
	 * 用于SigParameter和Authorization 生成使用，并且可以重复使用，因此每天更新一次
	 */
//	private String datetime = getCurSmsDatetimeFormat();


	public SmsCloopenResponseResult response(String result) {
		if (StringUtils.isNotBlank(result)) {
			SmsCloopenResponseResult smsResp = JSON.parseObject(result, SmsCloopenResponseResult.class);
			return smsResp;
		}
		throw new RuntimeException("短信响应异常");
	}

	public HttpResponseResult sendMsm(SmsInfo smsInfo) {
		String url = getSendUrl();
		List<BasicHeader> headers = getHeaders();
		Map<String, String> mapHeaders = new HashMap<String, String>();
		if (!ListUtils.isEmpty(headers)) {
			for (BasicHeader basicHeader : headers) {
				mapHeaders.put(basicHeader.getName(), basicHeader.getValue());
			}
		}

		Map<String, Object> jsonBody = new HashMap<String, Object>();
		jsonBody.put("to", String.join(",", smsInfo.getTo()));
		jsonBody.put("appId", systemPlugSmsConfig.getCloopenAppid());
		jsonBody.put("templateId", SmsCloopenTemplateEnum.getTemplateId(smsInfo.getTemplateEnum()));
		jsonBody.put("datas", smsInfo.getDatas());

		return RequestHelperUtil.sendHttpPostJson(url, JSON.toJSONString(jsonBody),mapHeaders);
	}

	public static String getCurSmsDatetimeFormat() {
		String format = DateUtils.format(new Date(), DateUtils.yyyyMMddHHmmss);
		return format;
	}

	private  String getSendUrl() {
		return String.format(systemPlugSmsConfig.getCloopenSendUrl(), systemPlugSmsConfig.getCloopenAccountSid(), getSig());
	}

	private  String getSig() {
		String sign = Bases.getMD5Str(systemPlugSmsConfig.getCloopenAccountSid() + systemPlugSmsConfig.getCloopenAuthToken() + getCurSmsDatetimeFormat()).toUpperCase();
		return sign;
	}

	private  List<BasicHeader> getHeaders() {
		List<BasicHeader> headers = new ArrayList<>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Authorization", generateAuthorization()));
		return headers;
	}

	private  String generateAuthorization() {
		String authorization = systemPlugSmsConfig.getCloopenAccountSid() + ":" + getCurSmsDatetimeFormat();
		String encodeUpper = Base64Utils.encode(authorization);
		return encodeUpper;
	}

}
