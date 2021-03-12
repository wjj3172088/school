package com.qh.basic.model.request.subject;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/24 09:42
 * @Description: 新增科目
 */
@Data
public class AddSubjectRequest {
    /**
     * 科目名称
     */
    @NotBlank(message = "科目名称不能为空")
    private String subjectName;
}
