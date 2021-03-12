package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qh.basic.domain.ScClass;
import com.qh.basic.mapper.AppStatisticsMapper;
import com.qh.basic.model.request.statistics.RegisterStatisticsReq;
import com.qh.basic.model.request.scclass.ClassSearchRequest;
import com.qh.basic.model.response.RegisterStatisticsResp;
import com.qh.basic.model.response.StudentStatisticsResp;
import com.qh.basic.service.IAppStatisticsService;
import com.qh.basic.service.IScClassService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.OrgService;
import com.qh.system.api.SmsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangdaoquan
 * @Date: 2020/11/25 13:25
 * @Description: APP注册统计service业务逻辑层
 */
@Service("appStatisticsServiceImpl")
public class AppStatisticsServiceImpl implements IAppStatisticsService {

    @Autowired
    private AppStatisticsMapper appStatisticsMapper;

    @Autowired
    private OrgService orgService;

    @Autowired
    private IScClassService iScClassService;

    @Autowired
    private SmsService smsService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * 各个学校的总注册数 ，今日新增注册数（学校名称，总注册数，今日新增注册数） 筛选条件（学校名称，日期）
     */
    @Override
    public IPage<RegisterStatisticsResp>  queryAllSchoolRegisterStatistics(RegisterStatisticsReq registerStatisticsReq) {
        //读取将当前登录用户绑定的学校Id作为默认学校查询
        registerStatisticsReq.setOrgId(SecurityUtils.getOrgId());
        // 今日开始时间
        String time = DateUtil.getDayBeginString();
        IPage<RegisterStatisticsResp> pageRegisterStatisticsResp =new Page<>();
        List<RegisterStatisticsResp> resList = new ArrayList<RegisterStatisticsResp>();
        try {
            // 获取查询信息
            String startTime = (String) registerStatisticsReq.getBeginTime();
            String endTime = (String) registerStatisticsReq.getEndTime();
            RegisterStatisticsResp registerStatisticsResp = new RegisterStatisticsResp();
            // 默认查询每个学校的注册次数 如果参数都为空
            if (StringUtils.isBlank(registerStatisticsReq.getOrgId())) {
                throw new BizException(CodeEnum.NOT_EXIST, "归属学校Id");
            }
            // 学校总注册数
            long total = appStatisticsMapper.getStudentStateMarkCount(registerStatisticsReq.getOrgId(), "","","");
            registerStatisticsResp.setRegisterNum((int)total);
            //根据入参 开始和结束时间不同拼装startTime和endTime
            if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
                startTime=time;
                endTime="";
                long day = DateUtil.getDayBegin();
                registerStatisticsResp.setTime(DateUtil.getDateToString(day * 1000) + "—" + DateUtil.getDateToString(day * 1000));
            }else if(StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(startTime)){
                registerStatisticsResp.setTime(startTime + "—"+ endTime);
                //加上时分秒
                endTime = endTime + " 23:59";
                //加上时分秒
                startTime = startTime + " 00:00";
            }else {
                throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
            }
            //调用赋值时间区间激活总数方法
            setRegisterStatisticsAddNum(registerStatisticsResp,registerStatisticsReq.getOrgId(), "", startTime, endTime);
            registerStatisticsResp.setOrgName(orgService.selectOrgNameById(registerStatisticsReq.getOrgId()));
            resList.add(registerStatisticsResp);
            pageRegisterStatisticsResp.setRecords(resList);
            pageRegisterStatisticsResp.setTotal(resList.size());
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "统计数据异常，异常原因:"+e.getMessage());
        }
        return pageRegisterStatisticsResp;
    }

    /**
     * @Description: 获取所有班级注册数总和统计方法
     * @Author: huangdaoquan
     * @Date: 2020/11/27 13:42
     *
     * @return
     */
    @Override
    public IPage<RegisterStatisticsResp>  queryAllClassRegisterStatistics(IPage<ScClass> objectPage, RegisterStatisticsReq registerStatisticsReq) {
        //读取将当前登录用户绑定的学校Id作为默认学校查询
        registerStatisticsReq.setOrgId(SecurityUtils.getOrgId());
        IPage<RegisterStatisticsResp> pageRegisterStatisticsResp =new Page<>();
        List<RegisterStatisticsResp> resList = new ArrayList<RegisterStatisticsResp>();
        try {
            String startTime = (String) registerStatisticsReq.getBeginTime();
            String endTime = (String) registerStatisticsReq.getEndTime();
            String startEndTime="";
            // 每日激活新增  时间查询
            if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
                startTime=DateUtil.getDayBeginString();
                endTime="";
                // 今日时间
                long day = DateUtil.getDayBegin();
                startEndTime = DateUtil.getDateToString(day * 1000) + "—" + DateUtil.getDateToString(day * 1000);
            }else if(StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(startTime)){
                startEndTime = startTime + "—"+ endTime;
                //加上时分秒
                endTime = endTime + " 23:59";
                //加上时分秒
                startTime = startTime + " 00:00";
            }else {
                throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
            }
            long total = 0;
            if (StringUtils.isBlank(registerStatisticsReq.getClassId())) {
                // 入参classId为空，获取该学校的所有班级集合
                IPage<ScClass> classList = iScClassService.selectListByPage(objectPage,new ClassSearchRequest());
                for (ScClass classGrade : classList.getRecords()) {
                    RegisterStatisticsResp registerStatisticsResp = new RegisterStatisticsResp();
                    total = appStatisticsMapper.getStudentStateMarkCount(registerStatisticsReq.getOrgId(), classGrade.getClassId(),"","");
                    registerStatisticsResp.setRegisterNum((int)total);
                    //赋值时间区间激活总数
                    setRegisterStatisticsAddNum(registerStatisticsResp,registerStatisticsReq.getOrgId(), classGrade.getClassId(), startTime, endTime);
                    registerStatisticsResp.setTime(startEndTime);
                    registerStatisticsResp.setOrgName(orgService.selectOrgNameById(registerStatisticsReq.getOrgId()));
                    registerStatisticsResp.setClassName(classGrade.getGradeName() + classGrade.getClassNum());
                    resList.add(registerStatisticsResp);
                }
                pageRegisterStatisticsResp.setTotal(classList.getTotal());
            } else {
                //classId不为空，根据classId获取该班的详情
                RegisterStatisticsResp registerStatisticsResp = new RegisterStatisticsResp();
                ScClass dbScClass = iScClassService.getById(registerStatisticsReq.getClassId());
                if(dbScClass==null||StringUtils.isBlank(dbScClass.getClassId())){
                    throw new BizException(CodeEnum.NOT_EXIST, "班级信息");
                }
                total = appStatisticsMapper.getStudentStateMarkCount(registerStatisticsReq.getOrgId(), dbScClass.getClassId(),"","");
                registerStatisticsResp.setRegisterNum((int)total);
                //赋值时间区间激活总数
                setRegisterStatisticsAddNum(registerStatisticsResp,registerStatisticsReq.getOrgId(), dbScClass.getClassId(),startTime,endTime);
                registerStatisticsResp.setTime(startEndTime);
                registerStatisticsResp.setOrgName(orgService.selectOrgNameById(registerStatisticsReq.getOrgId()));
                registerStatisticsResp.setClassName(registerStatisticsReq.getClassName());
                resList.add(registerStatisticsResp);
                pageRegisterStatisticsResp.setTotal(1);
            }
            pageRegisterStatisticsResp.setRecords(resList);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "统计数据异常，异常原因:"+e.getMessage());
        }
        return pageRegisterStatisticsResp;
    }

    /**
     * @Description: 公共方法 日区间统计区间新增激活数
     * @Author: huangdaoquan
     * @Date: 2020/12/9 9:15
     */
    private void setRegisterStatisticsAddNum(RegisterStatisticsResp registerStatisticsResp,String orgId
            ,String classId,String startTime,String endTime){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long day = DateUtil.getDayBegin();
            long total = appStatisticsMapper.getStudentStateMarkCount(orgId, classId,startTime,endTime);
            registerStatisticsResp.setAddNum((int)total);
            registerStatisticsResp.setClassId(classId);
            registerStatisticsResp.setOrgId(orgId);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
    }

    /**
     * 获取注册学生信息
     */
    @Override
    public IPage<StudentStatisticsResp> queryStudent(IPage<StudentStatisticsResp> pageStudentStatisticsResp, RegisterStatisticsReq registerStatisticsReq) {
        IPage<StudentStatisticsResp> resList = new Page<StudentStatisticsResp>();
        try {
            //获取查询信息
            registerStatisticsReq.setOrgId(SecurityUtils.getOrgId());
            resList = appStatisticsMapper.selectListByPage(pageStudentStatisticsResp,registerStatisticsReq);
            return resList;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    /**
     * 导出学生详情
     * @param registerStatisticsReq
     * @return
     */
    @Override
    public List<StudentStatisticsResp> studentExport(RegisterStatisticsReq registerStatisticsReq){
        List<StudentStatisticsResp> resList = null;
        try {
            //获取查询信息
            registerStatisticsReq.setOrgId(SecurityUtils.getOrgId());
            resList = appStatisticsMapper.selectListByPage(registerStatisticsReq);
            return resList;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }

    }


    /**
     * 学生家长账号短信提醒激活
     */
    @Override
    public R<Boolean> studentSmsActivate( RegisterStatisticsReq registerStatisticsReq) {
        try {
            //获取查询信息
            registerStatisticsReq.setOrgId(SecurityUtils.getOrgId());
            //查询条件为未激活
            registerStatisticsReq.setActivateState(0);
            //如果不是prd环境，固定给学生方诗涵家长huangdaoquan手机号发送
            if(StringUtils.isNotBlank(activeProfile) && !"prd".equals(activeProfile)){
                registerStatisticsReq.setTagNum("24021B93");
            }
            List<StudentStatisticsResp> resList = appStatisticsMapper.selectListByPage(registerStatisticsReq);
            if(resList==null){
                return R.fail(false,"该班级已没有未激活的家长，请核对后重试");
            }
            Integer successCount = 0;
            Integer failCount = 0;
            //String mobiles="";
            for (StudentStatisticsResp studentStatisticsResp : resList){
                //mobiles += studentStatisticsResp.getGuardianMobile()+",";
                //一个号码调用一次发送短信功能
                R<Boolean> booleanSms= smsService.smsSendMsg(studentStatisticsResp.getAccNum(),"","家长账号激活引导");
                if(booleanSms!=null && "200".equals(booleanSms.getCode()) && booleanSms.getData()){
                    successCount++;
                }else{
                    failCount++;
                }
            }
            //R<SmsResponseResult> smsResponseResult = smsService.smsSendMsg(mobiles,"","家长账号激活引导");//拼接号码后，统一发送
            return R.ok(true,"批量发送短信成功，发送成功笔数："+successCount.toString()+"; 发送失败笔数："+failCount.toString()+";");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return R.fail("发送短信出现未知异常，请联系管理员");
        }
    }

}
