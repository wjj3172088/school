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
 * 移动用户扩展信息对象 t_sc_move_acc_other
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_move_acc_other")
public class ScMoveAccOther extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 帐户Id
     */
    @TableId(value = "acc_id")
    private String accId;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 身份类型  1：身份证
     */
    @Excel(name = "身份类型  1：身份证")
    private Integer idType;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Long integral;

    /**
     * 机动车牌号
     */
    @Excel(name = "机动车牌号")
    private String motorNum;

    /**
     * 非机动车牌号
     */
    @Excel(name = "非机动车牌号")
    private String notMotorNum;

    /**
     * 机动车标签
     */
    @Excel(name = "机动车标签")
    private String motorTagNum;

    /**
     * 非机动车标签
     */
    @Excel(name = "非机动车标签")
    private String notMotorTagNum;
}
