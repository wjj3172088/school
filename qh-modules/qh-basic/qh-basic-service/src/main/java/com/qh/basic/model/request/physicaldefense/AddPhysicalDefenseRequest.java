package com.qh.basic.model.request.physicaldefense;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/22 14:24
 * @Description: 新增请求
 */
@Data
public class AddPhysicalDefenseRequest {
    /**
     * 器械类型
     */
    @NotNull(message = "器械类型不能为空")
    private Integer defenseType;

    /**
     * 器械名称
     */
    @NotBlank(message = "器械名称不能为空")
    private String name;

    /**
     * 配置数量
     */
    @NotNull(message = "配置数量不能为空")
    @Min(message = "配置数量不能小于0", value = 0L)
    @Max(message = "数量不能超过9位数", value = 999999999L)
    private Long count;

    /**
     * 配置时间
     */
    @NotNull(message = "配置时间不能为空")
    private Long configTime;

    /**
     * 质保时间
     */
    @NotNull(message = "质保时间不能为空")
    private Long expireTime;

    /**
     * 图片
     */
    private String images;

    /**
     * 校内位置
     */
    @NotBlank(message = "校内位置不能为空")
    private String location;

    /**
     * 是否可用
     */
    @NotNull(message = "是否可用不能为空")
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;
}
