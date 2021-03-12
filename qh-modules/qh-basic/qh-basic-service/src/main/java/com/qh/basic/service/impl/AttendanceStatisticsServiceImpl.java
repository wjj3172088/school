package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qh.basic.api.TeacherService;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.domain.ScMoveAcc;
import com.qh.basic.enums.AccTypeEnum;
import com.qh.basic.mapper.AttendanceStatisticsMapper;
import com.qh.basic.mapper.ScMoveAccMapper;
import com.qh.basic.mapper.ScStaffInfoMapper;
import com.qh.basic.mapper.ScTeacherMapper;
import com.qh.basic.model.response.AttendanceClassStatisticsInfoResp;
import com.qh.basic.model.response.AttendanceStaffInfoStatisticsResp;
import com.qh.basic.service.IAttendanceStatisticsService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * app下载数统计(注册数，启动数，活跃数) *
 * @Title:
 * @Description:
 * @Copyright:
 * @Company:
 * @author:fangjiatao
 * @version:Neon.3 Release (4.6.3)
 * @Create Date Time: 2020年9月2日 下午2:26:30
 * @Update Date Time: 2020年9月2日 下午2:26:30
 * @see
 */
@Service("attendanceStatisticsServiceImpl")
public class AttendanceStatisticsServiceImpl implements IAttendanceStatisticsService {

    @Autowired
    private AttendanceStatisticsMapper attendanceStatisticsMapper;

    @Autowired
    private ScTeacherMapper teacherMapper;

    @Autowired
    private ScStaffInfoMapper staffInfoMapper;

    @Autowired
    private ScMoveAccMapper moveAccMapper;

    /**
     * 获取当天教职工考勤统计
     * @return
     */
    @Override
    public AttendanceClassStatisticsInfoResp selectStaffInfoStatistics() {
        AttendanceClassStatisticsInfoResp resp = attendanceStatisticsMapper.selectStaffInfoStatistics(SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        //校外人数=全校人数-在校人数
        resp.setOutside(resp.getStatisAll() - resp.getInside());
        return resp;
    }

    /**
     * 获取当天教职工考勤统计
     * @param objectPage
     * @return
     */
    @Override
    public IPage<AttendanceStaffInfoStatisticsResp> selectStaffInfoDetAttendanceStatistics(IPage<Object> objectPage) {
        IPage<AttendanceStaffInfoStatisticsResp> resp = attendanceStatisticsMapper.selectStaffInfoDetAttendanceStatistics( objectPage,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        return resp;
    }

    /**
     * 导出当天教职工考勤
     * @return
     */
    @Override
    public List<AttendanceStaffInfoStatisticsResp> selectStaffInfoDetAttendanceExport() {
        List<AttendanceStaffInfoStatisticsResp> resp = attendanceStatisticsMapper.selectStaffInfoDetAttendanceStatistics(SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        return resp;
    }


    /**
     * 根据筛选条件获取教职工考勤统计
     * @param objectPage
     * @param staffInfoName
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public IPage<AttendanceStaffInfoStatisticsResp> selectStaffInfoByNameStatistics(IPage<Object> objectPage,String staffInfoName,String jobNumber
            ,String beginTime,String endTime) {
        IPage<AttendanceStaffInfoStatisticsResp> resp = new Page<>();
        ScTeacher scTeacher = teacherMapper.selectByTeacName(SecurityUtils.getOrgId()
                ,staffInfoName,jobNumber);
        ScStaffInfo staffInfo = staffInfoMapper.selectByStaffName(SecurityUtils.getOrgId()
                ,staffInfoName,jobNumber);
        String tableName="";
        String accId="";
        if(null != scTeacher && StringUtils.isNotBlank(scTeacher.getAccId())){
            accId = scTeacher.getAccId();
            tableName="t_sc_teacher";
            //判断入职时间是否大于筛选起始时间，如果条件成立将起始时间设为入职时间
            if(null != scTeacher.getCreateDate()
                    && scTeacher.getCreateDate().getTime()/ 1000 > DateUtils.date2TimeStamp(beginTime,DateUtils.yyyyMMdd)){
                beginTime=new SimpleDateFormat("yyyyMMdd").format(scTeacher.getCreateDate());
            }
        }else if(staffInfo!=null && StringUtils.isNotBlank(staffInfo.getAccId())) {
            accId = staffInfo.getAccId();
            tableName="t_sc_staff_info";
            //判断入职时间是否大于筛选起始时间，如果条件成立将起始时间设为入职时间
            if(null != staffInfo.getCreateDate()
                    && staffInfo.getCreateDate().getTime()/ 1000 > DateUtils.date2TimeStamp(beginTime,DateUtils.yyyyMMdd)){
                beginTime=new SimpleDateFormat("yyyyMMdd").format(staffInfo.getCreateDate());
            }
        }else{
            return resp;
        }
        resp = attendanceStatisticsMapper.selectStaffInfoByNameStatistics( tableName,objectPage,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)),
                Integer.parseInt(beginTime),Integer.parseInt(endTime),accId);
        return resp;
    }

    /**
     * 导出根据筛选条件获取教职工考勤导出
     * @param staffInfoName
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<AttendanceStaffInfoStatisticsResp> selectStaffInfoByNameExport(String staffInfoName,String jobNumber,String beginTime,String endTime) {
        List<AttendanceStaffInfoStatisticsResp> resp = null;
        ScTeacher scTeacher = teacherMapper.selectByTeacName(SecurityUtils.getOrgId()
                ,staffInfoName,jobNumber);
        ScStaffInfo staffInfo = staffInfoMapper.selectByStaffName(SecurityUtils.getOrgId()
                ,staffInfoName,jobNumber);
        String tableName="";
        String accId="";
        if(null != scTeacher && StringUtils.isNotBlank(scTeacher.getAccId())){
            accId = scTeacher.getAccId();
            tableName="t_sc_teacher";
            //判断入职时间是否大于筛选起始时间，如果条件成立将起始时间设为入职时间
            if(null != scTeacher.getCreateDate()
                    && scTeacher.getCreateDate().getTime()/ 1000 > DateUtils.date2TimeStamp(beginTime,DateUtils.yyyyMMdd)){
                beginTime=new SimpleDateFormat("yyyyMMdd").format(scTeacher.getCreateDate());
            }
        }else if(staffInfo!=null && StringUtils.isNotBlank(staffInfo.getAccId())) {
            accId = staffInfo.getAccId();
            tableName="t_sc_staff_info";
            //判断入职时间是否大于筛选起始时间，如果条件成立将起始时间设为入职时间
            if(null != staffInfo.getCreateDate()
                    && staffInfo.getCreateDate().getTime()/ 1000 > DateUtils.date2TimeStamp(beginTime,DateUtils.yyyyMMdd)){
                beginTime=new SimpleDateFormat("yyyyMMdd").format(staffInfo.getCreateDate());
            }
        }else{
            return resp;
        }
        resp = attendanceStatisticsMapper.selectStaffInfoByNameStatistics(tableName,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)),
                Integer.parseInt(beginTime),Integer.parseInt(endTime),accId);
        return resp;
    }

    /**
     * 获取当天学生考勤统计
     * @return
     */
    @Override
    public AttendanceClassStatisticsInfoResp selectStudentAttendanceStatistics() {
        AttendanceClassStatisticsInfoResp resp = attendanceStatisticsMapper.selectStudentAttendanceStatistics(SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        return resp;
    }

    /**
     * 获取当天学生考勤按班级统计
     * @param objectPage
     * @return
     */
    @Override
    public IPage<AttendanceClassStatisticsInfoResp> selectClassAttendanceStatistics(IPage<Object> objectPage) {
        IPage<AttendanceClassStatisticsInfoResp> resp = attendanceStatisticsMapper.selectClassAttendanceStatistics(objectPage,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        return resp;
    }

    /**
     * 获取当天学生考勤明细统计
     * @param objectPage
     * @param classId
     * @return
     */
    @Override
    public IPage<AttendanceClassStatisticsInfoResp> selectStudentListDetAttendanceStatistics(IPage<Object> objectPage,String classId) {
        IPage<AttendanceClassStatisticsInfoResp> resp = attendanceStatisticsMapper.selectStudentListDetAttendanceStatistics(objectPage,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)),classId);
        return resp;
    }


    /**
     * 获取当天学生考勤明细统计
     * @param objectPage
     * @param classId
     * @return
     */
    @Override
    public IPage<AttendanceClassStatisticsInfoResp> selectStudentListDetAttendanceStatisticsNew(IPage<Object> objectPage,String classId) {
        IPage<AttendanceClassStatisticsInfoResp> resp = attendanceStatisticsMapper.selectStudentListDetAttendanceStatisticsNew(objectPage,SecurityUtils.getOrgId(),
                Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)),classId,Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd))+1);
        return resp;
    }

}
