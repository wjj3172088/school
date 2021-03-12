package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 班级对象 t_sc_class
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_class")
public class ScClass extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 班级Id
     */
    @TableId(value = "class_id")
    private String classId;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 班别
     */
    @Excel(name = "班别")
    private String classNum;

    /**
     * 学生数量
     */
    private Long stuCount;

    /**
     * 所属组织id
     */
    private String orgId;

    /**
     * 老师邀请码
     */
    private String teacCode;

    /**
     * 班级邀请码
     */
    private String classCode;

    /**
     * 删除标志(Y:正常, N:无效,  D:删除)
     */
    private String stateMark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 第几届学生20200730 4.0版本增加
     */
    private String stage;

    /**
     * 班主任id
     */
    @TableField(exist = false)
    private String teachId;

    /**
     * 年级
     */
    @TableField(exist = false)
    @Excel(name = "年级")
    private String gradeName;

    /**
     * 班主任姓名
     */
    @TableField(exist = false)
    private String teacName;

    /**
     * 班主任手机号
     */
    @TableField(exist = false)
    private String mobile;

    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String className;
}
