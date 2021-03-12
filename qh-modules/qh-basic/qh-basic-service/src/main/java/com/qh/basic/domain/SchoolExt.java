package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 学校信息扩展对象 t_sc_school_ext
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_school_ext")
public class SchoolExt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "se_id")
    private String seId;

    /**
     * 机构Id
     */
    private String orgId;

    /**
     * 学校类型（00:小学、01:初中）
     */
    private String schoolType;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 位置
     */
    private String location;

    /**
     * 百度经度
     */
    private String bdLon;

    /**
     * 百度纬度
     */
    private String bdLat;

    /**
     * 校园占地面积（亩）
     */
    private BigDecimal area;

    /**
     * 放学时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date letOutTime;

    /**
     * 专任教师人数
     */
    private Long teaNum;

    /**
     * 学生总人数
     */
    private Long stuNum;

    /**
     * $column.columnComment
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;

    /**
     * $column.columnComment
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date latestOutTime;

    /**
     * 试用期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date probation;

    /**
     * 学校统一社会信用代码
     */
    private String schoolCode;

    /**
     * 校舍建筑面积（m*）
     */
    private BigDecimal buildArea;

    /**
     * 平安校园等级（0:未评价、1:1A级、2:2A级...）
     */
    private Integer safeLevel;

    /**
     * 是否为县级以上避灾安置点
     */
    private Boolean beCountyAvoidDisaster;

    /**
     * 校长姓名
     */
    private String principalName;

    /**
     * 校长电话
     */
    private String principalMobile;

    /**
     * 分管校领导信息，包括名字和电话
     */
    private String leaderData;

    /**
     * 安全职能部门负责人信息，包括名字和电话
     */
    private String safeData;

    /**
     * 教职工总人数
     */
    private Long teachStaffCount;

    /**
     * 专任教师人数
     */
    private Long residentStuNum;

    /**
     * 是否配备专职保安
     */
    private Boolean beFullTimeSecurityStaff;

    /**
     * 保安配备人数
     */
    private Long securityStaffCount;

    /**
     * 是否实行校园封闭化管理
     */
    private Boolean beCloseManage;

    /**
     * 是否设置一键式紧急报警
     */
    private Boolean beOneTouchAlarm;

    /**
     * 视频监控系统是否达标
     */
    private Boolean beVideoStandard;

    /**
     * 是否与公安联网
     */
    private Boolean beConnectPublicSecurity;

    /**
     * 是否设置护学岗
     */
    private Boolean beSetProtectPost;

    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String orgName;
}
