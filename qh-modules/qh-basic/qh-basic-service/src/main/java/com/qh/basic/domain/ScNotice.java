package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 学校公告记录对象 t_sc_notice
 *
 * @author 汪俊杰
 * @date 2020-11-25
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_notice")
public class ScNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "notice_id")
    private String noticeId;

    /**
     * 公告标题
     */
    @Excel(name = "公告标题")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @Excel(name = "公告内容")
    private String noticeContent;

    /**
     * 公告包含的图片，长度定位500
     */
    @Excel(name = "公告包含的图片，长度定位500")
    private String noticePicurls;

    /**
     * 公告图片列表，易于前端解析
     */
    @TableField(exist = false)
    private List<String> pictureList;

    /**
     * 班级
     */
    @Excel(name = "班级")
    private String classId;

    /**
     * 学校ID
     */
    private String orgId;

    /**
     * 发布人
     */
    private String publisherId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Integer createDate;

    /**
     * 最后修改时间
     */
    private Integer modifyDate;

    /**
     * 状态 0未确认(草稿),1已经发布 2,推送中 3推送成功
     */
    @Excel(name = "状态 0未确认(草稿),1已经发布 2,推送中 3推送成功")
    private Integer stateMark;

    /**
     * 公告目标人群
     * 0、全部可见  2、教师可见
     */
    @Excel(name = "公告目标人群 0、全部可见  2、教师可见")
    private Integer noticeTarget;

    /**
     * 是否需要确认反馈 1需要
     */
    private String ifConfirm;

    /**
     * 需要确认人数
     */
    @Excel(name = "需要确认人数")
    private Integer needCount;

    /**
     * 已经确认人数
     */
    @Excel(name = "已经确认人数")
    private Integer confirmCount;

    /**
     * 未确认人数
     */
    @Excel(name = "未确认人数")
    private Integer unconfirmedCount;

    /**
     * 发布者
     */
    @Excel(name = "发布者")
    private String publisherName;
}
