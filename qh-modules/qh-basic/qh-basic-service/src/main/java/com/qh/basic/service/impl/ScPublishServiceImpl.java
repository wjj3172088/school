package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScPublishMapper;
import com.qh.basic.domain.ScPublish;
import com.qh.basic.service.IScPublishService;

import java.util.Date;

/**
 * 公告模板Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-24
 */
@Service
public class ScPublishServiceImpl extends ServiceImpl<ScPublishMapper, ScPublish> implements IScPublishService {
    @Autowired
    private ScPublishMapper publishMapper;

    /**
     * 查询公告模板集合
     *
     * @param scPublish 操作公告模板对象
     * @return 操作公告模板集合
     */
    @Override
    public IPage<ScPublish> selectScPublishListByPage(IPage<ScPublish> page, ScPublish scPublish) {
        scPublish.setOrgId(SecurityUtils.getOrgId());
        return this.page(page, getQuery(scPublish));
    }

    /**
     * 新增
     *
     * @param publish 公告模板
     * @return 公告模板id
     */
    @Override
    public int add(ScPublish publish) {
        boolean dbExist = this.existByTitle(publish.getTitle());
        if (dbExist) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该标题");
        }
        String uId = UUIDG.generate();
        publish.setPublishId(uId);
        publish.setOrgId(SecurityUtils.getOrgId());
        publish.setCreateDate(DateUtil.getSystemSeconds());
        publish.setModifyDate(DateUtil.getSystemSeconds());
        return publishMapper.insert(publish);
    }

    /**
     * 修改
     *
     * @param publish 公告模板
     */
    @Override
    public void modify(ScPublish publish) {
        if (publish == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该记录");
        }
        if (StringUtils.isEmpty(publish.getPublishId())) {
            throw new BizException(CodeEnum.NOT_EXIST, "该记录id");
        }
        //校验是否存在不同id，相同的产品名称
        ScPublish dbPublish = this.selectByTitle(publish.getTitle());
        if (dbPublish != null && !dbPublish.getPublishId().equals(publish.getPublishId())) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该标题");
        }

        publish.setModifyDate(DateUtil.getSystemSeconds());
        publishMapper.modify(publish);
    }

    /**
     * 根据标题判断是否存在
     *
     * @param title
     * @return
     */
    private boolean existByTitle(String title) {
        QueryWrapper<ScPublish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(title), "title", title);
        return publishMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据标题判断查询
     *
     * @param title
     * @return
     */
    private ScPublish selectByTitle(String title) {
        QueryWrapper<ScPublish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(title), "title", title);
        return publishMapper.selectOne(queryWrapper);
    }

    /**
     * 查询公告模板参数拼接
     */
    private QueryWrapper<ScPublish> getQuery(ScPublish scPublish) {
        QueryWrapper<ScPublish> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(scPublish.getOrgId()), "org_id", scPublish.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scPublish.getTitle()), "title", scPublish.getTitle());
        queryWrapper.eq(scPublish.getType() != null, "type", scPublish.getType());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}