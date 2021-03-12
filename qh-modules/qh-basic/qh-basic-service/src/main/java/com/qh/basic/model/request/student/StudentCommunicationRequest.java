package com.qh.basic.model.request.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/26 09:09
 * @Description: 通讯录请求
 */
@Data
public class StudentCommunicationRequest {
    /**
     * 姓名或手机号
     */
    private String paraValue;

    /**
     * 班级id
     */
    @NotBlank(message = "班级不能为空")
    private String classId;
}
