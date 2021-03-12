package com.qh.basic.service;

import com.qh.basic.domain.ScPublish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公告模板Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-24
 */
public interface IScPublishService extends IService<ScPublish> {


    /**
     * 查询公告模板集合
     *
     * @param page      分页信息
     * @param scPublish 操作公告模板对象
     * @return 操作公告模板集合
     */
    IPage<ScPublish> selectScPublishListByPage(IPage<ScPublish> page, ScPublish scPublish);

    /**
     * 新增
     *
     * @param publish 公告模板
     * @return 公告模板id
     */
    int add(ScPublish publish);

    /**
     * 修改
     *
     * @param publish 公告模板
     */
    void modify(ScPublish publish);
}
