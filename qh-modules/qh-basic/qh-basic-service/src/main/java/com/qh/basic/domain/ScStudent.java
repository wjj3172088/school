package com.qh.basic.domain;

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
 * 学生信息对象 t_sc_student
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_student")
public class ScStudent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 学生Id
     */
    @TableId(value = "stu_id")
    private String stuId;

    /**
     * 性别(M:男，W:女)
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateBirth;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 学号
     */
    private String stuNum;

    /**
     * 学籍号
     */
    private String stuCode;

    /**
     * 标签编号
     */
    private String tagNum;

    /**
     * 监护人姓名
     */
    private String guardianName;

    /**
     * 监护人关系
     */
    private Integer guardianRelation;

    /**
     * 监护人手机
     */
    private String guardianMobile;

    /**
     * 走失状态(Y:正常，L:走失)
     */
    private String awayState;

    /**
     * 状态(S:学校、H:家)
     */
    private String stuState;

    /**
     * 删除标志(Y:正常, N:无效,  D:删除 ，V:未激活)
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
     * 标签电池低电状态(1:是低电 0:不是低电<正常>)
     */
    private Integer lowState;

    /**
     * 是否人脸认证过(0:未认证 1:已认证)
     */
    private Integer faceState;

    /**
     * 机构ID，学校ID
     */
    private String orgId;

    /**
     * 激活时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityDate;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 毕业状态：1：已毕业；2：未毕业
     */
    private Integer graduateType;

    /**
     * 班主任名称
     */
    @TableField(exist = false)
    private String teachName;

    /**
     * 年级
     */
    @TableField(exist = false)
    private Long grade;

    /**
     * 班别
     */
    @TableField(exist = false)
    private String classNum;

    /**
     * 移动账号id
     */
    @TableField(exist = false)
    private String accId;
}
