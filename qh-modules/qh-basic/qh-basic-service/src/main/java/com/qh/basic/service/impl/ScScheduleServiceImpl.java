package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScScheduleMapper;
import com.qh.basic.domain.ScSchedule;
import com.qh.basic.service.IScScheduleService;

/**
 * 教职工排班Service业务层处理
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Service
public class ScScheduleServiceImpl extends ServiceImpl<ScScheduleMapper, ScSchedule> implements IScScheduleService {

    /**
     * 查询教职工排班集合
     *
     * @param page         分页信息
     * @param scSchedule 操作教职工排班对象
     * @return 操作教职工排班集合
     */
    @Override
    public IPage<ScSchedule> selectScScheduleListByPage(IPage<ScSchedule> page, ScSchedule scSchedule) {
        scSchedule.setOrgId(SecurityUtils.getOrgId());
        return this.page(page, getQuery(scSchedule));
    }


    /**
     * 查询教职工排班参数拼接
     */
    private QueryWrapper<ScSchedule> getQuery(ScSchedule scSchedule) {
        QueryWrapper<ScSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(scSchedule.getOrgId()), "org_id", scSchedule.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(scSchedule.getScheduleName()), "schedule_name", scSchedule.getScheduleName());
        queryWrapper.like(StringUtils.isNotBlank(scSchedule.getPublisherName()), "publisher_name", scSchedule.getPublisherName());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;

    }
}