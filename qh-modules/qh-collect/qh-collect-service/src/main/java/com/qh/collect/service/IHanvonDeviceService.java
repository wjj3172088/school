package com.qh.collect.service;

import com.qh.collect.domain.HanvonDevice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉王考勤机设备管理Service接口
 *
 * @author huangdaoquan
 * @date 2020-12-29
 */
public interface IHanvonDeviceService extends IService<HanvonDevice> {


    /**
     * 查询汉王考勤机设备管理集合
     * @param page 分页对象
     * @param hanvonDevice 操作汉王考勤机设备管理对象
     * @return 操作汉王考勤机设备管理集合
     */
    IPage<HanvonDevice> selectHanvonDeviceListByPage(IPage<HanvonDevice> page, HanvonDevice hanvonDevice);

}
