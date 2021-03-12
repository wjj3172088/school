package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 毕业总览对象 t_sc_graduate
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_graduate")
public class ScGraduate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属学校id
     */
    private String orgId;

    /**
     * 所属学校名称
     */
    private String orgName;

    /**
     * XX届毕业生
     */
    private Integer year;

    /**
     * 学生人数
     */
    private Long count;

    /**
     * 操作人id
     */
    private String operId;

    /**
     * 操作人姓名
     */
    private String operName;

    /**
     * 操作人类型：1：后台
     */
    private Integer operUserType;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer modifyDate;
}
