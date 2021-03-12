package com.qh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.system.api.domain.SysOperLog;

/**
 * 操作日志 数据层
 *
 * @author
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
    int insertOperlog(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
