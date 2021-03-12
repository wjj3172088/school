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
 * 移动账号表对象 t_sc_move_acc
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_move_acc")
public class ScMoveAcc extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 帐户Id
     */
    @TableId(value = "acc_id")
    private String accId;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String accPwd;

    /**
     * 帐户
     */
    @Excel(name = "帐户")
    private String accNum;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;

    /**
     * 真实姓名，老师姓名
     */
    @Excel(name = "真实姓名，老师姓名")
    private String trueName;

    /**
     * 盐
     */
    @Excel(name = " 盐")
    private String salt;

    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String avatar;

    /**
     * 性别(M:男，W:女)
     */
    @Excel(name = "性别(M:男，W:女)")
    private String sex;

    /**
     * 用户类型(R:校长、T:老师、P:家长、S:职工)
     */
    @Excel(name = "用户类型(R:校长、T:老师、P:家长、S:职工)")
    private String accType;

    /**
     * 所属组织
     */
    @Excel(name = "所属组织")
    private String orgId;

    /**
     * 推送别名
     */
    @Excel(name = "推送别名")
    private String alias;

    /**
     * 声音(on:开、off:关)
     */
    @Excel(name = "声音(on:开、off:关)")
    private String voice;

    /**
     * 振动(on:开、off:关)
     */
    @Excel(name = "振动(on:开、off:关)")
    private String vibrate;

    /**
     * 个推设备ID
     */
    @Excel(name = "个推设备ID")
    private String gtCid;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 删除标志(Y:正常, N:无效,  D:删除，V:未激活)
     */
    @Excel(name = "删除标志(Y:正常, N:无效,  D:删除，V:未激活)")
    private String stateMark;

    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间")
    private Integer lastTime;

    /**
     * 激活时间
     */
    @Excel(name = "激活时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityDate;
}
