package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScSubject;
import com.qh.basic.mapper.ScSubjectMapper;
import com.qh.basic.service.IScSubjectService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学校科目Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-04
 */
@Service
public class ScSubjectServiceImpl extends ServiceImpl<ScSubjectMapper, ScSubject> implements IScSubjectService {
    @Autowired
    private ScSubjectMapper subjectMapper;

    /**
     * 根据名称查找
     *
     * @param subjectName 课程名字
     * @return
     */
    @Override
    public ScSubject selectBySubjectName(String subjectName) {
        QueryWrapper<ScSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(subjectName), "subject_name", subjectName);
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        return super.getOne(queryWrapper);
    }

    /**
     * 查询当前学校所有的科目
     *
     * @return
     */
    @Override
    public List<ScSubject> selectCurrentAll() {
        QueryWrapper<ScSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        return super.list(queryWrapper);
    }

    /**
     * 新增
     *
     * @param subjectName 科目名称
     */
    @Override
    public void add(String subjectName) {
        if (StringUtils.isEmpty(subjectName)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "科目名称");
        }
        ScSubject dbSubject = this.selectByName(subjectName);
        if (dbSubject != null) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该科目名称");
        }
        ScSubject subject = new ScSubject();
        subject.setSubjectId(UUIDG.generate());
        subject.setSubjectName(subjectName);
        subject.setOrgId(SecurityUtils.getOrgId());
        subject.setCreateDate(DateUtil.getSystemSeconds());
        subject.setModifyDate(DateUtil.getSystemSeconds());
        subjectMapper.insert(subject);
    }

    /**
     * 修改
     *
     * @param subjectId   科目id
     * @param subjectName 科目名称
     */
    @Override
    public void modify(String subjectId, String subjectName) {
        ScSubject dbSubject = this.selectByName(subjectName);
        if (dbSubject != null && !dbSubject.getSubjectId().equals(subjectId)) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该科目名称");
        }

        Map<String, Object> map = new HashMap<>(4);
        map.put("subjectId", subjectId);
        map.put("subjectName", subjectName);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("modifyDate", DateUtil.getSystemSeconds());
        subjectMapper.modifyBySubjectIdAndOrgId(map);
    }

    /**
     * 查询学校科目集合
     *
     * @param page      分页信息
     * @param scSubject 操作学校科目对象
     * @return 操作学校科目集合
     */
    @Override
    public IPage<ScSubject> selectListByPage(IPage<ScSubject> page, ScSubject scSubject) {
        return this.page(page, getQuery(scSubject));
    }

    /**
     * 根据
     *
     * @param subjectName 科目名称
     * @return
     */
    private ScSubject selectByName(String subjectName) {
        QueryWrapper<ScSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("subject_name", subjectName);
        return super.getOne(queryWrapper);
    }

    /**
     * 查询学校科目参数拼接
     */
    private QueryWrapper<ScSubject> getQuery(ScSubject scSubject) {
        QueryWrapper<ScSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(scSubject.getSubjectName()), "subject_name", scSubject.getSubjectName());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;

    }
}