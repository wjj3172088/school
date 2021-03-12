package com.qh.collect.domain;

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
 * 考勤总每日记录对象 t_sc_checkin_attendance
 * 
 * @author huangdaoquan
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_checkin_attendance")
public class ScCheckinAttendance extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** $column.columnComment */
    @TableId(value = "attendance_id", type = IdType.AUTO)
    private Integer attendanceId;

    /** 考勤人ID */
    @Excel(name = "考勤人ID")
    private String recorder;

    /** 考勤人类型  1、学生  2、教职工 */
    @Excel(name = "考勤人类型")
    private Integer recorderType;

    /** 考勤日期  如:20200202 */
    @Excel(name = "考勤日期")
    private Integer attendanceDate;

    /** 上学到校上班时间 */
    @Excel(name = "上学到校上班时间")
    private Long intoTime;

    /** 放学 到家时间 */
    @Excel(name = "离校时间")
    private Long outtoTime;

    /** 考勤状态(1、上学上班  5、放学下班  10、到家) */
    @Excel(name = "考勤状态(")
    private Integer stateMark;

    /** 到家时间 */
    @Excel(name = "到家时间")
    private Long tohomeTime;

    /** 班级 */
    @Excel(name = "班级")
    private String classId;

    /** 学校 */
    @Excel(name = "学校")
    private String orgId;
}
