package com.qh.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScNewsInfo;
import com.qh.basic.mapper.ScNewsInfoMapper;
import com.qh.basic.service.IScNewsInfoService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 校园资讯Service业务层处理
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Service
public class ScNewsInfoServiceImpl extends ServiceImpl<ScNewsInfoMapper, ScNewsInfo> implements IScNewsInfoService {

    @Autowired
    ScNewsInfoMapper newsInfoMapper;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询校园资讯集合
     *
     * @param scNewsInfo 操作校园资讯对象
     * @return 操作校园资讯集合
     */
    @Override
    public IPage<ScNewsInfo> selectScNewsInfoListByPage(IPage<ScNewsInfo> page, ScNewsInfo scNewsInfo) {

        scNewsInfo.setOrgId(SecurityUtils.getOrgId());
        //根据自定义SQL语句拼装查询
        IPage<ScNewsInfo> pagePublicity = newsInfoMapper.selectListByPage(page, scNewsInfo);
        for (ScNewsInfo publicity : pagePublicity.getRecords()) {
            List<String> picurlsResult = JSON.parseArray(publicity.getPicurl(), String.class);
            publicity.setPList(picUtils.imageAddFristDomain(picurlsResult));
        }
        return pagePublicity;
    }

    /**
     * 批量保存
     *
     * @param newsGroupId  资讯分组id
     * @param newsInfoList 资讯新闻列表
     * @return
     */
    @Override
    public String save(String newsGroupId, List<ScNewsInfo> newsInfoList) {
        List<ScNewsInfo> dbNewsList = this.selectByNewGroupId(newsGroupId);
        //新增暂存
        List<ScNewsInfo> addNewsList = new ArrayList<>();
        //修改暂存
        List<ScNewsInfo> modifyNewsList = new ArrayList<>();
        //删除暂存
        List<ScNewsInfo> deleteNewsList = new ArrayList<>();
        //当前新增和修改的资讯新闻id值，用于判断数据库中哪些需要删除
        List<String> newsGroupIdList = newsInfoList.stream().map(ScNewsInfo::getPublicityId).collect(Collectors.toList());
        String orgId = SecurityUtils.getOrgId();
        for (ScNewsInfo paraNewsInfo : newsInfoList) {
            if (StringUtils.isNotEmpty(paraNewsInfo.getPublicityId())) {
                modifyNewsList.add(paraNewsInfo);
            } else {
                addNewsList.add(paraNewsInfo);
            }
        }
        deleteNewsList.addAll(dbNewsList.stream().filter(x -> !newsGroupIdList.contains(x.getPublicityId())).collect(Collectors.toList()));

        if (!CollectionUtils.isEmpty(addNewsList)) {
            addNewsList.forEach(x -> {
                x.setPublicityId(UUIDG.generate());
                x.setNewsGroupId(newsGroupId);
                x.setOrgId(orgId);
                x.setCreateDate(DateUtil.getSystemSeconds());
                x.setModifyDate(DateUtil.getSystemSeconds());
            });
            //批量新增
            newsInfoMapper.batchInsert(addNewsList);
        }
        if (!CollectionUtils.isEmpty(modifyNewsList)) {
            modifyNewsList.forEach(x -> {
                x.setModifyDate(DateUtil.getSystemSeconds());
            });
            //批量修改
            newsInfoMapper.batchUpdate(modifyNewsList);
        }
        if (!CollectionUtils.isEmpty(deleteNewsList)) {
            //批量删除
            newsInfoMapper.batchDelete(deleteNewsList);
        }

        newsInfoList.forEach(x -> {
            x.setContent(null);
            x.setPList(null);
        });
        return JSON.toJSONString(newsInfoList);
    }

    /**
     * 根据资讯分组id删除
     *
     * @param newsGroupId 资讯分组id
     */
    @Override
    public void batchDelete(String newsGroupId) {
        Map<String, Object> deleteMap = new HashMap<>(3);
        deleteMap.put("org_id", SecurityUtils.getOrgId());
        deleteMap.put("news_group_id", newsGroupId);
        newsInfoMapper.deleteByMap(deleteMap);
    }

    /**
     * 查询校园资讯参数拼接
     */
    private QueryWrapper<ScNewsInfo> getQuery(ScNewsInfo scNewsInfo) {
        QueryWrapper<ScNewsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(scNewsInfo.getTitle()), "title", scNewsInfo.getTitle());
        queryWrapper.eq(scNewsInfo.getType() != null, "type", scNewsInfo.getType());
        queryWrapper.eq(scNewsInfo.getCreateDate() != null, "create_date", scNewsInfo.getCreateDate());
        queryWrapper.eq(StringUtils.isNotBlank(scNewsInfo.getOrgId()), "org_id", scNewsInfo.getOrgId());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;

    }

    /**
     * 根据资讯分组id查询
     *
     * @param newsGroupId 资讯分组id
     * @return
     */
    @Override
    public List<ScNewsInfo> selectByNewGroupId(String newsGroupId) {
        QueryWrapper<ScNewsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("news_group_id", newsGroupId);
        queryWrapper.orderByAsc("sort_id");
        return this.newsInfoMapper.selectList(queryWrapper);
    }
}