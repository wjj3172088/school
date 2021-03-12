package com.qh.basic.service;

import com.qh.basic.domain.ScNewsSingle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * ScNewsSingleService接口
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
public interface IScNewsSingleService extends IService<ScNewsSingle> {
    /**
     * 根据业务id删除
     *
     * @param bizId 业务id
     * @return
     */
    int deleteNewsSingleByBizId(String bizId);

    /**
     * 批量新增
     *
     * @param newsSingleList
     */
    void batchInsertNewsNew(List<ScNewsSingle> newsSingleList);
}
