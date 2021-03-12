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
 * 信息数据申请对象 t_sc_apply
 *
 * @author 汪俊杰
 * @date 2020-12-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_apply")
public class ScApply extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "apply_id", type = IdType.AUTO)
    private Long applyId;

    /**
     * 审核标题，
     */
    private String applyTitle;

    /**
     * 申请大类(1考勤,2加入)
     */
    private Integer applyTag;

    /**
     * 申请的类型，如加入班级、换班等
     */
    private Integer applyType;

    /**
     * 申请状态(0:待审核、1:同意、2:拒绝、3:撤消)
     */
    private Integer applyState;

    /**
     * 对应JAVA对象的JSON格式
     */
    private String applyValue;

    /**
     * 申请人
     */
    private String applyUserId;

    /**
     * 申请人称呼，如xxx家长，xxx父亲
     */
    private String applyAppellation;

    /**
     * 申请人名称
     */
    private String applyName;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 最后修改时间
     */
    private Integer modifyTime;

    /**
     * 审核时间
     */
    private Integer actionTime;

    /**
     * 申请目标 1、班级(班主任) 2、学校(校长或是管理员) 3、个人
     */
    private Integer applyTarget;

    /**
     * 目标值，classId,orgId,acc_num_id等
     */
    private String applyTargetValue;

    /**
     * 学校id
     */
    private String orgId;
}
