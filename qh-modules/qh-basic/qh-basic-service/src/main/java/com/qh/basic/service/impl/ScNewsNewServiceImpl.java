package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScNewsNew;
import com.qh.basic.mapper.ScNewsNewMapper;
import com.qh.basic.service.IScNewsNewService;
import com.qh.common.core.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息阅读明细Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
@Service
public class ScNewsNewServiceImpl extends ServiceImpl<ScNewsNewMapper, ScNewsNew> implements IScNewsNewService {
    @Autowired
    private ScNewsNewMapper newsNewMapper;

    /**
     * 根据业务id删除
     *
     * @param bizId 业务id
     * @return
     */
    @Override
    public int deleteNewsNewByBizId(String bizId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("biz_id", bizId);
        return newsNewMapper.deleteByMap(map);
    }

    /**
     * 批量新增
     *
     * @param news
     */
    @Override
    public void batchInsertNewsNew(List<ScNewsNew> news) {
        this.batchInsert(news);
    }

    /**
     * 批量分次新增
     *
     * @param newsNewList 需要新增的对象集合
     */
    private void batchInsert(List<ScNewsNew> newsNewList) {
        // 每100笔数据批量处理新增
        int count = newsNewList.size();
        // 每次循环真实要处理的数据笔数
        int toIndex = Constants.BATCH_NUM;
        // 每次循环设定要处理的数据笔数
        int batchNum = Constants.BATCH_NUM;
        for (int i = 0; i < count; i += batchNum) {
            if (i + batchNum > count) {
                // 最后一次循环如果没有100条数据，剩余的笔数
                toIndex = count - i;
            }
            // 此次循环要处理的数据集合
            List<ScNewsNew> toSaveList = newsNewList.subList(i, i + toIndex);
            // 批量新增
            newsNewMapper.batchInsertNewsNew(toSaveList);
        }
    }
}