package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.domain.SchoolExt;

/**
 * 学校信息扩展Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
public interface SchoolExtMapper extends BaseMapper<SchoolExt> {
    /**
     * 根据学校id查询
     *
     * @param orgId 学校id
     * @return
     */
    SchoolExt selectSchoolByOrgId(String orgId);

    /**
     * 根据学校id修改
     *
     * @param schoolExt 学校扩展信息
     */
    void modifyByOrgId(SchoolExt schoolExt);
}
