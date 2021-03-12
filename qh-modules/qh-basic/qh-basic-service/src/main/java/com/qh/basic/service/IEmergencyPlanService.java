package com.qh.basic.service;

import com.qh.basic.domain.EmergencyPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 应急预案Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-20
 */
public interface IEmergencyPlanService extends IService<EmergencyPlan> {


    /**
     * 查询应急预案集合
     *
     * @param page          分页信息
     * @param emergencyPlan 操作应急预案对象
     * @return 操作应急预案集合
     */
    IPage<EmergencyPlan> selectEmergencyPlanListByPage(IPage<EmergencyPlan> page, EmergencyPlan emergencyPlan);

    /**
     * 修改
     *
     * @param emergencyPlan 应急预案对象
     */
    void modify(EmergencyPlan emergencyPlan);
}
