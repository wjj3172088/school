package com.qh.system.domain.vo;

import lombok.Data;

/**
 * 短信响应   云通讯 
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2018年8月10日 上午10:56:13
 */
@Data
public class SmsCloopenResponseResult {
	
	public final static String SUCCESS = "000000";
	
	/**
	 * statusCode为"000000"表示请求发送成功。
	 * statusCode不是"000000"，表示请求发送失败。
	 * 客户服务端可以根据自己的逻辑进行重发或者其他处理
	 */
	private String statusCode;
	
	/**
	 * 成功有信息
	 */
	private String templateSMS;
	
	/**
	 * 失败有信息
	 */
	private String statusMsg;
}
