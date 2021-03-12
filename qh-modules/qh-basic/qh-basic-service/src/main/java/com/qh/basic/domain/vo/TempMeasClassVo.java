package com.qh.basic.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * 体温测量按班级统计Vo
 * 
 * @author huangdaoquan
 * @date 2021-01-03
 */
@Data
public class TempMeasClassVo  {

    /** 学校Id */
    private String orgId;

    /** 班级 */
    @Excel(name = "学校")
    private String orgName;

    /** 班级Id */
    private String classId;

    /** 班级 */
    @Excel(name = "班级")
    private String className;

    /** 测温日期 */
    @Excel(name = "测温日期")
    private String recordTime;


    /** 班级总人数 */
    @Excel(name = "班级总人数")
    private Integer classTotalCount;

    /** 班级测温总人数 */
    @Excel(name = "测温人数")
    private Integer classMeasCount;

    /** 班级测温异常总人数 */
    @Excel(name = "体温异常数")
    private Integer classAbnormalCount;



}
