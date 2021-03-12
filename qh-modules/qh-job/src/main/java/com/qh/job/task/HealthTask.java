package com.qh.job.task;

import com.qh.basic.api.StaffService;
import com.qh.basic.api.TeacherService;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.common.core.utils.StringUtils;
import com.qh.job.service.IHealthService;
import com.qh.job.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 疫情日报每日生成记录任务
 * 
 * @author huangdaoquan
 */
@Component("healthTask")
public class HealthTask
{
    @Autowired
    ISysJobService sysJobService;

    @Autowired
    private IHealthService healthService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StaffService staffService;

    /**
     * 批量同步教师健康码状态
     */
    public void batchSyncTeacherHealth()
    {
        List<TeacherExportVo>  teacherList =  teacherService.findAllData(new TeacherExportSearchRequest());
        for (TeacherExportVo teacher : teacherList) {
            if(StringUtils.isBlank(teacher.getIdCard()) || StringUtils.isBlank(teacher.getTeacId()))
            {
                continue;
            }
            String  healthState = healthService.getHttpHealthState(teacher.getIdCard());
            Integer count = teacherService.syncTeacherHealthState(teacher.getTeacId(), Integer.valueOf(healthState));
            System.out.print("teacId:"+teacher.getTeacId()+";healthState;执行状态：count："+count.toString());
        }
    }


    /**
     * 批量同步职工健康码状态
     */
    public void batchSyncStaffHealth()
    {
        List<Map>  staffList =  staffService.findAllData(new StaffInfoSearchRequest());
        for (Map staff : staffList) {
            if(null==staff || null==staff.get("idCard")||StringUtils.isBlank(staff.get("idCard").toString())
            ||null==staff.get("staffId")|| StringUtils.isBlank(staff.get("staffId").toString())) {
                continue;
            }
            String  healthState = healthService.getHttpHealthState(staff.get("idCard").toString());
            Integer count = staffService.syncStaffHealthState(staff.get("staffId").toString(), Integer.valueOf(healthState));
            System.out.print("staffId:"+staff.get("staffId").toString()+";healthState;执行状态：count："+count.toString());
        }
    }
}
