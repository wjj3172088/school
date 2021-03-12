package com.qh.basic.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/25 13:33
 * @Description: 技防导出
 */
@Data
public class TechnicalDefenseExportVo {
    /**
     * 器械名称
     */
    @Excel(name = "器械名称")
    private String name;

    /**
     * 器械类型名称
     */
    @Excel(name = "器械类型")
    private String defenseTypeName;

    /**
     * 器械型号
     */
    @Excel(name = "器械型号")
    private String model;

    /**
     * 器械数量
     */
    @Excel(name = "器械数量")
    private Long count;

    /**
     * 资金投入
     */
    @Excel(name = "资金投入")
    private BigDecimal price;

    /**
     * 配置时间
     */
    @Excel(name = "配置时间", width = 30, dateFormat = "yyyy-MM-dd", dateType = "int")
    private Long configTime;

    /**
     * 质保时间
     */
    @Excel(name = "质保时间", width = 30, dateFormat = "yyyy-MM-dd", dateType = "int")
    private Long expireTime;

    /**
     * 是否异常
     */
    @Excel(name = "是否异常")
    private String abnormalName;

    /**
     * 是否110联网
     */
    @Excel(name = "是否110联网")
    private String connectPublicSecurityName;
}
