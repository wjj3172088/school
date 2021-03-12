
package com.qh.system.service.impl;

import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.ListHelper;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.domain.SmsInfo;
import com.qh.system.domain.SmsSenderRequestParameter;
import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.enums.SmsSenderRequestProviderEnum;
import com.qh.system.enums.SmsTemplateEnum;
import com.qh.system.service.ISmsSenderService;
import com.qh.system.service.ISmsService;
import com.qh.system.utils.CacheRedisKeyConfigure;
import com.qh.system.config.SystemPlugSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @project SmartSafeCampusService
 * @package com.qinghai.service.provider.common
 * @filename SmsServiceImpl.java
 * @createtime 2020年8月1日上午10:59:34
 * @author fcj2593@163.com
 * @todo TODO
 */
@Service
public class SmsServiceImpl implements ISmsService {
	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	SendSmsProcessor sendSmsProcessor;

	@Autowired
	ISmsSenderService senderService;

	@Autowired
	SystemPlugSmsConfig systemPlugSmsConfig;

	/**
	 * 不通过Queue，自定义发送短信
	 * @param mobile
	 * @param msgContent
	 */
	@Override
	public Boolean smsSendMsg(String mobile,String msgContent,SmsTemplateEnum smsTemplateEnum){
		// 发送短信
		SmsInfo smsInfoBean = SmsInfo.builder()
				.Datas(new ListHelper<String>().Add(msgContent).getList())
				.To(new ListHelper<String>().Add(mobile).getList()).TemplateEnum(smsTemplateEnum)
				.To(new ListHelper<String>().Add(mobile).getList()).TemplateEnum(smsTemplateEnum)
				.build();
		SmsResponseResult response = senderService.send(smsInfoBean);
		if(response!=null && response.isSuccess()) {
			return true;
		} else{
			return false;
		}
	}

	@Override
	public boolean smsSendMsg(String mobile, int codeType) {
		// 2:登录获取验证码3:修改绑定手机获取验证码4：修改密码获取验证码  5 添加子用户
		String redisCodeType = "";
		switch (codeType) {
		case 2:
			redisCodeType = CacheRedisKeyConfigure.SSCL_LOGINVAL_CODE;
			break;
		case 3:
			redisCodeType = CacheRedisKeyConfigure.SSCL_MODPHOVAL_CODE;
			break;
		case 4:
			redisCodeType = CacheRedisKeyConfigure.SSCL_MODPWDVAL_CODE;
			break;
		case 5:
			redisCodeType = CacheRedisKeyConfigure.SSCL_ADDACC_CODE;
			break;
		default:

			throw new BizException(CodeEnum.NOT_EXIST, "参数错误");
		}
		// 发送短信
		SmsInfo smsInfoBean = SmsInfo.builder()
				.Datas(new ListHelper<String>().Add("").Add("5").getList())
				.To(new ListHelper<String>().Add(mobile).getList()).TemplateEnum(SmsTemplateEnum.SMS_BASE_SYS_ACTIVATION)
				.build();
		SmsSenderRequestParameter parameter = SmsSenderRequestParameter.builder().SmsInfo(smsInfoBean)
				.SmsProviderEnum(getDefaultSmsSenderRequestProviderEnum()).build();
		return true;
	}

	/**
	 * 获取默认短信运营商,从配置文件中获取 @Title: @Description: TODO @return @return
	 * SmsSenderRequestProviderEnum @throws
	 */
	private SmsSenderRequestProviderEnum getDefaultSmsSenderRequestProviderEnum() {
		//获取默认短信运营商
		return SmsSenderRequestProviderEnum
				.getSenderProviderEnum(Integer.parseInt(systemPlugSmsConfig.getDefaultProvider()));
	}

	@Override
	public boolean smsSendMsg(int moveAccNumId, int codeType) {
		// TODO Auto-generated method stub
		return false;
	}

}
