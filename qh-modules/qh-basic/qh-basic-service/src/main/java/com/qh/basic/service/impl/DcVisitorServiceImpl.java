package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.api.config.DeviceConfig;
import com.qh.basic.domain.DcVisitor;
import com.qh.basic.domain.vo.DcVisitorVo;
import com.qh.basic.mapper.DcVisitorMapper;
import com.qh.basic.model.request.visitor.VisitorSearchRequest;
import com.qh.basic.service.IDcVisitorService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 访客信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-14
 */
@Service
public class DcVisitorServiceImpl extends ServiceImpl<DcVisitorMapper, DcVisitor> implements IDcVisitorService {
    @Autowired
    private DcVisitorMapper dcVisitorMapper;
    @Autowired
    private DeviceConfig deviceConfig;

    /**
     * 查询访客信息集合
     *
     * @param page    分页信息
     * @param request 操作访客信息对象
     * @return 操作访客信息集合
     */
    @Override
    public IPage<DcVisitorVo> selectDcVisitorListByPage(IPage<DcVisitor> page, VisitorSearchRequest request) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("xm", request.getXm());
        map.put("gmsfhm", request.getGmsfhm());
        if (StringUtils.isNotEmpty(request.getBeginTime())) {
            map.put("beginTime", request.getBeginTime() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            map.put("endTime", request.getEndTime() + " 23:59:59");
        }
        IPage<DcVisitorVo> pageResult = dcVisitorMapper.selectListByPage(page, map);
        for (DcVisitorVo dcVisitorVo : pageResult.getRecords()) {
            if (StringUtils.isNotEmpty(dcVisitorVo.getPicurl())) {
                dcVisitorVo.setPicurl(deviceConfig.getBaseImage().concat(dcVisitorVo.getPicurl()));
            }
        }
        return pageResult;
    }
}