package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScGraduateDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 毕业详情Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
public interface ScGraduateDetailMapper extends BaseMapper<ScGraduateDetail> {
    /**
     * 批量新增
     *
     * @param graduateDetailList 毕业详情列表
     * @return
     */
    int batchInsert(@Param("list") List<ScGraduateDetail> graduateDetailList);
}
