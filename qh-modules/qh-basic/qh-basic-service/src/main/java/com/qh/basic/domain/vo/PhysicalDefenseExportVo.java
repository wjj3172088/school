package com.qh.basic.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/22 17:08
 * @Description: 物防导出
 */
@Data
public class PhysicalDefenseExportVo {
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
    @Excel(name = "配置时间", width = 30, dateFormat = "yyyy-MM-dd", dateType = "int")
    private Long configTime;

    /**
     * 质保时间
     */
    @Excel(name = "质保时间", width = 30, dateFormat = "yyyy-MM-dd", dateType = "int")
    private Long expireTime;

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
