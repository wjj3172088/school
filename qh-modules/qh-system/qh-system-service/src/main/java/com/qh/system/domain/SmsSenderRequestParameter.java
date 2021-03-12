package com.qh.system.domain;

import com.qh.system.enums.SmsSenderRequestProviderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@Setter
@Getter
public class SmsSenderRequestParameter {
	
	private SmsInfo  smsInfo;
	
	private SmsSenderRequestProviderEnum smsProviderEnum;

	@Generated("SparkTools")
	private SmsSenderRequestParameter(Builder builder) {
		this.smsInfo = builder.smsInfo;
		this.smsProviderEnum = builder.smsProviderEnum;
	}

	public SmsSenderRequestParameter() {
		super();
	}

	public SmsSenderRequestParameter(SmsInfo SmsInfo, SmsSenderRequestProviderEnum smsProviderEnum) {
		super();
		this.smsInfo=SmsInfo;
		this.smsProviderEnum = smsProviderEnum;
	}

	/**
	 * Creates builder to build {@link com.qinghai.service.plug.sms.base.SmsSenderRequestParameter}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link com.qinghai.service.plug.sms.base.SmsSenderRequestParameter}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private SmsInfo smsInfo;
		private SmsSenderRequestProviderEnum smsProviderEnum;

		private Builder() {
		}

		public Builder SmsInfo(SmsInfo SmsInfo) {
			this.smsInfo = SmsInfo;
			return this;
		}

		public Builder SmsProviderEnum(SmsSenderRequestProviderEnum smsProviderEnum) {
			this.smsProviderEnum = smsProviderEnum;
			return this;
		}

		public SmsSenderRequestParameter build() {
			return new SmsSenderRequestParameter(this);
		}
	}

}
