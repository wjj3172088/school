package com.qh.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.annotation.Excel.ColumnType;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 字典数据表 sys_dict_data
 * 
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_dict_item")
public class SysDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id", cellType = ColumnType.NUMERIC)
    private Long itemNumId;

    /** 项目Id */
    @Excel(name = "项目Id")
    private String itemId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemName;

    /** 项目值 */
    @Excel(name = "项目值")
    private String itemVal;

    /** 字典代码 */
    private String dictCode;

    /** 字典Id */
    private String dictId;

    /** 删除标志(Y:正常, N:无效,  D:删除) */
    private String stateMark;

    /** 备注 */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String modifyDate;
}
