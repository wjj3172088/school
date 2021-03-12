package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.protocol.Security;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScSignInMapper;
import com.qh.basic.domain.ScSignIn;
import com.qh.basic.service.IScSignInService;

import java.util.List;


/**
 * 到校签到Service业务层处理
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Service
public class ScSignInServiceImpl extends ServiceImpl<ScSignInMapper, ScSignIn> implements IScSignInService {

    /**
     * 查询到校签到集合
     *
     * @param page         分页信息
     * @param scSignIn 操作到校签到对象
     * @return 操作到校签到集合
     */
    @Override
    public IPage<ScSignIn> selectScSignInListByPage(IPage<ScSignIn> page, ScSignIn scSignIn) {
        scSignIn.setOrgId(SecurityUtils.getOrgId());
        return this.page(page, getQuery(scSignIn));
    }


    /**
     * 查询到校签到集合
     *
     * @param scSignIn 操作到校签到对象
     * @return 操作到校签到集合
     */
    @Override
    public List<ScSignIn> selectScSignInList(ScSignIn scSignIn) {
        scSignIn.setOrgId(SecurityUtils.getOrgId());
        return this.list(getQuery(scSignIn));
    }


    /**
     * 查询到校签到参数拼接
     */
    private QueryWrapper<ScSignIn> getQuery(ScSignIn scSignIn) {
        QueryWrapper<ScSignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(scSignIn.getOrgId()), "org_id", scSignIn.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(scSignIn.getStuName()), "stu_name", scSignIn.getStuName());
        queryWrapper.like(StringUtils.isNotBlank(scSignIn.getPublisherName()), "publisher_name", scSignIn.getPublisherName());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;

    }
}