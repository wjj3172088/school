package com.qh.basic.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 职工信息对象 t_sc_staff_info
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_staff_info")
public class ScStaffInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 职工Id
     */
    @TableId(value = "staff_id")
    private String staffId;

    /**
     * 所属组织
     */
    private String orgId;

    /**
     * 职称(2:保安、3:保洁、4:食堂员工)
     */
    private Integer jobTitle;

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
    private Date createDate;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 人脸图片
     */
    private String faceImage;

    /**
     * 用户ID，t_sc_move_acc acc_id 外键
     */
    private String accId;

    /**
     * 是否有健康证
     */
    private boolean healthCertificate;

    /**
     * 工号
     */
    private Long jobNumber;

    /**
     * 职工姓名（虚拟-数据表中不存在）
     */
    @TableField(exist = false)
    private String staffName;
}
