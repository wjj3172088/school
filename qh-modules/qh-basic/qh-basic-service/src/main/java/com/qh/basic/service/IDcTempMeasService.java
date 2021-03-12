package com.qh.basic.service;

import com.qh.basic.domain.DcTempMeas;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.TempMeasClassVo;
import com.qh.basic.domain.vo.TempMeasStuVo;
import com.qh.basic.model.request.statistics.TempMeasReq;

import java.util.List;

/**
 * 体温测量记录Service接口
 *
 * @author huangdaoquan
 * @date 2020-12-31
 */
public interface IDcTempMeasService extends IService<DcTempMeas> {


    /**
     * 查询体温测量记录集合
     * @param page 分页对象
     * @param dcTempMeas 操作体温测量记录对象
     * @return 操作体温测量记录集合
     */
    IPage<DcTempMeas> selectDcTempMeasListByPage(IPage<DcTempMeas> page, DcTempMeas dcTempMeas);

    /**
     * 体温检测按班级统计
     * @param page
     * @param classId
     * @param recordTime
     * @return
     */
    IPage<TempMeasClassVo> queryMeasClassList(IPage<Object> page, String classId, String recordTime);

    /**
     * 体温检测按学生明细统计
     * @param page
     * @param tempMeasReq
     * @return
     */
    IPage<TempMeasStuVo> queryMeasStuList(IPage<Object> page,TempMeasReq tempMeasReq);



    /**
     * 体温检测按学生明细统计
     * @param tempMeasReq
     * @return
     */
    List<TempMeasStuVo> exportMeasStuList(TempMeasReq tempMeasReq);

}
