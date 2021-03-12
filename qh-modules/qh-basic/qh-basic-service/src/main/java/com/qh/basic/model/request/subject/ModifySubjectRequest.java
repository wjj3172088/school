package com.qh.basic.model.request.subject;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/24 09:15
 * @Description: 修改科目
 */
@Data
public class ModifySubjectRequest extends AddSubjectRequest {
    /**
     * 科目id
     */
    @NotBlank(message = "科目id不能为空")
    private String subjectId;
}
