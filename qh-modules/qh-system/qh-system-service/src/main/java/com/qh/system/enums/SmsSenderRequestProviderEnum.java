package com.qh.system.enums;

import com.qh.system.service.ISmsSenderService;

import java.util.Arrays;

public enum SmsSenderRequestProviderEnum {

	CLOOPERN(1,null,"");

	private int type;
	
	private String senderProviderName;

	private ISmsSenderService sender;

	private SmsSenderRequestProviderEnum(int type, ISmsSenderService sender, String senderProviderName) {
		this.type = type;
		this.sender = sender;
		this.senderProviderName=senderProviderName;
	}
	
	public static void setSender(int type, ISmsSenderService sender,String senderProviderName) {
		for (SmsSenderRequestProviderEnum codeAndMessage : SmsSenderRequestProviderEnum.values()) {
			// 通过enum.get获取字段值
			if (codeAndMessage.getType() == type) {
				codeAndMessage.sender = sender;
				codeAndMessage.senderProviderName=senderProviderName;
			}
		}
//		throw new IllegalArgumentException("Unknown enum type " + type + ", Allowed values are " + Arrays.toString(values()));
	}
	
	public static SmsSenderRequestProviderEnum getSenderProviderEnum(int type) {
		for (SmsSenderRequestProviderEnum codeAndMessage : SmsSenderRequestProviderEnum.values()) {
			// 通过enum.get获取字段值
			if (codeAndMessage.getType() == type) {
				return codeAndMessage;
			}
		}
		throw new IllegalArgumentException("Unknown get enum type " + type + ", Allowed values are " + Arrays.toString(values()));
	}

	public ISmsSenderService getSender() {
		return sender;
	}

	public int getType() {
		return type;
	}

	public String getSenderProviderName() {
		return senderProviderName;
	}

}
