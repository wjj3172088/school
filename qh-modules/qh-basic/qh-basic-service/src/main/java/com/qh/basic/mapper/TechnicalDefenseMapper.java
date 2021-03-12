package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.TechnicalDefense;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 技防信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2021-01-25
 */
public interface TechnicalDefenseMapper extends BaseMapper<TechnicalDefense> {
    /**
     * 批量新增
     *
     * @param addList
     */
    void batchInsert(@Param("list") List<TechnicalDefense> addList);

    /**
     * 批量修改
     *
     * @param modifyList
     */
    void batchUpdate(@Param("list") List<TechnicalDefense> modifyList);

    /**
     * 修改
     *
     * @param technicalDefense
     */
    int updateByDefenseId(TechnicalDefense technicalDefense);
}
