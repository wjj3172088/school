package com.qh.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScPublicityMapper;
import com.qh.basic.domain.ScPublicity;
import com.qh.basic.service.IScPublicityService;

import java.util.List;

/**
 * 安全宣传Service业务层处理
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Service
public class ScPublicityServiceImpl extends ServiceImpl<ScPublicityMapper, ScPublicity> implements IScPublicityService {

    @Autowired
    ScPublicityMapper publicityMapper;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询安全宣传集合
     *
     * @param scPublicity 操作安全宣传对象
     * @return 操作安全宣传集合
     */
    @Override
    public IPage<ScPublicity> selectScPublicityListByPage(IPage<ScPublicity> page, ScPublicity scPublicity) {

        scPublicity.setOrgId(SecurityUtils.getOrgId());
        //根据自定义SQL语句拼装查询
        IPage<ScPublicity> pagePublicity = publicityMapper.selectListByPage(page, scPublicity);
        for (ScPublicity publicity : pagePublicity.getRecords()) {
            List<String> picurlsResult = JSON.parseArray(publicity.getPicurl(),String.class);
            publicity.setPList(picUtils.imageAddFristDomain(picurlsResult));
        }
        return pagePublicity;
    }


    /**
     * 查询安全宣传参数拼接
     */
    private QueryWrapper<ScPublicity> getQuery(ScPublicity scPublicity) {
        QueryWrapper<ScPublicity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(scPublicity.getTitle()), "title", scPublicity.getTitle());
        queryWrapper.eq(scPublicity.getType() != null, "type", scPublicity.getType());
        queryWrapper.eq(scPublicity.getCreateDate() != null, "create_date", scPublicity.getCreateDate());
        queryWrapper.eq(scPublicity.getStateMark() != null, "state_mark", scPublicity.getStateMark());
        queryWrapper.eq(StringUtils.isNotBlank(scPublicity.getOrgId()), "org_id", scPublicity.getOrgId());
        queryWrapper.orderByDesc("publicity_id");
        return queryWrapper;

    }
}