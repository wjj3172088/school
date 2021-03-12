 package com.qh.basic.controller;

 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 import com.qh.basic.model.response.AttendanceClassStatisticsInfoResp;
 import com.qh.basic.model.response.AttendanceStaffInfoStatisticsResp;
 import com.qh.basic.service.IAttendanceStatisticsService;
 import com.qh.common.core.utils.StringUtils;
 import com.qh.common.core.utils.poi.ExcelUtil;
 import com.qh.common.core.web.controller.BaseController;
 import com.qh.common.core.web.domain.AjaxResult;
 import com.qh.common.core.web.domain.R;
 import com.qh.common.log.annotation.Log;
 import com.qh.common.log.enums.BusinessType;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;

 /**
   * 考勤管理
   * @Title:
   * @Description:
   * @Copyright:
   * @Company:
   * @author:huangdaoquan
   * @version:Neon.3 Release (4.6.3)
   * @Create Date Time: 2020年9月2日 下午2:13:37
   * @Update Date Time: 2020年9月2日 下午2:13:37
   * @see
   */

  @RestController
  @RequestMapping("/attendanceStatistics")
  public class AttendanceStatisticsController extends BaseController {

      @Autowired
      IAttendanceStatisticsService attendanceStatisticsService;

     /**
      * 获取当天全部教职工考勤总览统计
      * @return
      */
      @GetMapping("/getStaffInfoPandect")
      public AjaxResult getStaffInfoPandect() {
          return AjaxResult.success(attendanceStatisticsService.selectStaffInfoStatistics());
      }

     /**
      * 获取教职工考勤列表统计
      * @return
      */
     @PreAuthorize("@ss.hasPermi('basic:teacherstatistics:list')")
     @GetMapping("/getStaffInfoList")
     public  R<IPage<AttendanceStaffInfoStatisticsResp>>  getStaffInfoDetAttendance(String staffInfoName,String jobNumber,String beginTime,String endTime) {
      IPage<AttendanceStaffInfoStatisticsResp> list=new Page<>();
      if((StringUtils.isNotBlank(staffInfoName) || StringUtils.isNotBlank(jobNumber))
              && StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)){
         //根据筛选条件获取教职工考勤统计
         list = attendanceStatisticsService.selectStaffInfoByNameStatistics(getPage(), staffInfoName, jobNumber, beginTime, endTime);
      }else if(StringUtils.isBlank(staffInfoName) && StringUtils.isBlank(jobNumber)
              && StringUtils.isBlank(beginTime) && StringUtils.isBlank(endTime)) {
         //获取当天教职工考勤统计
         list = attendanceStatisticsService.selectStaffInfoDetAttendanceStatistics(getPage());
      }else{
        return R.fail("对不起，考勤历史数据量过大，请结合教职工姓名+开始时间+结束时间组合使用筛选");
      }
      return R.ok(list);
     }


      /**
       * 导出教师考勤信息列表
       */
      @Log(title = "考勤管理", businessType = BusinessType.EXPORT)
      @PreAuthorize("@ss.hasPermi('basic:attendanceStatistics:staffInfoExport')")
      @PostMapping("/staffInfoExport")
      public void export(HttpServletResponse response, String staffInfoName,String jobNumber,String beginTime,String endTime) throws IOException {
       List<AttendanceStaffInfoStatisticsResp>  listAttendance = new ArrayList<>();
       if((StringUtils.isNotBlank(staffInfoName) || StringUtils.isNotBlank(jobNumber))
               && StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)){
         //根据筛选条件获取教职工考勤统计
         listAttendance = attendanceStatisticsService.selectStaffInfoByNameExport(staffInfoName, jobNumber, beginTime, endTime);
       }else if(StringUtils.isBlank(staffInfoName) && StringUtils.isBlank(jobNumber)
               && StringUtils.isBlank(beginTime) && StringUtils.isBlank(endTime)) {
         //获取当天教职工考勤统计
         listAttendance = attendanceStatisticsService.selectStaffInfoDetAttendanceExport();
       }
       ExcelUtil<AttendanceStaffInfoStatisticsResp> util = new ExcelUtil<AttendanceStaffInfoStatisticsResp>(AttendanceStaffInfoStatisticsResp.class);
       util.exportExcel(response, listAttendance, "教职工考勤信息导出");
      }

      /** 从这里开始为教师考勤统计
       * 获取当天全部学生考勤总览统计
       * @return
       */
      @GetMapping("/getStudentPandect")
      public AjaxResult getStudentPandect() {
       return AjaxResult.success(attendanceStatisticsService.selectStudentAttendanceStatistics());
      }

      /**
       * 获取当天班级考勤总览统计
       * @return
       */
      @PreAuthorize("@ss.hasPermi('basic:attendancestatistics:getAllStudent')")
      @GetMapping("/getClassPandect")
      public  R<IPage<AttendanceClassStatisticsInfoResp>>  getClassPandect() {
       IPage<AttendanceClassStatisticsInfoResp> list =  attendanceStatisticsService.selectClassAttendanceStatistics(getPage());
       return R.ok(list);
      }

      /**
       * 获取当天学生详情列表统计
       * @return
       */
      @PreAuthorize("@ss.hasPermi('basic:attendancestatistics:getStudentList')")
      @GetMapping("/getStudentList")
      public  R<IPage<AttendanceClassStatisticsInfoResp>>  getStudentList(String classId) {
       //IPage<AttendanceClassStatisticsInfoResp> list =  attendanceStatisticsService.selectStudentListDetAttendanceStatistics(getPage(),classId);
       IPage<AttendanceClassStatisticsInfoResp> list =  attendanceStatisticsService.selectStudentListDetAttendanceStatisticsNew(getPage(),classId);
       return R.ok(list);
      }





 }
