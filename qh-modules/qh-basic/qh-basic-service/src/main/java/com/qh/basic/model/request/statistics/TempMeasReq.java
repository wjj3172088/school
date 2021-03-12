package com.qh.basic.model.request.statistics;

import lombok.Data;

/**
 * 体温测量入参
 * 
 * @author huangdaoquan
 * @date 2021-01-03
 */
@Data
public class TempMeasReq {

    /** 学校Id */
    private String orgId;

    /** 班级Id */
    private String classId;


    /**
     * 最大体温上限
     */
    private double max;

    /**
     * 检测日期
     */
    private String recordTime;

    /**
     * 开始时间
     */
    private String recordTimeStart;

    /**
     * 结束时间
     */
    private String recordTimeEnd;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 标签卡
     */
    private String tagNum;

    /**
     * 体温 1:异常
     */
    private Integer temperature;


}
