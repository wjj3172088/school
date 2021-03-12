
package com.qh.system.enums;

/**
 * @project SmartSafeCampusService
 * @package com.qinghai.service.plug.sms
 * @filename SmsTemplateEnum.java
 * @createtime 2020年8月3日上午9:01:39  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
public enum SmsTemplateEnum {

	REGISTER_BASE_SMS(1, "注册短信"),
	SMS_BASE_LOGIN(1, "登录"),
	
	SMS_BASE_BIND(2, "绑定"),
	
	SMS_BASE_PASS(3, "密码"),
	/**烟感自检*/
	SMS_BASE_SMOKETEST(3, "烟感自检"),
	/**火灾告警*/
	SMS_BASE_FIREALARM(3, "火灾告警"),
	/**后台登录短信验证码*/
	SMS_BASE_SYS_LOGIN(4, "后台登录短信验证码"),

	/**后台提醒激活短信语*/
	SMS_BASE_SYS_ACTIVATION(4, "家长账号激活引导");
	
	
	
	
	private int smsType;
	private String smsValue;

	private SmsTemplateEnum(int smsType, String smsValue) {
		this.smsType = smsType;
		this.smsValue = smsValue;
	}

	public int getSmsType() {
		return smsType;
	}

	public String getSmsValue() {
		return smsValue;
	}

	/**
	 * 根据code值获取对应的枚举
	 * @return 返回匹配的枚举
	 */
	public static SmsTemplateEnum getSmsTemplateEnum(String smsValue) {
		for (SmsTemplateEnum smsTemplateEnum : SmsTemplateEnum.values()) {
			if (smsTemplateEnum.smsValue.equals(smsValue)) {
				return smsTemplateEnum;
			}
		}
		return null;
	}


}
