package com.qh.collect.service;

import com.qh.collect.domain.ScCheckinAttendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 考勤总每日记录Service接口
 *
 * @author huangdaoquan
 * @date 2020-12-28
 */
public interface IScCheckinAttendanceService extends IService<ScCheckinAttendance> {


    /**
     * 查询考勤总每日记录集合
     * @param page 分页对象
     * @param scCheckinAttendance 操作考勤总每日记录对象
     * @return 操作考勤总每日记录集合
     */
    IPage<ScCheckinAttendance> selectScCheckinAttendanceListByPage(IPage<ScCheckinAttendance> page, ScCheckinAttendance scCheckinAttendance);

    /**
     * 获取指定条件下一条考勤记录信息
     * @param accOrStuId
     * @param orgId
     * @param day
     * @param recorderType
     * @return
     */
    ScCheckinAttendance queryAttendanceByDay(String accOrStuId, String orgId,int day,int recorderType);

    /**
     * 保存考勤信息
     * @param orgId
     * @param recorder
     * @param recorderType
     * @param checkTime
     * @return
     */
    boolean saveCheckinAttendance(String orgId, String recorder, int recorderType ,Long checkTime);
}
