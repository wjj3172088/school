package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 操作记录对象 t_sc_oper_log
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_oper_log")
public class ScOperLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属学校id
     */
    @Excel(name = "所属学校id")
    private String orgId;

    /**
     * 所属学校名称
     */
    @Excel(name = "所属学校名称")
    private String orgName;

    /**
     * 操作人Id
     */
    @Excel(name = "操作人Id")
    private String operId;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operName;

    /**
     * 操作内容
     */
    @Excel(name = "操作内容")
    private String operContent;

    /**
     * 操作类型：1：升学
     */
    @Excel(name = "操作类型：1：升学")
    private Integer operType;

    /**
     * 操作人类型：1：后台
     */
    @Excel(name = "操作人类型：1：后台")
    private Integer operUserType;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Integer createDate;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间")
    private Integer modifyDate;
}
