package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScSubject;

import java.util.Map;

/**
 * 学校科目Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-12-04
 */
public interface ScSubjectMapper extends BaseMapper<ScSubject> {

    /**
     * 修改
     *
     * @param map 科目信息
     */
    void modifyBySubjectIdAndOrgId(Map<String,Object> map);
}
