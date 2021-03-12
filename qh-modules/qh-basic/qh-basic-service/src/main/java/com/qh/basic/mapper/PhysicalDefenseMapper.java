package com.qh.basic.mapper;

import com.qh.basic.domain.PhysicalDefense;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物防信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2021-01-22
 */
public interface PhysicalDefenseMapper extends BaseMapper<PhysicalDefense> {
    /**
     * 批量新增
     *
     * @param addList
     */
    void batchInsert(@Param("list") List<PhysicalDefense> addList);

    /**
     * 批量修改
     *
     * @param modifyList
     */
    void batchUpdate(@Param("list") List<PhysicalDefense> modifyList);
}
