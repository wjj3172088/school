package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 信息数据申请Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-12-24
 */
public interface ScApplyMapper extends BaseMapper<ScApply> {
    /**
     * 批量新增
     *
     * @param applyList 申请单列表
     * @return
     */
    int batchInsert(@Param("list") List<ScApply> applyList);
}
