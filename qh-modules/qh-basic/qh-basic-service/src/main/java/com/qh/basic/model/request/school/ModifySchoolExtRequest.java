package com.qh.basic.model.request.school;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/20 15:15
 * @Description:
 */
@Data
public class ModifySchoolExtRequest {
    /**
     * 学校统一社会信用代码
     */
    private String schoolCode;

    /**
     * 位置
     */
    private String location;

    /**
     * 校舍建筑面积（m*）
     */
    private BigDecimal area;

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
    private Long teaNum;

    /**
     * 学生总人数
     */
    private Long stuNum;

    /**
     * 住校生人数
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
}
