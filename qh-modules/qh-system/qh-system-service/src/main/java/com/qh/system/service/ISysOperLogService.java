package com.qh.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.system.api.domain.SysOperLog;

/**
 * 操作日志 服务层
 *
 * @author
 */
public interface ISysOperLogService extends IService<SysOperLog> {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
    int insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     * @param page 分页对象
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    IPage<SysOperLog> selectOperLogListByPage(IPage<SysOperLog> page, SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     * @param operLog
     * @return
     */
    List<SysOperLog> selectOperLogListAll(SysOperLog operLog);

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
