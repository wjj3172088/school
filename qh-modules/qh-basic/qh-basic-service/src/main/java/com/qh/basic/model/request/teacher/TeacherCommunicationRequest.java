package com.qh.basic.model.request.teacher;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/26 14:03
 * @Description:
 */
@Data
public class TeacherCommunicationRequest {
    /**
     * 姓名或手机号
     */
    private String paraValue;

    /**
     * 教师/职工类型 T：教师；S:职工
     */
    private String type;

    /**
     * 子类型：职工根据字典staffJobTitle定义，教师根据字典jobTitle
     */
    private String subType;
}
