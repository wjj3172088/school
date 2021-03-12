 package com.qh.basic.service;


 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.qh.basic.model.response.AttendanceClassStatisticsInfoResp;
 import com.qh.basic.model.response.AttendanceStaffInfoStatisticsResp;

 import java.util.List;

 /**
   * 考勤管理
   * @Title:
   * @Description:
   * @Copyright:
   * @Company:
   * @author:huangdaoquan
   * @version:Neon.3 Release (4.6.3)
   * @Create Date Time: 2020年9月2日 下午2:25:39
   * @Update Date Time: 2020年9月2日 下午2:25:39
   * @see
   */
  public interface IAttendanceStatisticsService {

     /**
      * 获取当天教职工考勤统计
      * @return
      */
     AttendanceClassStatisticsInfoResp selectStaffInfoStatistics();

     /**
      * 获取当天教职工考勤统计
      * @param objectPage
      * @return
      */
     IPage<AttendanceStaffInfoStatisticsResp> selectStaffInfoDetAttendanceStatistics(IPage<Object> objectPage);

     /**
      * 导出当天教职工考勤
      * @return
      */
     List<AttendanceStaffInfoStatisticsResp> selectStaffInfoDetAttendanceExport();

     /**
      * 根据筛选条件获取教职工考勤统计
      * @param objectPage
      * @param staffInfoName
      * @param jobNumber
      * @param beginTime
      * @param endTime
      * @return
      */
     IPage<AttendanceStaffInfoStatisticsResp> selectStaffInfoByNameStatistics(IPage<Object> objectPage,String staffInfoName,String jobNumber,String beginTime,String endTime);

     /**
      * 导出根据筛选条件获取教职工考勤导出
      * @param staffInfoName
      * @param jobNumber
      * @param beginTime
      * @param endTime
      * @return
      */
     List<AttendanceStaffInfoStatisticsResp> selectStaffInfoByNameExport(String staffInfoName,String jobNumber,String beginTime,String endTime);

     /**
      * 获取当天学生考勤统计
      * @return
      */
     AttendanceClassStatisticsInfoResp selectStudentAttendanceStatistics();

     /**
      * 获取当天学生考勤按班级统计
      * @param objectPage
      * @return
      */
     IPage<AttendanceClassStatisticsInfoResp> selectClassAttendanceStatistics(IPage<Object> objectPage);

     /**
      * 获取当天学生考勤明细统计
      * @param objectPage
      * @param classId
      * @return
      */
     IPage<AttendanceClassStatisticsInfoResp> selectStudentListDetAttendanceStatistics(IPage<Object> objectPage,String classId);

     /**
      * 获取当天学生考勤明细统计 (新)
      * @param objectPage
      * @param classId
      * @return
      */
     IPage<AttendanceClassStatisticsInfoResp> selectStudentListDetAttendanceStatisticsNew(IPage<Object> objectPage,String classId);
 }
