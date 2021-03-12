
package com.qh.system.service;

import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.enums.SmsTemplateEnum;

/**
 * @project SmartSafeCampusService
 * @package com.qinghai.service.provider.common
 * @filename ISmsService.java
 * @createtime 2020年8月1日上午10:54:58  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
public interface ISmsService {

	/**
	 * 不通过Queue，自定义发送短信
	 * @param mobile
	 * @param msgContent
	 * @param smsTemplateEnum
	 * @return
	 */
	Boolean smsSendMsg(String mobile, String msgContent, SmsTemplateEnum smsTemplateEnum);

	/**
	 * 发送验证码
	 * @Title:
	 * @Description: TODO
	 * @param mobile
	 * @param codeType
	 * @return  
	 * @return boolean
	 * @throws
	 */
	boolean smsSendMsg(String mobile, int codeType);
	/**
	 * 发送验证码  具体号码从用户信息中获取
	 * @Title:
	 * @Description: TODO
	 * @param moveAccNumId
	 * @param codeType
	 * @return  
	 * @return boolean
	 * @throws
	 */
	boolean smsSendMsg(int moveAccNumId, int codeType);
}
