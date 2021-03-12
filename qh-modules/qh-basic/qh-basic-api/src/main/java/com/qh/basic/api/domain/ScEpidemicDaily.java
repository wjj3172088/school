package com.qh.basic.api.domain;

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
 * 疫情日报对象 t_sc_epidemic_daily
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_epidemic_daily")
public class ScEpidemicDaily extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 主Id */
    @TableId(value = "daily_id", type = IdType.ID_WORKER_STR)
    private String dailyId;

    /** 发布者Id */
    private String publisherId;

    /** 发布者姓名 */
    @Excel(name = "发布者姓名")
    private String publisherName;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer createDate;

    /** 修改时间 */
    private Integer modifyDate;

    /** 图片路径 */
    private String picurl;

    /** 文件路径 */
    private String fileurl;

    /** 填报状态 */
    @Excel(name = "填报状态",readConverterExp = "0=未填报,1=已填报" ,defaultValue = "未填报")
    private Integer stateMark;

    /** 所属学校Id */
    private String orgId;

    /** 所属学校名称 */
    @Excel(name = "所属学校名称")
    private String orgName;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
}
