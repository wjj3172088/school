package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScNewsInfo;

import java.util.List;

/**
 * 校园资讯Service接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface IScNewsInfoService extends IService<ScNewsInfo> {


    /**
     * 查询校园资讯集合
     *
     * @param page       分页信息
     * @param scNewsInfo 操作校园资讯对象
     * @return 操作校园资讯集合
     */
    IPage<ScNewsInfo> selectScNewsInfoListByPage(IPage<ScNewsInfo> page, ScNewsInfo scNewsInfo);

    /**
     * 批量保存
     *
     * @param newsGroupId  资讯分组id
     * @param newsInfoList 资讯新闻列表
     * @return
     */
    String save(String newsGroupId, List<ScNewsInfo> newsInfoList);

    /**
     * 根据资讯分组id删除
     *
     * @param newsGroupId 资讯分组id
     */
    void batchDelete(String newsGroupId);

    /**
     * 根据资讯分组id查询
     *
     * @param newsGroupId 资讯分组id
     * @return
     */
    List<ScNewsInfo> selectByNewGroupId(String newsGroupId);
}
