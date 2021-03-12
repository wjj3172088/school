package com.qh.basic.model.request.technicaldefense;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/25 13:47
 * @Description: 修改请求
 */
@Data
public class ModifyTechnicalDefenseRequest extends AddTechnicalDefenseRequest {
    /**
     * 技防id
     */
    @NotBlank(message = "技防id不能为空")
    private String defenseId;
}
