package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScGraduate;

/**
 * 毕业总览Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
public interface IScGraduateService extends IService<ScGraduate> {
    /**
     * 查询毕业总览集合
     *
     * @param page       分页信息
     * @param scGraduate 操作毕业总览对象
     * @return 操作毕业总览集合
     */
    IPage<ScGraduate> selectScGraduateListByPage(IPage<ScGraduate> page, ScGraduate scGraduate);

    /**
     * 新增毕业总览信息
     *
     * @param orgId   学校id
     * @param orgName 学校名称
     * @param count   学生人数
     */
    void add(String orgId, String orgName, Long count);
}
