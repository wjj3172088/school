package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScOperLog;
import com.qh.basic.enums.OperTypeEnum;
import com.qh.basic.mapper.ScOperLogMapper;
import com.qh.basic.model.request.operlog.SearchRequest;
import com.qh.basic.service.IScOperLogService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 操作记录Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Service
public class ScOperLogServiceImpl extends ServiceImpl<ScOperLogMapper, ScOperLog> implements IScOperLogService {
    /**
     * 查询操作记录集合
     *
     * @param page    分页信息
     * @param request 操作操作记录对象
     * @return 操作操作记录集合
     */
    @Override
    public IPage<ScOperLog> selectScOperLogListByPage(IPage<ScOperLog> page, SearchRequest request) {
        return this.page(page, getQuery(request));
    }

    /**
     * 操作记录
     *
     * @param userId       操作人id
     * @param userName     操作人姓名
     * @param operType     操作类型
     * @param operUserType 操作人类型
     */
    @Override
    public void add(String userId, String userName, int operType, int operUserType) {
        ScOperLog log = new ScOperLog();
        log.setOperId(userId);
        log.setOperName(userName);
        log.setOrgId(SecurityUtils.getOrgId());
        log.setOrgName(SecurityUtils.getOrgName());
        log.setOperType(operType);
        log.setOperContent(OperTypeEnum.getMessage(operType));
        log.setOperUserType(operUserType);
        log.setCreateDate(DateUtil.getSystemSeconds());
        log.setModifyDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(log);
    }

    /**
     * 查询操作记录参数拼接
     */
    private QueryWrapper<ScOperLog> getQuery(SearchRequest request) {

        QueryWrapper<ScOperLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(request.getOperName()), "oper_name", request.getOperName());
        queryWrapper.eq(request.getOperType() != null, "oper_type", request.getOperType());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        if (StringUtils.isNotEmpty(request.getBeginTime())) {
            Long startTime = DateUtils.dateFirstTime(request.getBeginTime());
            queryWrapper.ge("create_date", startTime);
        }
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            Long endTime = DateUtils.dateLastTime(request.getEndTime());
            queryWrapper.le("create_date", endTime);
        }
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}