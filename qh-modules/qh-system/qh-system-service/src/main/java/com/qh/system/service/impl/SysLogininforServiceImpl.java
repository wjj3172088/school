package com.qh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.domain.SysLogininfor;
import com.qh.system.mapper.SysLogininforMapper;
import com.qh.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author 
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService
{

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public int insertLogininfor(SysLogininfor logininfor)
    {
        return logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public IPage<SysLogininfor> selectLogininforList(IPage<SysLogininfor> page, SysLogininfor logininfor)
    {
        return this.page(page, getQuery(logininfor));
    }

    private QueryWrapper<SysLogininfor> getQuery(SysLogininfor logininfor){
        QueryWrapper<SysLogininfor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logininfor.getIpaddr()), "ipaddr", logininfor.getIpaddr());
        queryWrapper.eq(StringUtils.isNotBlank(logininfor.getStatus()), "status", logininfor.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(logininfor.getUserName()), "user_name", logininfor.getUserName());
        queryWrapper.ge(StringUtils.isNotBlank(logininfor.getBeginTime()), "date_format(access_time,'%y%m%d')", DateUtils.formatYmd(logininfor.getBeginTime()));
        queryWrapper.le(StringUtils.isNotBlank(logininfor.getEndTime()), "date_format(access_time,'%y%m%d')", DateUtils.formatYmd(logininfor.getEndTime()));
        queryWrapper.orderByDesc("info_id");
        return queryWrapper;
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforListAll(SysLogininfor logininfor){
        return this.list(getQuery(logininfor));
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds)
    {
        return logininforMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
