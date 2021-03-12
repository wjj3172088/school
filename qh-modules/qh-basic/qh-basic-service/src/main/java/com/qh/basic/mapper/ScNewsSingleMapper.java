package com.qh.basic.mapper;

import com.qh.basic.domain.ScNewsNew;
import com.qh.basic.domain.ScNewsSingle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ScNewsSingleMapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
public interface ScNewsSingleMapper extends BaseMapper<ScNewsSingle> {
    /**
     * 批量新增
     *
     * @param newsSingleList 消息
     * @return
     */
    int batchInsertNewsSingle(@Param("list") List<ScNewsSingle> newsSingleList);
}
