 package com.qh.basic.controller;

 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.qh.basic.model.request.statistics.RegisterStatisticsReq;
 import com.qh.basic.model.response.RegisterStatisticsResp;
 import com.qh.basic.model.response.StudentStatisticsResp;
 import com.qh.basic.service.IAppStatisticsService;
 import com.qh.common.core.utils.poi.ExcelUtil;
 import com.qh.common.core.web.controller.BaseController;
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
 import java.util.List;

 /**
 * @Description: APP激活数统计Controller
 * @Author: huangdaoquan
 * @Date: 2020/11/25 14:28
 */
  @RestController
  @RequestMapping("/appStatistics")
  public class AppStatisticsController extends BaseController {

      @Autowired
      IAppStatisticsService appStatisticsService;

      @GetMapping("/orgStatistics")
      public R<IPage<RegisterStatisticsResp>> registerStatistics(RegisterStatisticsReq registerStatisticsReq) {
          IPage<RegisterStatisticsResp> list = appStatisticsService.queryAllSchoolRegisterStatistics(registerStatisticsReq);
          return R.ok(list);
      }


     @GetMapping("/classStatistics")
      public R<IPage<RegisterStatisticsResp>> registerClassStatistics(RegisterStatisticsReq registerStatisticsReq) {
          IPage<RegisterStatisticsResp> list = appStatisticsService.queryAllClassRegisterStatistics(getPage(),registerStatisticsReq);
          return R.ok(list);
      }

     /**
      * 获取班级学生明细统计
      */
     @GetMapping("/studentStatistics")
     public R<IPage<StudentStatisticsResp>> getStudentByClassId(RegisterStatisticsReq registerStatisticsReq) {
         IPage<StudentStatisticsResp> list = appStatisticsService.queryStudent(getPage(),registerStatisticsReq);
         return R.ok(list);
     }

    /**
     * 导出班级学生明细统计
     */
    @PostMapping("/studentExport")
    @Log(title = "学生考勤管理", businessType = BusinessType.EXPORT)
    public void studentExport(HttpServletResponse response, RegisterStatisticsReq registerStatisticsReq) throws IOException {
        List<StudentStatisticsResp> listStudentStatisticsResp = appStatisticsService.studentExport(registerStatisticsReq);
        ExcelUtil<StudentStatisticsResp> util = new ExcelUtil<StudentStatisticsResp>(StudentStatisticsResp.class);
        util.exportExcel(response, listStudentStatisticsResp, "学生考勤信息导出");
    }

     /**
      * 学生家长账号短信提醒激活
      */
     @Log(title = "一键发送短信激活" , businessType = BusinessType.SMSLOG)
     @PreAuthorize("@ss.hasPermi('basic:appstatistics:studentSmsActivate')")
     @GetMapping("/studentSmsActivate")
     public R<Boolean> studentSmsActivate(RegisterStatisticsReq registerStatisticsReq) {
         return appStatisticsService.studentSmsActivate(registerStatisticsReq);
     }


 }
