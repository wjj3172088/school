package com.qh.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.SchoolExt;
import com.qh.basic.mapper.SchoolExtMapper;
import com.qh.basic.model.request.school.ModifySchoolExtRequest;
import com.qh.basic.service.ISchoolExtService;
import com.qh.common.core.utils.bean.BeanUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 学校信息扩展Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
@Service
public class SchoolExtServiceImpl extends ServiceImpl<SchoolExtMapper, SchoolExt> implements ISchoolExtService {
    /**
     * 根据学校id查询
     *
     * @param orgId 学校id
     * @return
     */
    @Override
    public SchoolExt selectSchoolByOrgId(String orgId) {
        return this.baseMapper.selectSchoolByOrgId(orgId);
    }

    /**
     * 修改
     *
     * @param schoolExtRequest 学校扩展
     */
    @Override
    public void modify(ModifySchoolExtRequest schoolExtRequest) {
        SchoolExt schoolExt = new SchoolExt();
        BeanUtils.copyProperties(schoolExtRequest, schoolExt);
        schoolExt.setOrgId(SecurityUtils.getOrgId());
//        if (!CollectionUtils.isEmpty(schoolExtRequest.getLeaderData())) {
//            String leaderData = JSON.toJSONString(schoolExtRequest.getLeaderData());
//            schoolExt.setLeaderData(leaderData);
//        }
//        if (!CollectionUtils.isEmpty(schoolExtRequest.getSafeData())) {
//            String safeData = JSON.toJSONString(schoolExtRequest.getSafeData());
//            schoolExt.setSafeData(safeData);
//        }
        this.baseMapper.modifyByOrgId(schoolExt);
    }
}