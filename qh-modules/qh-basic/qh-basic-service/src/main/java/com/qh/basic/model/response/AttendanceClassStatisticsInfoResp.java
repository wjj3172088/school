
package com.qh.basic.model.response;

import lombok.*;

import java.io.Serializable;

/**
 * @project SmartSafeCampusLib
 * @package com.qinghai.lib.dto.beans.result.office
 * @filename AttendanceStudentInfoBean.java
 * @createtime 2020-9-1815:02:44  
 * @author  fcj2593@163.com
 * @todo   TODO
 *   班级统计 考勤
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceClassStatisticsInfoResp implements Serializable  {

	/** 
	* @Fields 	:serialVersionUID : TODO
	*/
	private static final long serialVersionUID = -2985705404911236804L;
	/**
	 * 年级班级
	 */
	private String gradeClass;
	/**
	 * 班主任
	 */
	private String masterTeacher;
	/**
	 * 外面
	 */
	private int outside;
	/**
	 * 里面
	 */
	private int inside;
	/**
	 * 请假
	 */
	private int askLeave;
	/**
	 * 班级中学生总数
	 */
	private int statisAll;
	
	/**
	 * 体温异常学生数量
	 */
	private int abnormalTemperature;

	private String classId;

	private String stuId;

	private String stuName;

	private String guardianMobile;
}
