package com.qh.basic.service;

import com.qh.basic.domain.ScNewsNew;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 消息阅读明细Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
public interface IScNewsNewService extends IService<ScNewsNew> {
    /**
     * 根据业务id删除
     *
     * @param bizId 业务id
     * @return
     */
    int deleteNewsNewByBizId(String bizId);

    /**
     * 批量新增
     *
     * @param news
     */
    void batchInsertNewsNew(List<ScNewsNew> news);
}
