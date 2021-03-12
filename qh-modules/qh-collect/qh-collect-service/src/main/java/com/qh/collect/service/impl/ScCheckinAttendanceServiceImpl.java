package com.qh.collect.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.utils.Bases;
import com.qh.common.core.utils.http.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.collect.mapper.ScCheckinAttendanceMapper;
import com.qh.collect.domain.ScCheckinAttendance;
import com.qh.collect.service.IScCheckinAttendanceService;

/**
 * 考勤总每日记录Service业务层处理
 *
 * @author huangdaoquan
 * @date 2020-12-28
 */
@Service
public class ScCheckinAttendanceServiceImpl extends ServiceImpl<ScCheckinAttendanceMapper, ScCheckinAttendance> implements IScCheckinAttendanceService {

    @Autowired
    ScCheckinAttendanceMapper scCheckinAttendanceMapper;

    /**
     * 查询考勤总每日记录集合
     *
     * @param scCheckinAttendance 操作考勤总每日记录对象
     * @return 操作考勤总每日记录集合
     */
    @Override
    public IPage<ScCheckinAttendance> selectScCheckinAttendanceListByPage(IPage<ScCheckinAttendance> page, ScCheckinAttendance scCheckinAttendance) {
        return this.page(page, getQuery(scCheckinAttendance));
    }


    /**
     * 查询考勤总每日记录参数拼接
     */
    private QueryWrapper<ScCheckinAttendance> getQuery(ScCheckinAttendance scCheckinAttendance) {
        QueryWrapper<ScCheckinAttendance> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(scCheckinAttendance.getOrgId()), "org_id", scCheckinAttendance.getOrgId());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("attendance_date");
        return queryWrapper;

    }

    /**
     * 获取指定条件一条考勤记录信息
     * @param accOrStuId
     * @param orgId
     * @param day
     * @param recorderType
     * @return
     */
    @Override
    public ScCheckinAttendance queryAttendanceByDay(String accOrStuId, String orgId,int day,int recorderType) {
        return scCheckinAttendanceMapper.findOneSchoolCheckinAttendanceInMap(accOrStuId,  orgId, day, recorderType);
    }

    /**
     * 保存考勤信息
     * @param orgId
     * @param recorder
     * @param recorderType
     * @param checkTime
     * @return
     */
    @Override
    public boolean saveCheckinAttendance(String orgId, String recorder, int recorderType ,Long checkTime) {
        int day = Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd));

        ScCheckinAttendance newAttendance = new ScCheckinAttendance();
        newAttendance.setAttendanceDate(day);
        newAttendance.setOrgId(orgId);
        newAttendance.setRecorder(recorder);
        // 1：学生 2：教职工
        newAttendance.setRecorderType(recorderType);
        newAttendance.setIntoTime(checkTime);
        // 1：上学上班 5：放学下班
        newAttendance.setStateMark(1);
        return this.save(newAttendance) ;
    }
}