package com.qh.collect.service;

import com.qh.collect.model.response.HwSocketStatusResp;

/**
 * 汉王考勤机业务层处理接口
 * @author 黄道权
 * @date 2020-11-13
 */
public interface IHwAttendanceService {


    /**
     * 启动侦听服务
     * @return
     */
    HwSocketStatusResp startListenerAction();

    /**
     * 读取侦听服务
     * @return
     */
    HwSocketStatusResp getListenerAction();


}
