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
 * 毕业详情对象 t_sc_graduate_detail
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_graduate_detail")
public class ScGraduateDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生id
     */
    private String stuId;

    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名")
    private String stuName;

    /**
     * 学生身份证
     */
    @Excel(name = "学生身份证")
    private String idCard;

    /**
     * 学号
     */
    private String stuNum;

    /**
     * 学生卡ID
     */
    @Excel(name = "学生卡ID")
    private String tagNum;

    /**
     * 学校id
     */
    private String orgId;

    /**
     * 学校名称
     */
    private String orgName;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 学生班级
     */
    @Excel(name = "学生班级")
    private String className;

    /**
     * 家长姓名
     */
    private String guardianName;

    /**
     * 家长手机号
     */
    @Excel(name = "家长手机号")
    private String guardianMobile;

    /**
     * 亲属关系
     */
    private Integer guardianRelation;

    /**
     * 亲属关系名称
     */
    @Excel(name = "亲属关系")
    private String guardianRelationName;

    /**
     * 毕业档期
     */
    @Excel(name = "毕业档期")
    private Integer year;

    /**
     * 老师id
     */
    private String teacherId;

    /**
     * 班主任姓名
     */
    @Excel(name = "班主任姓名")
    private String teacherName;

    /**
     * 班主任手机号
     */
    @Excel(name = "班主任手机号")
    private String teacherMobile;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer modifyDate;
}
