package com.qh.basic.model.request.student;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/30 13:19
 * @Description: 学生导入请求
 */
@Data
public class StudentImportRequest {
    /**
     * 学生卡ID/标签号
     */
    @Excel(name = "学生卡ID")
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
     * 亲属关系
     */
    @Excel(name = "亲属关系")
    private String guardianRelation;

    /**
     * 家长手机号
     */
    @Excel(name = "家长手机号")
    private String guardianMobile;

    /**
     * 年级
     */
    @Excel(name = "年级")
    private String gradeName;

    /**
     * 班级
     */
    @Excel(name = "班级")
    private String className;
}
