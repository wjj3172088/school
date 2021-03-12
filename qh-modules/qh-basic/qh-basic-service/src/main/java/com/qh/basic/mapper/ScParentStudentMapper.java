package com.qh.basic.mapper;

import com.qh.basic.domain.ScParentStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家长学生关系Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
public interface ScParentStudentMapper extends BaseMapper<ScParentStudent> {
    /**
     * 根据移动账号id查询信息
     *
     * @param accId 移动账号id
     * @return
     */
    Integer countDefaultByAccId(String accId);

    /**
     * 删除学生和家长的关系
     *
     * @param stuIdList 学生id
     */
    void deleteByStuIdList(@Param("stuIdList") List<String> stuIdList);

    /**
     * 设置家长的默认孩子
     *
     * @param stuIdList 学生id
     */
    void setParentDefaultChild(@Param("stuIdList") List<String> stuIdList);
}
