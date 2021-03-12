package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.model.response.AttendanceClassStatisticsInfoResp;
import com.qh.basic.model.response.AttendanceStaffInfoStatisticsResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教职工考勤统计、学生考勤统计
 * @author huangdaoquan
 */
public interface AttendanceStatisticsMapper {

	/**
	 * 教职工考勤统计总览
	 * @param orgId
	 * @param date
	 * @return
	 */
	AttendanceClassStatisticsInfoResp selectStaffInfoStatistics(@Param("orgId") String orgId, @Param("date") int date);

	/**
	 * 教职工明细当日考勤统计列表
	 * @param objectPage
	 * @param orgId
	 * @param date
	 * @return
	 */
	IPage<AttendanceStaffInfoStatisticsResp>  selectStaffInfoDetAttendanceStatistics(IPage<Object> objectPage, @Param("orgId") String orgId, @Param("date") int date);


	/**
	 * 导出教职工明细当日考勤
	 * @param orgId
	 * @param date
	 * @return
	 */
	List<AttendanceStaffInfoStatisticsResp> selectStaffInfoDetAttendanceStatistics(@Param("orgId") String orgId, @Param("date") int date);

	/**
	 * 教职工明细当日考勤统计每日列表
	 * @param tableName
	 * @param objectPage
	 * @param orgId
	 * @param date
	 * @param beginTime
	 * @param endTime
	 * @param accId
	 * @return
	 */
	IPage<AttendanceStaffInfoStatisticsResp>  selectStaffInfoByNameStatistics(@Param("tableName") String tableName,IPage<Object> objectPage, @Param("orgId") String orgId, @Param("date") int date
			, @Param("beginTime") int beginTime, @Param("endTime") int endTime, @Param("accId") String accId);

	/**
	 * 导出教职工明细当日考勤统计每日
	 * @param tableName
	 * @param orgId
	 * @param date
	 * @param beginTime
	 * @param endTime
	 * @param accId
	 * @return
	 */
	List<AttendanceStaffInfoStatisticsResp>  selectStaffInfoByNameStatistics(@Param("tableName") String tableName, @Param("orgId") String orgId, @Param("date") int date
			, @Param("beginTime") int beginTime, @Param("endTime") int endTime, @Param("accId") String accId);

	/**
	 * 学生考勤统计总览
	 * @param orgId
	 * @param date
	 * @return
	 */
	AttendanceClassStatisticsInfoResp selectStudentAttendanceStatistics(@Param("orgId") String orgId, @Param("date") int date);

	/**
	 * 学生考勤按班级统计
	 * @param objectPage
	 * @param orgId
	 * @param date
	 * @return
	 */
	IPage<AttendanceClassStatisticsInfoResp>  selectClassAttendanceStatistics(IPage<Object> objectPage,@Param("orgId") String orgId, @Param("date") int date);

	/**
	 * 学生考勤按明细统计
	 * @param objectPage
	 * @param orgId
	 * @param date
	 * @param classId
	 * @return
	 */
	IPage<AttendanceClassStatisticsInfoResp>  selectStudentListDetAttendanceStatistics(IPage<Object> objectPage,@Param("orgId") String orgId, @Param("date") int date,@Param("classId") String classId);

	/**
	 * 学生考勤按明细统计
	 * @param objectPage
	 * @param orgId
	 * @param date
	 * @param classId
	 * @param dateEnd
	 * @return
	 */
	IPage<AttendanceClassStatisticsInfoResp>  selectStudentListDetAttendanceStatisticsNew(IPage<Object> objectPage,@Param("orgId") String orgId
			, @Param("date") int date,@Param("classId") String classId, @Param("dateEnd") int dateEnd);
}
