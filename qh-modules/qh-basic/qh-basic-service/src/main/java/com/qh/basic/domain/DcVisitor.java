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
 * 访客信息对象 t_dc_visitor
 *
 * @author 汪俊杰
 * @date 2021-01-14
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_dc_visitor")
public class DcVisitor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 访客Id
     */
    @TableId(value = "visitor_id", type = IdType.AUTO)
    private String visitorId;

    /**
     * 设备号
     */
    private String sbhm;

    /**
     * 原表号码
     */
    private String ybhm;

    /**
     * 访客姓名
     */
    private String xm;

    /**
     * 性别(0：未知 1：男 2：女)
     */
    private String xbdm;

    /**
     * 身份证号码
     */
    private String gmsfhm;

    /**
     * 出生日期
     */
    private Date csrq;

    /**
     * 标准的民族代码
     */
    private String mzdm;

    /**
     * 住址
     */
    private String dzmc;

    /**
     * 电话号码
     */
    private String dh;

    /**
     * 图片路径url
     */
    private String picurl;

    /**
     * 来访时间
     */
    private Date lfsj;

    /**
     * 离开时间
     */
    private Date lksj;

    /**
     * 被访部门
     */
    private String bfbm;

    /**
     * 被访人员
     */
    private String bfry;

    /**
     * $column.columnComment
     */
    private String orgId;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date modifyDate;
}
