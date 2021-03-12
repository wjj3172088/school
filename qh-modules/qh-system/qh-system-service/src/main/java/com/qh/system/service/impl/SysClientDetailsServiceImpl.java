package com.qh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.constant.CacheConstants;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.domain.SysClientDetails;
import com.qh.system.mapper.SysClientDetailsMapper;
import com.qh.system.service.ISysClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 终端配置Service业务层处理
 * 
 * @author
 */
@Service
public class SysClientDetailsServiceImpl extends ServiceImpl<SysClientDetailsMapper, SysClientDetails> implements ISysClientDetailsService
{
    @Autowired
    private SysClientDetailsMapper sysClientDetailsMapper;

    /**
     * 查询终端配置
     * 
     * @param clientId 终端配置ID
     * @return 终端配置
     */
    @Override
    public SysClientDetails selectSysClientDetailsById(String clientId)
    {
        return sysClientDetailsMapper.selectSysClientDetailsById(clientId);
    }

    /**
     * 查询终端配置列表
     * 
     * @param sysClientDetails 终端配置
     * @return 终端配置
     */
    @Override
    public IPage<SysClientDetails> selectSysClientDetailsList(IPage<SysClientDetails> page, SysClientDetails sysClientDetails)
    {
        QueryWrapper<SysClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sysClientDetails.getClientId()), "client_id", sysClientDetails.getClientId());
        return this.page(page, queryWrapper);
    }

    /**
     * 新增终端配置
     * 
     * @param sysClientDetails 终端配置
     * @return 结果
     */
    @Override
    public int insertSysClientDetails(SysClientDetails sysClientDetails)
    {
        sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getOriginSecret()));
        return sysClientDetailsMapper.insertSysClientDetails(sysClientDetails);
    }

    /**
     * 修改终端配置
     * 
     * @param sysClientDetails 终端配置
     * @return 结果
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#sysClientDetails.clientId")
    public int updateSysClientDetails(SysClientDetails sysClientDetails)
    {
        sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getOriginSecret()));
        return sysClientDetailsMapper.updateSysClientDetails(sysClientDetails);
    }

    /**
     * 批量删除终端配置
     * 
     * @param clientIds 需要删除的终端配置ID
     * @return 结果
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
    public int deleteSysClientDetailsByIds(String[] clientIds)
    {
        return sysClientDetailsMapper.deleteSysClientDetailsByIds(clientIds);
    }
}
