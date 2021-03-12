package com.qh.basic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体温测量按学生明细统计Vo
 * 
 * @author huangdaoquan
 * @date 2021-01-03
 */
@Data
public class TempMeasStuVo {

    private String measId;

    /** 学校Id */
    private String orgId;

    /** 学校 */
    @Excel(name = "学校")
    private String orgName;

    /** 班级Id */
    private String classId;

    /** 班级 */
    @Excel(name = "班级")
    private String className;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String stuName;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 标签编号 */
    @Excel(name = "标签编号")
    private String tagNum;

    /** 体温 */
    @Excel(name = "体温")
    private BigDecimal temperature;

    /** 体温状态 */
    @Excel(name = "状态")
    private String state;

    /** 测量时间 */
    @Excel(name = "测量时间" , width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;



}
