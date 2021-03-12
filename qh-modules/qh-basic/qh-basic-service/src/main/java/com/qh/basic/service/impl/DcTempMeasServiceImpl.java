package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.DcTempMeas;
import com.qh.basic.domain.vo.TempMeasClassVo;
import com.qh.basic.domain.vo.TempMeasStuVo;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.mapper.DcTempMeasMapper;
import com.qh.basic.model.request.statistics.TempMeasReq;
import com.qh.basic.service.IDcTempMeasService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体温测量记录Service业务层处理
 *
 * @author huangdaoquan
 * @date 2020-12-31
 */
@Service
public class DcTempMeasServiceImpl extends ServiceImpl<DcTempMeasMapper, DcTempMeas> implements IDcTempMeasService {

    @Autowired
    private RemoteDictDataService remoteDictDataService;

    @Autowired
    private DcTempMeasMapper tempMeasMapper;

    /**
     * 查询体温测量记录集合
     *
     * @param dcTempMeas 操作体温测量记录对象
     * @return 操作体温测量记录集合
     */
    @Override
    public IPage<DcTempMeas> selectDcTempMeasListByPage(IPage<DcTempMeas> page, DcTempMeas dcTempMeas) {
        return this.page(page, getQuery(dcTempMeas));
    }

    /**
     * 查询体温测量记录参数拼接
     */
    private QueryWrapper<DcTempMeas> getQuery(DcTempMeas dcTempMeas) {
        QueryWrapper<DcTempMeas> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(dcTempMeas.getTagNum()), "tag_num", dcTempMeas.getTagNum());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("record_time");
        return queryWrapper;

    }

    /**
     * 体温检测按班级统计
     * @param classId
     * @return
     */
    @Override
    public IPage<TempMeasClassVo> queryMeasClassList(IPage<Object> page, String classId, String recordTime){
        //获取体温异常上限动态属性信息
        R<SysDictData> dictDataResult = remoteDictDataService.getDictDataByItemName(DictTypeEnum.SYS_PARAM.getValue(),"temperatureUp");
        double max = 37.3;
        if (null != dictDataResult && null != dictDataResult.getData() && StringUtils.isNotBlank( dictDataResult.getData().getItemVal()) ) {
            max = Double.parseDouble(dictDataResult.getData().getItemVal());
        }
        TempMeasReq tempMeasReq= new TempMeasReq();
        tempMeasReq.setMax(max);
        tempMeasReq.setClassId(classId);
        tempMeasReq.setOrgId(SecurityUtils.getOrgId());
        if(StringUtils.isNotBlank(recordTime) ){
            tempMeasReq.setRecordTimeStart(recordTime);
            tempMeasReq.setRecordTimeEnd(recordTime+" 23:59");
        }else{
            tempMeasReq.setRecordTimeStart(DateUtils.format(DateUtils.yyyy_MM_dd));
            tempMeasReq.setRecordTimeEnd(DateUtils.format(DateUtils.yyyy_MM_dd)+" 23:59");
        }
        return tempMeasMapper.queryMeasClassList( page,tempMeasReq);
    }

    /**
     * 体温检测按学生明细统计
     * @param page
     * @param tempMeasReq
     * @return
     */
    @Override
    public IPage<TempMeasStuVo> queryMeasStuList(IPage<Object> page,TempMeasReq tempMeasReq){
        //获取体温异常上限动态属性信息
        R<SysDictData> dictDataResult = remoteDictDataService.getDictDataByItemName(DictTypeEnum.SYS_PARAM.getValue(),"temperatureUp");
        double max = 37.3;
        if (null != dictDataResult && null != dictDataResult.getData() && StringUtils.isNotBlank( dictDataResult.getData().getItemVal()) ) {
            max = Double.parseDouble(dictDataResult.getData().getItemVal());
        }
        tempMeasReq.setMax(max);
        tempMeasReq.setOrgId(SecurityUtils.getOrgId());
        if(StringUtils.isNotBlank(tempMeasReq.getRecordTime()) ){
            tempMeasReq.setRecordTimeStart(tempMeasReq.getRecordTime());
            tempMeasReq.setRecordTimeEnd(tempMeasReq.getRecordTime()+" 23:59");
        }
        return tempMeasMapper.queryMeasStuList(page,tempMeasReq);
    }

    /**
     * 体温检测按学生明细统计
     * @param tempMeasReq
     * @return
     */
    @Override
    public List<TempMeasStuVo> exportMeasStuList(TempMeasReq tempMeasReq){
        //获取体温异常上限动态属性信息
        R<SysDictData> dictDataResult = remoteDictDataService.getDictDataByItemName(DictTypeEnum.SYS_PARAM.getValue(),"temperatureUp");
        double max = 37.3;
        if (null != dictDataResult && null != dictDataResult.getData() && StringUtils.isNotBlank( dictDataResult.getData().getItemVal()) ) {
            max = Double.parseDouble(dictDataResult.getData().getItemVal());
        }
        tempMeasReq.setMax(max);
        tempMeasReq.setOrgId(SecurityUtils.getOrgId());
        if(StringUtils.isNotBlank(tempMeasReq.getRecordTime()) ){
            tempMeasReq.setRecordTimeStart(tempMeasReq.getRecordTime());
            tempMeasReq.setRecordTimeEnd(tempMeasReq.getRecordTime()+" 23:59");
        }
        return tempMeasMapper.queryMeasStuList(tempMeasReq);
    }

}