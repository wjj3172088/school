package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体温测量记录对象 t_dc_temp_meas
 * 
 * @author huangdaoquan
 * @date 2020-12-31
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_dc_temp_meas")
public class DcTempMeas extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "meas_id", type = IdType.AUTO)
    private String measId;

    /** 设备编号 */
    private String deviceCode;

    /** 标签编号 */
    @Excel(name = "标签编号")
    private String tagNum;

    /** 体温 */
    @Excel(name = "体温")
    private BigDecimal temperature;

    /** 测量时间 */
    @Excel(name = "测量时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;
}
