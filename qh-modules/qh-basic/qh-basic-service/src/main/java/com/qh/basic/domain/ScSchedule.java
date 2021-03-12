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

import javax.validation.constraints.NotBlank;

/**
 * 教职工排班对象 t_sc_schedule
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_schedule")
public class ScSchedule extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 排班主Id */
    @TableId(value = "schedule_id", type = IdType.ID_WORKER_STR)
    private String scheduleId;

    /** 班次名称 */
    @Excel(name = "班次名称")
    @NotBlank(message = "班次名称不能为空")
    private String scheduleName;

    /** 发布者Id */
    @Excel(name = "发布者Id")
    private String publisherId;

    /** 发布者 */
    @Excel(name = "发布者")
    private String publisherName;

    /** 星期一 */
    @Excel(name = "星期一")
    private String day1;

    /** 星期二 */
    @Excel(name = "星期二")
    private String day2;

    /** 星期三 */
    @Excel(name = "星期三")
    private String day3;

    /** 星期四 */
    @Excel(name = "星期四")
    private String day4;

    /** 星期五 */
    @Excel(name = "星期五")
    private String day5;

    /** 星期六 */
    @Excel(name = "星期六")
    private String day6;

    /** 星期七 */
    @Excel(name = "星期七")
    private String day7;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer createDate;

    /** 修改时间 */
    private Integer modifyDate;

    /** 状态 */
    @Excel(name = "状态")
    private Integer stateMark;

    /** 所属学校Id */
    @Excel(name = "所属学校Id")
    private String orgId;

    /** 所属学校名称 */
    @Excel(name = "所属学校名称")
    private String orgName;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
}
