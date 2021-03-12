package com.qh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.system.domain.SysClientDetails;

/**
 * 终端配置Mapper接口
 * 
 * @author 
 */
public interface StudentStatisticsMapper extends BaseMapper<SysClientDetails>
{
    /**
     * 查询终端配置
     * 
     * @param clientId 终端配置ID
     * @return 终端配置
     */
    SysClientDetails selectSysClientDetailsById(String clientId);

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
     * 删除终端配置
     * 
     * @param clientId 终端配置ID
     * @return 结果
     */
    int deleteSysClientDetailsById(String clientId);

    /**
     * 批量删除终端配置
     * 
     * @param clientIds 需要删除的数据ID
     * @return 结果
     */
    int deleteSysClientDetailsByIds(String[] clientIds);
}
