package com.qh.collect.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.collect.mapper.HanvonDeviceMapper;
import com.qh.collect.domain.HanvonDevice;
import com.qh.collect.service.IHanvonDeviceService;

/**
 * 汉王考勤机设备管理Service业务层处理
 *
 * @author huangdaoquan
 * @date 2020-12-29
 */
@Service
public class HanvonDeviceServiceImpl extends ServiceImpl<HanvonDeviceMapper, HanvonDevice> implements IHanvonDeviceService {

    /**
     * 查询汉王考勤机设备管理集合
     *
     * @param hanvonDevice 操作汉王考勤机设备管理对象
     * @return 操作汉王考勤机设备管理集合
     */
    @Override
    public IPage<HanvonDevice> selectHanvonDeviceListByPage(IPage<HanvonDevice> page, HanvonDevice hanvonDevice) {
        return this.page(page, getQuery(hanvonDevice));
    }


    /**
     * 查询汉王考勤机设备管理参数拼接
     */
    private QueryWrapper<HanvonDevice> getQuery(HanvonDevice hanvonDevice) {
        QueryWrapper<HanvonDevice> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(hanvonDevice.getOrgId()), "org_id", hanvonDevice.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(hanvonDevice.getOrgName()), "org_name", hanvonDevice.getOrgName());
        queryWrapper.like(StringUtils.isNotBlank(hanvonDevice.getDeviceName()), "device_name", hanvonDevice.getDeviceName());
        queryWrapper.like(StringUtils.isNotBlank(hanvonDevice.getCreateUserName()), "create_user_name", hanvonDevice.getCreateUserName());
        queryWrapper.like(StringUtils.isNotBlank(hanvonDevice.getUpdateUserName()), "update_user_name", hanvonDevice.getUpdateUserName());
        queryWrapper.eq(hanvonDevice.getOnlineState() != null, "online_state", hanvonDevice.getOnlineState());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("org_id");
        return queryWrapper;

    }
}