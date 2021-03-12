package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScNewsGroup;
import com.qh.basic.domain.ScNewsInfo;
import com.qh.basic.mapper.ScNewsGroupMapper;
import com.qh.basic.model.response.newsgroup.NewsGroupDetailReponse;
import com.qh.basic.service.IScNewsGroupService;
import com.qh.basic.service.IScNewsInfoService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 咨询分组Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-18
 */
@Service
public class ScNewsGroupServiceImpl extends ServiceImpl<ScNewsGroupMapper, ScNewsGroup> implements IScNewsGroupService {
    @Autowired
    private IScNewsInfoService newsInfoService;

    /**
     * 查询咨询分组集合
     *
     * @param page        分页信息
     * @param scNewsGroup 操作咨询分组对象
     * @return 操作咨询分组集合
     */
    @Override
    public IPage<ScNewsGroup> selectScNewsGroupListByPage(IPage<ScNewsGroup> page, ScNewsGroup scNewsGroup) {
        return this.page(page, getQuery(scNewsGroup));
    }

    /**
     * 新增
     *
     * @param type         资讯类型
     * @param newsInfoList 资讯集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Integer type, List<ScNewsInfo> newsInfoList) {
        if (CollectionUtils.isEmpty(newsInfoList)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "资讯");
        }
        List<ScNewsInfo> list = newsInfoList.stream().sorted(Comparator.comparing(ScNewsInfo::getSortId)).collect(Collectors.toList());
        String newsGroupId = UUIDG.generate();
        //保存资讯新闻
        String newsData = newsInfoService.save(newsGroupId, list);

        //新增资讯分组信息
        ScNewsInfo newsInfo = list.get(0);
        ScNewsGroup newsGroup = new ScNewsGroup();
        BeanUtils.copyProperties(newsInfo, newsGroup);
        newsGroup.setNewsData(newsData);
        newsGroup.setType(type);
        newsGroup.setNewsGroupId(newsGroupId);
        newsGroup.setOrgId(SecurityUtils.getOrgId());
        newsGroup.setCreateDate(DateUtil.getSystemSeconds());
        newsGroup.setModifyDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(newsGroup);
    }

    /**
     * 修改
     *
     * @param newsGroupId  资讯分组id
     * @param type         资讯类型
     * @param newsInfoList 资讯集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(String newsGroupId, Integer type, List<ScNewsInfo> newsInfoList) {
        if (CollectionUtils.isEmpty(newsInfoList)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "资讯");
        }
        List<ScNewsInfo> list = newsInfoList.stream().sorted(Comparator.comparing(ScNewsInfo::getSortId)).collect(Collectors.toList());
        //保存资讯新闻
        String newsData = newsInfoService.save(newsGroupId, list);

        ScNewsGroup dbNewsGroup = super.baseMapper.selectById(newsGroupId);
        if (dbNewsGroup == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该资讯分组");
        }
        if (dbNewsGroup.getType() != null && dbNewsGroup.getType() == 2 && type == 1) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "已经发布不能修改为草稿箱");
        }

        ScNewsInfo newsInfo = list.get(0);
        ScNewsGroup newsGroup = new ScNewsGroup();
        BeanUtils.copyProperties(newsInfo, newsGroup);
        newsGroup.setType(type);
        newsGroup.setNewsGroupId(newsGroupId);
        newsGroup.setNewsData(newsData);
        newsGroup.setModifyDate(DateUtil.getSystemSeconds());
        super.baseMapper.updateById(newsGroup);
    }

    /**
     * 删除
     *
     * @param newsGroupIdList 资讯分组id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> newsGroupIdList) {
        for (String newsGroupId : newsGroupIdList) {
            newsInfoService.batchDelete(newsGroupId);
        }
        super.baseMapper.deleteBatchIds(newsGroupIdList);
    }

    /**
     * 根据资讯分组id查询
     *
     * @param newsGroupId 资讯分组id
     * @return
     */
    @Override
    public NewsGroupDetailReponse selectByNewGroupId(String newsGroupId) {
        List<ScNewsInfo> list = newsInfoService.selectByNewGroupId(newsGroupId);
        ScNewsGroup newsGroup = super.baseMapper.selectById(newsGroupId);

        NewsGroupDetailReponse reponse = new NewsGroupDetailReponse();
        reponse.setNewsGroup(newsGroup);
        reponse.setNewsInfoList(list);
        return reponse;
    }

    /**
     * 查询咨询分组参数拼接
     */
    private QueryWrapper<ScNewsGroup> getQuery(ScNewsGroup scNewsGroup) {
        QueryWrapper<ScNewsGroup> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(scNewsGroup.getTitle()), "title", scNewsGroup.getTitle());
        queryWrapper.eq(scNewsGroup.getType() != null, "type", scNewsGroup.getType());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}