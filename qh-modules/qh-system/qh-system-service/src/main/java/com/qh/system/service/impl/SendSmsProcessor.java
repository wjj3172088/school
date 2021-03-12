package com.qh.system.service.impl;

import com.qh.system.domain.SmsSenderRequestParameter;
import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.enums.SmsSenderRequestProviderEnum;
import com.qh.system.service.ISmsProcessorService;
import com.qh.system.service.ISmsSenderService;
import com.qh.system.config.SystemPlugSmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 短信发送队列    202008 fcj 修改   完成发送
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2018年8月13日 下午6:15:31
 */
@Component
public class SendSmsProcessor extends Thread implements ISmsProcessorService {

	private static final Logger logger = LoggerFactory.getLogger(SendSmsProcessor.class);

	@Autowired
	SystemPlugSmsConfig systemPlugSmsConfig;

	
	@Autowired
	@Qualifier("smsCloopenSenderServiceImpl")
	ISmsSenderService smsCloopenSendServiceImpl;
	/**
	 * FIXME 此处没有考虑停机后没有发送完，会导致队列中的数据丢失问题，断网没有做重发
	 */
	public static BlockingQueue<SmsSenderRequestParameter> smsReqQueue = new LinkedBlockingQueue<>();
	/**
	 * 是否允许发送
	 */
	private boolean canPush = true;

	private boolean canTake = true;
	
	private  volatile boolean isSleep=false;
	
	private static Object objectLock=new Object();

	/**
	 * 推送短信信息
	 * @param smsReq
	 */
	@Override
	public void pushSms(SmsSenderRequestParameter smsReq) {
		try {
		if (canPush) {
			smsReqQueue.add(smsReq);
			if(isSleep) {
				logger.debug("thread is sleep ,notify");
				synchronized (objectLock) {
					objectLock.notify();
				}
			}
			logger.info(" smsReqQueue size:"+smsReqQueue.size());
			System.out.println(" smsReqQueue size:"+smsReqQueue.size());
		}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

	@PostConstruct
	public void init() {
		//初始化枚举对应的接口实现
		SmsSenderRequestProviderEnum.setSender(1, smsCloopenSendServiceImpl,"Cloopen");
		// bean 初始化完成后 启动线程
		this.start();
	}

	@PreDestroy
	public void destory() {
		// 销毁短信处理队列
		this.canPush = false;
		while (true) {
			if (smsReqQueue.isEmpty()) {
				this.canTake = false;
				break;
			}
		}
	}

	@Override
	public void run() {
		System.out.println("start");
		synchronized (objectLock) {
			while (canTake) {
				try {
					SmsSenderRequestParameter smsReq = SendSmsProcessor.smsReqQueue.take();
					if (smsReq == null || smsReq.getSmsInfo() == null) {
						isSleep = true;
						logger.debug("Queue is null ,wait");
						System.out.println("Queue is null ,notify");
						objectLock.wait();
						isSleep = false;
					}
					logger.debug("last");
					//// 读取sms能否发送的状态
					if (Boolean.valueOf(systemPlugSmsConfig.getCansend())) {
						// 发送短信
						// 获取对应的发送运营商
						ISmsSenderService senderService = smsReq.getSmsProviderEnum().getSender();
						SmsResponseResult response = senderService.send(smsReq.getSmsInfo());
						if (response == null || false == response.isSuccess()) {
							logger.error("短息发送失败:{}, 短信类型 :{} 短信通道:{}",
									smsReq.getSmsInfo().toString(),
									smsReq.getSmsInfo().getTemplateEnum().getSmsValue());
						}
					}
				} catch (InterruptedException e) {
					logger.error("短信 queue take req exception ", e);
				} catch (Exception e) {
					logger.error("短信出现异常 ", e);
				}
			}
		}
	}
}
