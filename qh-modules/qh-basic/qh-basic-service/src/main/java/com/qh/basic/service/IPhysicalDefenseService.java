package com.qh.basic.service;

import com.qh.basic.domain.PhysicalDefense;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.PhysicalDefenseExportVo;
import com.qh.basic.model.request.physicaldefense.PhysicalDefenseImportRequest;

import java.util.List;

/**
 * 物防信息Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-22
 */
public interface IPhysicalDefenseService extends IService<PhysicalDefense> {
    /**
     * 查询物防信息集合
     *
     * @param page            分页信息
     * @param physicalDefense 操作物防信息对象
     * @return 操作物防信息集合
     */
    IPage<PhysicalDefense> selectPhysicalDefenseListByPage(IPage<PhysicalDefense> page, PhysicalDefense physicalDefense);

    /**
     * 新增
     *
     * @param physicalDefense 物防信息
     */
    void add(PhysicalDefense physicalDefense);

    /**
     * 修改
     *
     * @param physicalDefense 物防信息
     */
    void modify(PhysicalDefense physicalDefense);

    /**
     * 查询需要导出的数据
     *
     * @param physicalDefense 查询条件
     * @return
     */
    List<PhysicalDefenseExportVo> selectExportList(PhysicalDefense physicalDefense);

    /**
     * 导入
     *
     * @param physicalDefenseImportRequestList 导入信息
     */
    void importData(List<PhysicalDefenseImportRequest> physicalDefenseImportRequestList);
}
