package com.qh.common.core.enums;

/**
 * 业务类型，直接使用枚举的名称作为值，字符不能大于20个字符，否则存入数据库会出现问题
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2018年6月22日 下午6:14:09
 */
public enum BizType implements StrValueEnum{

	/**
	 * 学生
	 */
	student(),
	
	/**
	 * 人脸
	 */
	face(),
	
	/**
	 * 学生、老师、职工（人脸）
	 */
	photo(),
	
	/**
	 * 移动应用
	 */
	apply(),
	
	
	/**
	 * 用户头像
	 */
	accIcon(),
	
	/**
	 * 学生头像
	 */
	stuIcon(),
	
	/**
	 * 访客
	 */
	visitor(),
	
	/**
	 * 过车
	 */
	vehCar(),
	
	/**
	 * 临时，用于临时文件的上传目录，例如：导入文件存储路径之类的
	 */
	temp(),

	/**
	 * 作业
	 */
	schoolwork(),

	/**
	 * 公告
	 */
	notice(),

	/**
	 * 课表
	 */
	curriculum(),
    
    /**
     * 
     *客服
     *
     */
    customer(),
    /**
     * 安全宣传
     */
    publicity();

	private BizType() {
	}

	public String value() {
		return this.name();
	}
	
}
