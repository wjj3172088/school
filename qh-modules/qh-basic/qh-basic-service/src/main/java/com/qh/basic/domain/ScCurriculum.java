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

import java.util.List;

/**
 * 课程对象 t_sc_curriculum
 * 
 * @author 黄道权
 * @date 2020-11-13
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_curriculum")
public class ScCurriculum extends BaseEntity {

private static final long serialVersionUID=1L;


    /** 课程主ID */
    @TableId(value = "curriculum_id", type = IdType.AUTO)
    private String curriculumId;

    /** 班级ID */
    private String classId;

    /**
     * 班别
     */
    @TableField(exist = false)
    @Excel(name = "班级")
    private String className ;

    /** 所属组织、学校 */
    private String orgId;

    /**
     * 学校名
     */
    @TableField(exist = false)
    @Excel(name = "所属学校")
    private String orgName ;

    /** 课程表图片地址 */
    private String cumiculumPic;


    /** 课程表图片地址集合 */
    @TableField(exist = false)
    private List<String> pList;


    /** 课程表说明 */
    @Excel(name = "课程表说明")
    private String curriculumMark;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer createDate;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer modifyDate;

    /** 课程表状态是否可用 0不可用 1可用 */
    private String stateMark;


}
