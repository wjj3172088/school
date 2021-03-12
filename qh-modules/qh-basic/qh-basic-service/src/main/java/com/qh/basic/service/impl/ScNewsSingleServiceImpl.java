package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScNewsSingle;
import com.qh.basic.mapper.ScNewsSingleMapper;
import com.qh.basic.service.IScNewsSingleService;
import com.qh.common.core.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScNewsSingleService业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-27
 */
@Service
public class ScNewsSingleServiceImpl extends ServiceImpl<ScNewsSingleMapper, ScNewsSingle> implements IScNewsSingleService {
    @Autowired
    private ScNewsSingleMapper newsSingleMapper;

    /**
     * 根据业务id删除
     *
     * @param bizId 业务id
     * @return
     */
    @Override
    public int deleteNewsSingleByBizId(String bizId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("biz_id", bizId);
        return newsSingleMapper.deleteByMap(map);
    }

    /**
     * 批量新增
     *
     * @param newsSingleList
     */
    @Override
    public void batchInsertNewsNew(List<ScNewsSingle> newsSingleList) {
        this.batchInsert(newsSingleList);
    }

    /**
     * 批量分次新增
     *
     * @param newsSingleList 需要新增的对象集合
     */
    private void batchInsert(List<ScNewsSingle> newsSingleList) {
        // 每100笔数据批量处理新增
        int count = newsSingleList.size();
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
            List<ScNewsSingle> toSaveList = newsSingleList.subList(i, i + toIndex);
            // 批量新增
            newsSingleMapper.batchInsertNewsSingle(toSaveList);
        }
    }
}