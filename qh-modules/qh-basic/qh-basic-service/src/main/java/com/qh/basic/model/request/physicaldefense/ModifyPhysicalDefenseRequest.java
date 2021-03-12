package com.qh.basic.model.request.physicaldefense;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/22 14:25
 * @Description: 修改请求
 */
@Data
public class ModifyPhysicalDefenseRequest extends AddPhysicalDefenseRequest {
    /**
     * 物防id
     */
    @NotBlank(message = "物防id不能为空")
    private String defenseId;
}
