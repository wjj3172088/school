package com.qh.system.domain;

import com.qh.system.enums.SmsTemplateEnum;
import lombok.Data;

import javax.annotation.Generated;
import java.util.Collections;
import java.util.List;

/**
 * 短信发送模板
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2018年8月10日 上午10:53:45
 */
@Data
public class SmsInfo {

	/**
	 * 短信接收端手机号码集合，用英文逗号分开，每批发送的手机号数量不得超过200个
	 */
	private List<String> to;


//	/**
//	 * 应用Id
//	 */
//	private String appId;

	/**
	 * 模板Id
	 */
	private SmsTemplateEnum templateEnum;

	/**
	 * 内容数据，用于替换模板中{序号}
	 */
	private List<String> datas;

	@Generated("SparkTools")
	private SmsInfo(Builder builder) {
		this.to = builder.to;
		this.templateEnum = builder.templateEnum;
		this.datas = builder.datas;
	}

	/**
	 * 会在发送短信时，自动设置appId、templateId
	* @author Loren
	* @DateTime 2018年8月10日 上午11:18:31
	*
	* @param to 短信接收端手机号码集合，用英文逗号分开，每批发送的手机号数量不得超过200个
	* @param datas 内容数据，用于替换模板中{序号}
	*
	 */
	public SmsInfo(List<String> to, List<String> datas) {
		super();
		this.to = to;
		this.datas = datas;
	}

	/**
	 * Creates builder to build {@link SmsInfoBean}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link SmsInfoBean}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<String> to = Collections.emptyList();
		private SmsTemplateEnum templateEnum;
		private List<String> datas = Collections.emptyList();

		private Builder() {
		}

		public Builder To(List<String> to) {
			this.to = to;
			return this;
		}

		public Builder TemplateEnum(SmsTemplateEnum templateEnum) {
			this.templateEnum = templateEnum;
			return this;
		}

		public Builder Datas(List<String> datas) {
			this.datas = datas;
			return this;
		}

		public SmsInfo build() {
			return new SmsInfo(this);
		}
	}

}
