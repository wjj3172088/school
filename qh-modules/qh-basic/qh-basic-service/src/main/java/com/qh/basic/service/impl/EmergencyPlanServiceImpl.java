package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.EmergencyPlanMapper;
import com.qh.basic.domain.EmergencyPlan;
import com.qh.basic.service.IEmergencyPlanService;

/**
 * 应急预案Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-20
 */
@Service
public class EmergencyPlanServiceImpl extends ServiceImpl<EmergencyPlanMapper, EmergencyPlan> implements IEmergencyPlanService {

    /**
     * 查询应急预案集合
     *
     * @param page          分页信息
     * @param emergencyPlan 操作应急预案对象
     * @return 操作应急预案集合
     */
    @Override
    public IPage<EmergencyPlan> selectEmergencyPlanListByPage(IPage<EmergencyPlan> page, EmergencyPlan emergencyPlan) {
        return this.page(page, getQuery(emergencyPlan));
    }

    /**
     * 修改
     *
     * @param emergencyPlan 应急预案对象
     */
    @Override
    public void modify(EmergencyPlan emergencyPlan) {
        if (StringUtils.isEmpty(emergencyPlan.getPlanId())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "应急预案id");
        }
        emergencyPlan.setUpdateDate(DateUtil.getSystemSeconds());
        super.baseMapper.modifyByPlanId(emergencyPlan);
    }

    /**
     * 查询应急预案参数拼接
     */
    private QueryWrapper<EmergencyPlan> getQuery(EmergencyPlan emergencyPlan) {
        QueryWrapper<EmergencyPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(emergencyPlan.getName()), "name", emergencyPlan.getName());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq(emergencyPlan.getType() != null, "type", emergencyPlan.getType());
        queryWrapper.eq(emergencyPlan.getLevel() != null, "level", emergencyPlan.getLevel());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}