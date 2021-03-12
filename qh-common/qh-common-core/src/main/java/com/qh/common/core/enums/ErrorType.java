package com.qh.common.core.enums;

/**
 *  返回数据错误类型
 *  @author zgf
 *  @DateTime 2019年4月24日 上午10:47:45
 */
public enum ErrorType {
	
	/**  成功  */
	SUCCESS(0, "成功"),
	/** 获取不到参数或json格式有错误  */
	JSON_FORMAT_ERROR(1, "获取不到参数或json格式有错误"),
	/** 获取不到功能码 */
	FUN_NULL_ERROR(2, "获取不到功能码"),
	/** 功能码错误  */
	FUN_ERROR(3, "功能码错误"),
	/**  json 数据缺少参数  */
	JSON_LACK_PARAM_ERROR(4, "json 数据缺少参数"),
	/**参数为空*/
	USE_NUM_NULL_ERROR(5, "参数[uuid]为空"),
	
	TOKEN_ERROR(6, "token异常,请重新登录"),
	/**参数错误*/
	PARAM_ERROR(7, "参数错误"),

	/**账号在其他设备上登录*/
	LOGIN_OTHER_DEVICES(8,"账号在其他设备上登录"),

	APP_LOGIN_ERROR(9, "请登录后再使用"),
	
	BUSI_VAILI_ERROR(10,"业务逻辑校验错误"),
	
	
	NO_CHILD_ERROR(20, "该帐号还没有绑定相关孩子信息"),
	
	
	NO_CLASS_ERROR(21, "你的账号没有绑定相关的班级信息"),
	
	STU_NO_STATION_NEAR(30, "设置家庭地址成功，附近100米无城市基站，暂无法使用到家提醒功能。"),
	
	MAIN_ACC_EXPIRE_ERROR(40, "您的账号已到期，续费后才能查看{stuName}同学的信息！"),
	
	SUB_ACC_EXPIRE_ERROR(41, "您的账号已到期，续费后才能查看{stuName}同学的信息！请让主账号为您续费！"),
	
	MAIN_ACC_EXPIRE_WARN(42, "您的账号将于{n}天后到期，到期后将无法查看{stuName}同学的信息，请及时充值"),
	
	SUB_ACC_EXPIRE_WARN(43, "您的账号将于{n}天后到期，到期后将无法查看{stuName}同学的信息，请及时通知主账号为您充值"),
	
	APP_NEED_UPDATE(99, "应用需要升级"),

	NO_FILE(102, "OSS文件不存在"),
	

	/**其他错误或系统错误*/
	ERROR(-1, "其他错误或系统错误");
	
	private int codeVal;
    private String codeMsg;

    private ErrorType(int value, String name){
    	codeVal = value;
    	codeMsg = name;
    }

	public int getCodeVal() {
		return codeVal;
	}

	public void setCodeVal(int codeVal) {
		this.codeVal = codeVal;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}


}
