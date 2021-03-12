 package com.qh.basic.service;


 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.qh.basic.domain.ScClass;
 import com.qh.basic.model.request.statistics.RegisterStatisticsReq;
 import com.qh.basic.model.response.RegisterStatisticsResp;
 import com.qh.basic.model.response.StudentStatisticsResp;
 import com.qh.common.core.web.domain.R;

 import java.util.List;

 /**
   * app下载统计(注册数，活跃数，启动数)
   * @Title:
   * @Description:
   * @Copyright:
   * @Company:
   * @author:fangjiatao
   * @version:Neon.3 Release (4.6.3)
   * @Create Date Time: 2020年9月2日 下午2:25:39
   * @Update Date Time: 2020年9月2日 下午2:25:39
   * @see
   */
  public interface IAppStatisticsService {
     /**
      * 获取总注册次数
      * @Title: queryRegisterStatistics
      * @Description: TODO
      * @param req
      * @return AjaxJson
      * @throws
      */
     //public List<RegisterStatisticsResp> queryRegisterStatistics(RegisterStatisticsReq req);

     /**
      * 获取各个学校的注册次数
      * @Title: queryAllSchoolRegisterStatistics
      * @Description: TODO
      * @param req
      * @return AjaxJson
      * @throws
      */
     IPage<RegisterStatisticsResp> queryAllSchoolRegisterStatistics(RegisterStatisticsReq req);

     /**
      * 获取各个班级的注册次数
      * @param objectPage
      * @param req
      * @return
      */
     IPage<RegisterStatisticsResp> queryAllClassRegisterStatistics(IPage<ScClass> objectPage, RegisterStatisticsReq req);

     /**
      * 根据classId获取学生详情列表
      * @param pageStudentStatisticsResp
      * @param registerStatisticsReq
      * @return
      */
     IPage<StudentStatisticsResp> queryStudent(IPage<StudentStatisticsResp> pageStudentStatisticsResp, RegisterStatisticsReq registerStatisticsReq) ;


     /**
      * 根据classId获取学生列表 导出
      * @param registerStatisticsReq
      * @return
      */
     List<StudentStatisticsResp> studentExport(RegisterStatisticsReq registerStatisticsReq) ;

     /**
      * 学生家长账号短信提醒激活
      * @param registerStatisticsReq
      * @return
      */
     R<Boolean> studentSmsActivate(RegisterStatisticsReq registerStatisticsReq) ;
 }
