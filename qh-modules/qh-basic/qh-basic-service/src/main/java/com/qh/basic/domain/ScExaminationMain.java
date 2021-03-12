package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 考试成绩对象 t_sc_examination_main
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_examination_main")
public class ScExaminationMain extends BaseEntity {

private static final long serialVersionUID=1L;


    /** 成绩主ID */
    @TableId(value = "examination_id", type = IdType.AUTO)
    private String examinationId;

    /** 学校ID */
    @Excel(name = "学校ID")
    private String orgId;

    /** 班级ID */
    @Excel(name = "班级ID")
    private String classId;

    /** 科目ID */
    @Excel(name = "科目ID")
    private String subjectId;

    /** 最高分 */
    @Excel(name = "最高分")
    private BigDecimal highest;

    /** 考试时间 */
    @Excel(name = "考试时间")
    private Integer examinationDate;

    /** 考试名称 */
    @Excel(name = "考试名称")
    private String examinationName;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Long createDate;

    private Integer modifyDate;

    /** 平均分 */
    @Excel(name = "平均分")
    private BigDecimal average;

    /** 发布人Id */
    @Excel(name = "发布人Id")
    private String publisherId;

    /**
     * 学校名
     */
    @TableField(exist = false)
    private String orgName ;

    /**
     * 班别
     */
    @TableField(exist = false)
    private String className ;

    /**
     * 科目名
     */
    @TableField(exist = false)
    private String subjectName ;
}
