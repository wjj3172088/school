package com.qh.basic.mapper;

import com.qh.basic.domain.ScPublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 公告模板Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-24
 */
public interface ScPublishMapper extends BaseMapper<ScPublish> {
    /**
     * 修改
     *
     * @param publish 模板公告信息
     */
    void modify(ScPublish publish);
}
