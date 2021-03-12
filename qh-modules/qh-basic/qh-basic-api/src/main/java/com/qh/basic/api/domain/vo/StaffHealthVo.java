package com.qh.basic.api.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: huangdaoquan
 * @Date: 2021/1/29 13:25
 * @Description:
 */
@Data
public class StaffHealthVo {

    /**
     * 教师Id
     */
    private String id;

    /**
     * 工号
     */
    @Excel(name = "工号")
    private Long jobNumber;

    /**
     * 职业
     */
    @Excel(name = "类型")
    private String dutyType;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String userName;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 健康状态
     */
    @Excel(name = "健康状态",readConverterExp = "0=未知,1=绿码,2=黄码,3=红码" ,defaultValue = "未知")
    private Integer healthState;


    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date healthDate;

}
