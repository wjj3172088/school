package com.qh.basic.mapper;

import com.qh.basic.domain.ScNewsNew;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息阅读明细Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
public interface ScNewsNewMapper extends BaseMapper<ScNewsNew> {
    /**
     * 批量新增
     *
     * @param schoolNewsNews 消息
     * @return
     */
    int batchInsertNewsNew(@Param("list") List<ScNewsNew> schoolNewsNews);
}
