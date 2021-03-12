package com.qh.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.SchoolExt;
import com.qh.basic.model.request.school.ModifySchoolExtRequest;

/**
 * 学校信息扩展Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
public interface ISchoolExtService extends IService<SchoolExt> {
    /**
     * 根据学校id查询
     *
     * @param orgId 学校id
     * @return
     */
    SchoolExt selectSchoolByOrgId(String orgId);

    /**
     * 修改
     *
     * @param schoolExtRequest 学校扩展
     */
    void modify(ModifySchoolExtRequest schoolExtRequest);
}
