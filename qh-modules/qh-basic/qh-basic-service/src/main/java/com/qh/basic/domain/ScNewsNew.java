package com.qh.basic.domain;

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

import java.util.Date;

/**
 * 消息阅读明细对象 t_sc_news_new
 * 
 * @author 汪俊杰
 * @date 2020-11-27
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_news_new")
public class ScNewsNew extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** Id */
    @TableId(value = "news_id")
    private String newsId;

    /** 是否查阅(0:未查阅、1:已查阅) */
    @Excel(name = "是否查阅(0:未查阅、1:已查阅)")
    private Integer look;

    /** 移动帐户Id */
    @Excel(name = "移动帐户Id")
    private String accId;

    /** 业务Id */
    @Excel(name = "业务Id")
    private String bizId;

    /** 班级id */
    @Excel(name = "班级id")
    private String classId;

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

    /** 推送到具体某个学生用stuId，推送到老师用accId */
    @Excel(name = "推送到具体某个学生用stuId，推送到老师用accId")
    private String targetId;
}
