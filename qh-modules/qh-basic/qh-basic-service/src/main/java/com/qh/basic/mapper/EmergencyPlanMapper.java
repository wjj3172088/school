package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.EmergencyPlan;

/**
 * 应急预案Mapper接口
 *
 * @author 汪俊杰
 * @date 2021-01-20
 */
public interface EmergencyPlanMapper extends BaseMapper<EmergencyPlan> {
    /**
     * 根据planId修改
     *
     * @param emergencyPlan 应急预案信息
     */
    void modifyByPlanId(EmergencyPlan emergencyPlan);
}
