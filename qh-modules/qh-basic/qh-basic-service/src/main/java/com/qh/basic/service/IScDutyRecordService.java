package com.qh.basic.service;

import com.qh.basic.domain.ScDutyRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 值班上报记录Service接口
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
public interface IScDutyRecordService extends IService<ScDutyRecord> {

    /**
     * 查询值班上报记录集合
     *
     * @param page         分页信息
     * @param scDutyRecord 操作值班上报记录对象
     * @return 操作值班上报记录集合
     */
    IPage<ScDutyRecord> selectScDutyRecordListByPage(IPage<ScDutyRecord> page, ScDutyRecord scDutyRecord);

    /**
     * 查询值班上报记录集合
     *
     * @param scDutyRecord 操作值班上报记录对象
     * @return 操作值班上报记录集合
     */
    List<ScDutyRecord> selectScDutyRecordList(ScDutyRecord scDutyRecord);

}
