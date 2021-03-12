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
 * 到校签到对象 t_sc_sign_in
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_sign_in")
public class ScSignIn extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** $column.columnComment */
    @TableId(value = "sign_id", type = IdType.ID_WORKER_STR)
    private String id;

    /** 学生Id */
    private String stuId;

    /** 姓名 */
    @Excel(name = "签到姓名")
    private String stuName;

    /** 发布者Id */
    private String publisherId;

    /** 发布者 */
    @Excel(name = "点名教师")
    private String publisherName;

    /** 创建时间 */
    @Excel(name = "签到时间",dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer createDate;

    /** 修改时间 */
    private Integer modifyDate;

    /** 签到类型 */
    private Integer signType;

    /** 状态 */
    private Integer stateMark;

    /** 所属学校Id */
    private String orgId;

    /** 所属学校名称 */
    @Excel(name = "所属学校")
    private String orgName;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
}
