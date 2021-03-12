package com.qh.basic.model.request.physicaldefense;

import com.qh.common.core.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/23 11:21
 * @Description:
 */
@Data
public class PhysicalDefenseImportRequest {
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
     * 配置数量
     */
    @Excel(name = "配置数量")
    private Long count;

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
     * 校内位置
     */
    @Excel(name = "校内位置")
    private String location;

    /**
     * 是否可用
     */
    @Excel(name = "是否可用")
    private String enableName;
}
