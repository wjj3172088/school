
package com.qh.system.enums;

/**
 * 短信模块
 * @project SmartSafeCampusService
 * @package com.qinghai.service.plug.sms.cloopen
 * @filename SmsCloopenTemplateEnum.java
 * @createtime 2020年8月3日上午9:00:44  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
public enum SmsCloopenTemplateEnum {
	//模板对应运营商内设置的模块ID
	REGISTER_SMS(SmsTemplateEnum.REGISTER_BASE_SMS, 1),
	
	SMSLOGIN(SmsTemplateEnum.SMS_BASE_LOGIN, 433395),
	
	SMSBIND(SmsTemplateEnum.SMS_BASE_BIND, 433395),
	
	SMSPASS(SmsTemplateEnum.SMS_BASE_PASS, 433395),
	/**烟感自检*/
	SMOKETEST(SmsTemplateEnum.SMS_BASE_SMOKETEST, 434082),
	/**火灾告警*/
	FIREALARM(SmsTemplateEnum.SMS_BASE_FIREALARM, 433389),
	/**后台登录短信验证码*/
	SYS_LOGIN(SmsTemplateEnum.SMS_BASE_SYS_LOGIN, 484670),

	/**后台提醒激活短信语*/
	SYS_ACTIVATION(SmsTemplateEnum.SMS_BASE_SYS_ACTIVATION, 796157);
	
	private SmsTemplateEnum templateFlag;
	private int templateId;

	private SmsCloopenTemplateEnum(SmsTemplateEnum enu, int templateId) {
		this.templateFlag = enu;
		this.templateId = templateId;
	}

	public SmsTemplateEnum getTemplateFlag() {
		return templateFlag;
	}

	public int getTemplateId() {
		return templateId;
	}

	public static int getTemplateId(SmsTemplateEnum enu) {
		// 通过enum.values()获取所有的枚举值
		for (SmsCloopenTemplateEnum codeAndMessage : SmsCloopenTemplateEnum.values()) {
			// 通过enum.get获取字段值
			if (codeAndMessage.getTemplateFlag() == enu) {
				return codeAndMessage.getTemplateId();
			}
		}
		return 0;
	}
}
