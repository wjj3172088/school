package com.qh.collect.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.collect.mapper.HanvonAttendanceLogMapper;
import com.qh.collect.domain.HanvonAttendanceLog;
import com.qh.collect.service.IHanvonAttendanceLogService;

import java.text.ParseException;
import java.util.List;

/**
 * 汉王考勤记录Service业务层处理
 *
 * @author huangdaoquan
 * @date 2020-12-25
 */
@Service
public class HanvonAttendanceLogServiceImpl extends ServiceImpl<HanvonAttendanceLogMapper, HanvonAttendanceLog> implements IHanvonAttendanceLogService {

    /**
     * 查询汉王考勤记录集合
     *
     * @param hanvonAttendanceLog 操作汉王考勤记录对象
     * @return 操作汉王考勤记录集合
     */
    @Override
    public IPage<HanvonAttendanceLog> selectHanvonAttendanceLogListByPage(IPage<HanvonAttendanceLog> page, HanvonAttendanceLog hanvonAttendanceLog) {
        hanvonAttendanceLog.setOrgId(SecurityUtils.getOrgId());
        try {
            //打卡时间筛选入参 时间转换为时间戳
            if(StringUtils.isNotBlank(hanvonAttendanceLog.getBeginTime()) ) {
                hanvonAttendanceLog.setBeginTime(DateUtil.dateToStamp(hanvonAttendanceLog.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(hanvonAttendanceLog.getEndTime()) ) {
                hanvonAttendanceLog.setEndTime(DateUtil.dateToStamp(hanvonAttendanceLog.getEndTime()+" 23:59:59"));
            }
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
        return this.page(page, getQuery(hanvonAttendanceLog));
    }


    /**
     * 查询汉王考勤记录集合
     *
     * @param hanvonAttendanceLog 操作汉王考勤记录对象
     * @return 操作汉王考勤记录集合
     */
    @Override
    public List<HanvonAttendanceLog> exportHanvonAttendanceLog(HanvonAttendanceLog hanvonAttendanceLog) {
        hanvonAttendanceLog.setOrgId(SecurityUtils.getOrgId());
        try {
            //打卡时间筛选入参 时间转换为时间戳
            if(StringUtils.isNotBlank(hanvonAttendanceLog.getBeginTime()) ) {
                hanvonAttendanceLog.setBeginTime(DateUtil.dateToStamp(hanvonAttendanceLog.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(hanvonAttendanceLog.getEndTime()) ) {
                hanvonAttendanceLog.setEndTime(DateUtil.dateToStamp(hanvonAttendanceLog.getEndTime()+" 23:59:59"));
            }
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
        return this.list(getQuery(hanvonAttendanceLog));
    }


    /**
     * 查询汉王考勤记录参数拼接
     */
    private QueryWrapper<HanvonAttendanceLog> getQuery(HanvonAttendanceLog hanvonAttendanceLog) {
        QueryWrapper<HanvonAttendanceLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", hanvonAttendanceLog.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(hanvonAttendanceLog.getPeopleNumber()), "people_number", hanvonAttendanceLog.getPeopleNumber());
        queryWrapper.like(StringUtils.isNotBlank(hanvonAttendanceLog.getPeopleName()), "people_name", hanvonAttendanceLog.getPeopleName());
        queryWrapper.eq(StringUtils.isNotBlank(hanvonAttendanceLog.getAttendanceType()), "attendance_type", hanvonAttendanceLog.getAttendanceType());
        queryWrapper.between(StringUtils.isNotBlank(hanvonAttendanceLog.getBeginTime()), "check_time"
                , hanvonAttendanceLog.getBeginTime(), hanvonAttendanceLog.getEndTime());
        queryWrapper.eq(hanvonAttendanceLog.getCreateDate() != null, "create_date", hanvonAttendanceLog.getCreateDate());
        queryWrapper.eq(StringUtils.isNotBlank(hanvonAttendanceLog.getDeviceId()), "device_id", hanvonAttendanceLog.getDeviceId());
        queryWrapper.orderByDesc("check_time");
        return queryWrapper;

    }
}