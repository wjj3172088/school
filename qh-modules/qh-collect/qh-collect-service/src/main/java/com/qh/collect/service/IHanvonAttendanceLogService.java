package com.qh.collect.service;

import com.qh.collect.domain.HanvonAttendanceLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 汉王考勤记录Service接口
 *
 * @author huangdaoquan
 * @date 2020-12-25
 */
public interface IHanvonAttendanceLogService extends IService<HanvonAttendanceLog> {


    /**
     * 查询汉王考勤记录集合
     * @param page 分页对象
     * @param hanvonAttendanceLog 操作汉王考勤记录对象
     * @return 操作汉王考勤记录集合
     */
    IPage<HanvonAttendanceLog> selectHanvonAttendanceLogListByPage(IPage<HanvonAttendanceLog> page, HanvonAttendanceLog hanvonAttendanceLog);

    /**
     * 查询汉王考勤记录集合
     *
     * @param hanvonAttendanceLog 操作汉王考勤记录对象
     * @return 操作汉王考勤记录集合
     */
    List<HanvonAttendanceLog> exportHanvonAttendanceLog(HanvonAttendanceLog hanvonAttendanceLog);

}
