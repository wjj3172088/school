package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 家长学生关系对象 t_sc_parent_student
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_parent_student")
public class ScParentStudent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 家长学生Id
     */
    @TableId(value = "ps_id")
    private String psId;

    /**
     * 移动帐户Id
     */
    private String accId;

    /**
     * 学生Id
     */
    private String stuId;

    /**
     * 关系ID,关联t_sys_dict_item中item_num_id
     */
    private Integer relation;

    /**
     * 关系值，冗余添加
     */
    private String relationValue;

    /**
     * 默认显示(true/false)
     */
    private Boolean def;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * $column.columnComment
     */
    @Excel(name = "修改日期")
    private String prtAccType;

    /**
     * 账号有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
}
