package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScNewsGroup;
import com.qh.basic.domain.ScNewsInfo;
import com.qh.basic.model.response.newsgroup.NewsGroupDetailReponse;

import java.util.List;

/**
 * 咨询分组Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-18
 */
public interface IScNewsGroupService extends IService<ScNewsGroup> {


    /**
     * 查询咨询分组集合
     *
     * @param page        分页信息
     * @param scNewsGroup 操作咨询分组对象
     * @return 操作咨询分组集合
     */
    IPage<ScNewsGroup> selectScNewsGroupListByPage(IPage<ScNewsGroup> page, ScNewsGroup scNewsGroup);

    /**
     * 新增
     *
     * @param type         资讯类型
     * @param newsInfoList 资讯集合
     */
    void add(Integer type, List<ScNewsInfo> newsInfoList);

    /**
     * 修改
     *
     * @param newsGroupId  资讯分组id
     * @param type         资讯类型
     * @param newsInfoList 资讯集合
     */
    void modify(String newsGroupId, Integer type, List<ScNewsInfo> newsInfoList);

    /**
     * 删除
     *
     * @param newsGroupIdList 资讯分组id集合
     */
    void delete(List<String> newsGroupIdList);

    /**
     * 根据资讯分组id查询
     *
     * @param newsGroupId 资讯分组id
     * @return
     */
    NewsGroupDetailReponse selectByNewGroupId(String newsGroupId);
}
