package com.qh.basic.service;

import com.qh.basic.domain.ScSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学校科目Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-04
 */
public interface IScSubjectService extends IService<ScSubject> {
    /**
     * 根据名称查找
     *
     * @param subjectName 科目名字
     * @return
     */
    ScSubject selectBySubjectName(String subjectName);

    /**
     * 查询当前学校所有的科目
     *
     * @return
     */
    List<ScSubject> selectCurrentAll();

    /**
     * 新增
     *
     * @param subjectName 科目名称
     */
    void add(String subjectName);

    /**
     * 修改
     *
     * @param subjectId   科目id
     * @param subjectName 科目名称
     */
    void modify(String subjectId, String subjectName);

    /**
     * 查询学校科目集合
     *
     * @param page      分页信息
     * @param scSubject 操作学校科目对象
     * @return 操作学校科目集合
     */
    IPage<ScSubject> selectListByPage(IPage<ScSubject> page, ScSubject scSubject);
}
