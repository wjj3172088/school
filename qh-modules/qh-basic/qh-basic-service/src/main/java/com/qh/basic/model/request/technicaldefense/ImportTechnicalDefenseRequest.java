package com.qh.basic.model.request.technicaldefense;

import com.qh.common.core.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/25 13:47
 * @Description: 导入请求
 */
@Data
public class ImportTechnicalDefenseRequest {
    /**
     * 器械类型名称
     */
    @Excel(name = "器械类型")
    private String defenseTypeName;

    /**
     * 器械名称
     */
    @Excel(name = "器械名称")
    private String name;

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
    private String price;

    /**
     * 配置时间
     */
    @Excel(name = "配置时间")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date configTime;

    /**
     * 质保时间
     */
    @Excel(name = "质保时间")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date expireTime;

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
