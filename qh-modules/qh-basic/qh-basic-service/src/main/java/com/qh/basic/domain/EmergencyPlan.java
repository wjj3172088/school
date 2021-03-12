package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 应急预案对象 t_sc_emergency_plan
 *
 * @author 汪俊杰
 * @date 2021-01-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_emergency_plan")
public class EmergencyPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "plan_id")
    private String planId;

    /**
     * 预案名称
     */
    private String name;

    /**
     * 所属学校id
     */
    private String orgId;

    /**
     * 所属学校名称
     */
    private String orgName;

    /**
     * 预案类型
     */
    private Integer type;

    /**
     * 预案级别
     */
    private Integer level;

    /**
     * 制定时间
     */
    private Integer planTime;

    /**
     * 制定人
     */
    private String publisherName;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件信息
     */
    private String fileData;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer updateDate;
}
