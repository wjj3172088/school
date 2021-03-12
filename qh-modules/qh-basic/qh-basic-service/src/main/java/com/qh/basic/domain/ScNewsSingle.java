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
 * ScNewsSingle对象 t_sc_news_single
 * 
 * @author 汪俊杰
 * @date 2020-11-27
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_news_single")
public class ScNewsSingle extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 消息大类(考勤10, 安全20, 系统30, 通知40) */
    @Excel(name = "消息大类(考勤10, 安全20, 系统30, 通知40)")
    private Integer tag;

    /** 消息小类型(到校,离校到家附近) */
    @Excel(name = "消息小类型(到校,离校到家附近)")
    private Integer newsType;

    /** 消息主题 */
    @Excel(name = "消息主题")
    private String newsTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 业务Id */
    @Excel(name = "业务Id")
    private String bizId;

    /** 班级id */
    @Excel(name = "班级id")
    private String classId;

    /** 图片url */
    @Excel(name = "图片url")
    private String image;

    /** 大图地址 */
    @Excel(name = "大图地址")
    private String bigImage;

    /** 申请日期 */
    @Excel(name = "申请日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 修改日期 */
    @Excel(name = "修改日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /** 机构 */
    @Excel(name = "机构")
    private String orgId;

    /** 发布人 */
    @Excel(name = "发布人")
    private String publisherId;

    /** 发布人名称 */
    @Excel(name = "发布人名称")
    private String publisherName;

    /** 通知可见范围，1：全部可见(校长系统公告、学校公告等) ，2：老师可见(学校或校长针对全部老师发送的公告) 3： 班级可见(作业、班级公告等) 4:特定人员可见(针对发送记录直接产生new_new表) 其他后续再增加 默认0全部不可见 */
    @Excel(name = "通知可见范围，1：全部可见(校长系统公告、学校公告等) ，2：老师可见(学校或校长针对全部老师发送的公告) 3： 班级可见(作业、班级公告等) 4:特定人员可见(针对发送记录直接产生new_new表) 其他后续再增加 默认0全部不可见")
    private Integer visibleLevel;

    /** 目标用户ID,如果此值存在visible_level为4，特定目标 */
    @Excel(name = "目标用户ID,如果此值存在visible_level为4，特定目标")
    private String accId;

    /** 推送到具体某个学生用stuId，推送到老师用accId，在visible_level为4的时候填写 */
    @Excel(name = "推送到具体某个学生用stuId，推送到老师用accId，在visible_level为4的时候填写")
    private String targetId;
}
