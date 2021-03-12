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
 * 汉王考勤记录对象 t_hanvon_attendance_log
 * 
 * @author huangdaoquan
 * @date 2020-12-25
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_hanvon_attendance_log")
public class HanvonAttendanceLog extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "log_id", type = IdType.ID_WORKER_STR)
    private Long logId;

    /** 学校 */
    private String orgId;

    /** 班级id */
    private String classId;

    /** 考勤人ID（外键Id） */
    private String recorder;

    /** 考勤人类型  1、学生  2、教职工 */
    private Integer recorderType;

    /** 考勤人工号 */
    @Excel(name = "考勤人工号")
    private String peopleNumber;

    /** 考勤人姓名 */
    @Excel(name = "考勤人姓名")
    private String peopleName;

    /** 打卡类型（face：人脸 card:卡片） */
    @Excel(name = "打卡类型" , readConverterExp = "face=人脸,card=卡片")
    private String attendanceType;

    /** 考勤日期  如:20200202 */
    @Excel(name = "考勤日期")
    private Integer attendanceDate;

    /** 考勤时间 */
    @Excel(name = "考勤时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Long checkTime;

    /** 服务器创建时间 */
    @Excel(name = "服务器创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Long createDate;

    /** 设备Id */
    @Excel(name = "考勤设备号")
    private String deviceId;
}
