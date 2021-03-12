package com.qh.basic.service;

import com.qh.basic.domain.ScGraduateDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.StudentExtendVo;
import com.qh.system.api.domain.SysDictData;

import java.util.List;

/**
 * 毕业详情Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
public interface IScGraduateDetailService extends IService<ScGraduateDetail> {
    /**
     * 查询毕业详情集合
     *
     * @param page             分页信息
     * @param scGraduateDetail 操作毕业详情对象
     * @return 操作毕业详情集合
     */
    IPage<ScGraduateDetail> selectScGraduateDetailListByPage(IPage<ScGraduateDetail> page, ScGraduateDetail scGraduateDetail);

    /**
     * 查询导出
     *
     * @param scGraduateDetail 导出请求
     * @return
     */
    List<ScGraduateDetail> selectExport(ScGraduateDetail scGraduateDetail);

    /**
     * 新增毕业详情
     *
     * @param studentExtendList 毕业学校列表
     * @param guarRelationList 亲属关系字典列表
     */
    void add(List<StudentExtendVo> studentExtendList, List<SysDictData> guarRelationList);
}
