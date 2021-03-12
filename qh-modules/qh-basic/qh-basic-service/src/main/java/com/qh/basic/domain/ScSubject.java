package com.qh.basic.domain;

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
 * 学校科目对象 t_sc_subject
 *
 * @author 汪俊杰
 * @date 2020-12-04
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_subject")
public class ScSubject extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 科目id
     */
    @TableId(value = "subject_id")
    private String subjectId;

    /**
     * 科目名称
     */
    @Excel(name = "科目名称")
    private String subjectName;

    /**
     * 学校ID
     */
    @Excel(name = "学校ID")
    private String orgId;

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
