package com.qh.basic.model.request.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/4 14:08
 * @Description: 修改学生
 */
@Data
public class StudentModifyRequest {
    /**
     * 学生Id
     */
    @NotBlank(message = "学生Id不能为空")
    private String stuId;

    /**
     * 姓名
     */
    @NotBlank(message = "学生姓名不能为空")
    private String stuName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 标签编号
     */
    @NotBlank(message = "学生ID卡不能为空")
    private String tagNum;
}
