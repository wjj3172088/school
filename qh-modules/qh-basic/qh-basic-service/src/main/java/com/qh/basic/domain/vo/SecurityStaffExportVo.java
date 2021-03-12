package com.qh.basic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/21 14:58
 * @Description: 三防保安导出
 */
@Data
public class SecurityStaffExportVo {
    /**
     * 保安姓名
     */
    @Excel(name = "保安姓名")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sexName;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;

    /**
     * 身份证
     */
    @Excel(name = "身份证")
    private String idCard;

    /**
     * 学历
     */
    @Excel(name = "学历")
    private String educationTypeName;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯")
    private String nativePlace;

    /**
     * 保安证编号
     */
    @Excel(name = "保安证编号")
    private String staffNumber;

    /**
     * 保安公司
     */
    @Excel(name = "保安公司")
    private String company;

    /**
     * 政治面貌
     */
    @Excel(name = "政治面貌")
    private String politicalFaceName;

    /**
     * 聘用性质
     */
    @Excel(name = "聘用性质")
    private String recruitTypeName;

    /**
     * 从事保安工作时间
     */
    @Excel(name = "从事保安工作时间")
    private Long staffYear;

    /**
     * 聘用日期
     */
    @Excel(name = "聘用日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workTime;

    /**
     * 合同有效期(年)
     */
    @Excel(name = "合同有效期(年)")
    private Long contractExpire;
}
