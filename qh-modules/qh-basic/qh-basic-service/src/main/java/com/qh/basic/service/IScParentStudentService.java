package com.qh.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScParentStudent;
import com.qh.basic.domain.ScStudent;

import java.util.Date;
import java.util.List;

/**
 * 家长学生关系Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
public interface IScParentStudentService extends IService<ScParentStudent> {
    /**
     * 保存家长和学生的关系
     *
     * @param accId      移动账号id
     * @param expireTime 过期时间
     * @param student    学生信息
     */
    void save(String accId, Date expireTime, ScStudent student);

    /**
     * 删除学生和家长的关系
     *
     * @param stuIdList 学生id列表
     */
    void deleteByStuIdList(List<String> stuIdList);

    /**
     * 设置家长的默认孩子
     *
     * @param stuIdList 学生id列表
     */
    void setParentDefaultChild(List<String> stuIdList);
}
