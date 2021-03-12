package com.qh.basic.service;

import com.qh.basic.domain.ScSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 教职工排班Service接口
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
public interface IScScheduleService extends IService<ScSchedule> {


    /**
     * 查询教职工排班集合
     *
     * @param page         分页信息
     * @param scSchedule 操作教职工排班对象
     * @return 操作教职工排班集合
     */
    IPage<ScSchedule> selectScScheduleListByPage(IPage<ScSchedule> page, ScSchedule scSchedule);

}
