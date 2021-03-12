
package com.qh.system.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一返回标识结果
 * @project SmartSafeCampusService
 * @package com.qinghai.service.plug.sms
 * @filename SmsResponseBean.java
 * @createtime 2020年8月3日上午9:41:54  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
@Data
@AllArgsConstructor
public class SmsResponseResult {

//	public SmsResponseResult(boolean isSuccess,String statusMsg){
//		this.isSuccess=isSuccess;
//		this.statusMsg=statusMsg;
//	}
	/**
	 * 标识
	 */
	private boolean isSuccess;
	
	/**
	 * 信息
	 */
	private String statusMsg;
}
