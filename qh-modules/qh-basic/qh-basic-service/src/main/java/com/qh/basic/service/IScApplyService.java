package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScApply;
import com.qh.basic.domain.ScStudent;

import java.util.List;

/**
 * 信息数据申请Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-24
 */
public interface IScApplyService extends IService<ScApply> {


    /**
     * 查询信息数据申请集合
     *
     * @param page    分页信息
     * @param scApply 操作信息数据申请对象
     * @return 操作信息数据申请集合
     */
    IPage<ScApply> selectScApplyListByPage(IPage<ScApply> page, ScApply scApply);

    /**
     * 新增申请
     *
     * @param targetClassId 转班班级id
     * @param studentList   学生列表
     */
    void add(String targetClassId, List<ScStudent> studentList);
}
