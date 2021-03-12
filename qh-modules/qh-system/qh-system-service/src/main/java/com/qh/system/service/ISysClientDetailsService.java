package com.qh.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.system.domain.SysClientDetails;

/**
 * 终端配置Service接口
 * 
 * @author
 */
public interface ISysClientDetailsService extends IService<SysClientDetails>
{
    /**
     * 查询终端配置
     * 
     * @param clientId 终端配置ID
     * @return 终端配置
     */
    SysClientDetails selectSysClientDetailsById(String clientId);

    /**
     * 查询终端配置列表
     * @param page 分页对象
     * @param sysClientDetails 终端配置
     * @return 终端配置集合
     */
    IPage<SysClientDetails> selectSysClientDetailsList(IPage<SysClientDetails> page, SysClientDetails sysClientDetails);

    /**
     * 新增终端配置
     * 
     * @param sysClientDetails 终端配置
     * @return 结果
     */
    int insertSysClientDetails(SysClientDetails sysClientDetails);

    /**
     * 修改终端配置
     * 
     * @param sysClientDetails 终端配置
     * @return 结果
     */
    int updateSysClientDetails(SysClientDetails sysClientDetails);

    /**
     * 批量删除终端配置
     * 
     * @param clientIds 需要删除的终端配置ID
     * @return 结果
     */
    int deleteSysClientDetailsByIds(String[] clientIds);
}
