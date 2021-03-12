package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 值班上报记录对象 t_sc_duty_record
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_duty_record")
public class ScDutyRecord extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 值班上报主Id */
    @TableId(value = "record_id", type = IdType.ID_WORKER_STR)
    private String recordId;

    /** 发布者Id */
    private String publisherId;

    /** 联系电话 */
    private String tel;

    /** 巡查时长 */
    private String time;

    /** 巡查时间 */
    @Excel(name = "巡查日期", dateFormat = "yyyy-MM-dd", dateType = "int",height = 40)
    private Integer recordDate;

    /** 修改时间 */
    private Integer modifyDate;

    /** 图片路径 */
    private String picurl;

    /** 值班类别 */
    @Excel(name = "值班类别",readConverterExp = "1=教师值周,2=行政值周,3=其他" ,defaultValue = "其他",height = 40)
    private Integer recordType;

    /** 巡查状况 */
    @Excel(name = "巡查状况",readConverterExp = "0=异常,1=正常" ,defaultValue = "正常",height = 40)
    private Integer stateMark;

    /** 所属学校Id */
    private String orgId;

    /** 所属学校名称 */
    @Excel(name = "所属学校",height = 40)
    private String orgName;

    /** 带班人信息 */
    @Excel(name = "带班人",width =23,height = 40)
    private String leader;

    /** 值班人信息 */
    @Excel(name = "值班人",width =23,height = 40)
    private String dutyStaff;

    /** 巡查情况 */
    @Excel(name = "巡查情况",width =35,height = 40)
    private String remark;

    /** 巡查反馈 */
    @Excel(name = "巡查反馈",width =35,height = 40)
    private String feedback;

    /** 值班员 */
    @Excel(name = "提交人",height = 40)
    private String publisherName;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int",height = 40)
    private Integer createDate;
}
