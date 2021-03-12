package com.qh.basic.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/4 15:46
 * @Description: 学生导出
 */
@Data
public class StudentExportVo {
    /**
     * 学生ID
     */
    @Excel(name = "学生ID")
    private String tagNum;

    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名")
    private String stuName;

    /**
     * 学生身份证
     */
    @Excel(name = "学生身份证")
    private String idCard;

    /**
     * 家长姓名
     */
    @Excel(name = "家长姓名")
    private String guardianName;

    /**
     * 家长手机号
     */
    @Excel(name = "家长手机号")
    private String guardianMobile;

    /**
     * 亲属关系
     */
    @Excel(name = "亲属关系")
    private String guardianRelation;

    /**
     * 年级
     */
    @Excel(name = "年级")
    private String className;
}
