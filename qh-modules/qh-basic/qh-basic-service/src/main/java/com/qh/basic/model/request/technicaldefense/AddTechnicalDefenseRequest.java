package com.qh.basic.model.request.technicaldefense;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/25 13:47
 * @Description: 新增请求
 */
@Data
public class AddTechnicalDefenseRequest {
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
     * 器械型号
     */
    @NotBlank(message = "器械型号不能为空")
    private String model;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    @Min(message = "数量不能小于0", value = 0L)
    @Max(message = "数量不能超过9位数", value = 999999999L)
    private Long count;

    /**
     * 资金投入
     */
    @Digits(message = "数量不能超过9位数,不能超过两位小数", integer = 9, fraction = 2)
    private BigDecimal price;

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
     * 是否异常
     */
    @NotNull(message = "是否异常不能为空")
    private Boolean beAbnormal;

    /**
     * 是否110联网
     */
    @NotNull(message = "是否110联网不能为空")
    private Boolean beConnectPublicSecurity;

    /**
     * 备注
     */
    private String remark;
}
