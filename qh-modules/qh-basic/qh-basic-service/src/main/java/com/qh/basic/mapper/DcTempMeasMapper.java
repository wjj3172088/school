package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.DcTempMeas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScCurriculum;
import com.qh.basic.domain.vo.TempMeasClassVo;
import com.qh.basic.domain.vo.TempMeasStuVo;
import com.qh.basic.model.request.statistics.TempMeasReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 体温测量记录Mapper接口
 *
 * @author huangdaoquan
 * @date 2020-12-31
 */
public interface DcTempMeasMapper extends BaseMapper<DcTempMeas> {

    /**
     * 体温检测按班级统计
     * @param page
     * @param tempMeasReq
     * @return
     */
    IPage<TempMeasClassVo> queryMeasClassList(IPage<Object> page, @Param("tempMeasReq") TempMeasReq tempMeasReq);

    /**
     * 体温检测按学生明细统计
     * @param page
     * @param tempMeasReq
     * @return
     */
    IPage<TempMeasStuVo> queryMeasStuList(IPage<Object> page, @Param("tempMeasReq") TempMeasReq tempMeasReq);

    /**
     * 导出体温检测按学生明细
     * @param tempMeasReq
     * @return
     */
    List<TempMeasStuVo> queryMeasStuList(@Param("tempMeasReq") TempMeasReq tempMeasReq);

}
