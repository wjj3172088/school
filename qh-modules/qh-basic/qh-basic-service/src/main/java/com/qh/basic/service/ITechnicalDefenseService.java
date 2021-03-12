package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.TechnicalDefense;
import com.qh.basic.domain.vo.TechnicalDefenseExportVo;
import com.qh.basic.model.request.technicaldefense.ImportTechnicalDefenseRequest;

import java.util.List;

/**
 * 技防信息Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-25
 */
public interface ITechnicalDefenseService extends IService<TechnicalDefense> {
    /**
     * 查询技防信息集合
     *
     * @param page             分页信息
     * @param technicalDefense 操作技防信息对象
     * @return 操作技防信息集合
     */
    IPage<TechnicalDefense> selectTechnicalDefenseListByPage(IPage<TechnicalDefense> page, TechnicalDefense technicalDefense);

    /**
     * 查询需要导出的数据
     *
     * @param technicalDefense 查询条件
     * @return
     */
    List<TechnicalDefenseExportVo> selectExportList(TechnicalDefense technicalDefense);

    /**
     * 新增
     *
     * @param technicalDefense 技防信息
     */
    void add(TechnicalDefense technicalDefense);

    /**
     * 修改
     *
     * @param technicalDefense 技防信息
     */
    void modify(TechnicalDefense technicalDefense);

    /**
     * 导入
     *
     * @param importTechnicalDefenseRequestList 导入请求
     */
    void importData(List<ImportTechnicalDefenseRequest> importTechnicalDefenseRequestList);
}
