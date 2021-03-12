package com.qh.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.qh.common.core.annotation.Excel;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.qh.common.core.web.domain.BaseEntity;

/**
 * 班级教师关联对象 t_sc_teac_class
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_teac_class")
public class ScTeacClass extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 班级教师Id
     */
    @TableId(value = "tc_id")
    private String tcId;

    /**
     * 教师Id
     */
    @Excel(name = "教师Id")
    private String teacId;

    /**
     * 班级Id
     */
    @Excel(name = "班级Id")
    private String classId;

    /**
     * 是否班主任（Y:是，N:否）
     */
    @Excel(name = "是否班主任", readConverterExp = "Y=:是，N:否")
    private String direct;

    /**
     * 申请日期
     */
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 默认显示
     */
    @Excel(name = "默认显示")
    private Integer def;

    /**
     * 学科ID
     */
    @Excel(name = "学科ID")
    private String subjectId;

    /**
     * 学科名称，此处为冗余数据
     */
    @Excel(name = "学科名称，此处为冗余数据")
    private String subjectName;
}
