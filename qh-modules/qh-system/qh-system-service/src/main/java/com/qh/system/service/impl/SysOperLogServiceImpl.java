package com.qh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.api.domain.SysOperLog;
import com.qh.system.mapper.SysOperLogMapper;
import com.qh.system.service.ISysOperLogService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author 
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
    @Override
    public int insertOperlog(SysOperLog operLog) {
        return operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public IPage<SysOperLog> selectOperLogListByPage(IPage<SysOperLog> page, SysOperLog operLog) {
        return this.page(page, getQuery(operLog));
    }

    private QueryWrapper<SysOperLog> getQuery(SysOperLog operLog) {
        QueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(operLog.getTitle()), "title", operLog.getTitle());
        if (operLog.getBusinessType() != null) {
            queryWrapper.eq("business_type", operLog.getBusinessType());
        }
        queryWrapper.like(StringUtils.isNotBlank(operLog.getOrgId()), "org_id", operLog.getOrgId());
        queryWrapper.in(ArrayUtils.isNotEmpty(operLog.getBusinessTypes()), "business_type", operLog.getBusinessTypes());
        queryWrapper.eq(operLog.getStatus() != null, "status", operLog.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(operLog.getOperName()), "oper_name", operLog.getOperName());
        queryWrapper.ge(StringUtils.isNotBlank(operLog.getBeginTime()), "date_format(oper_time,'%y%m%d')", DateUtils.formatYmd(operLog.getBeginTime()));
        queryWrapper.le(StringUtils.isNotBlank(operLog.getEndTime()), "date_format(oper_time,'%y%m%d')", DateUtils.formatYmd(operLog.getEndTime()));
        queryWrapper.orderByDesc("oper_id");
        return queryWrapper;
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogListAll(SysOperLog operLog) {
        return this.list(getQuery(operLog));
    }


    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
