package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 作业管理对象 t_sc_schoolwork
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_schoolwork")
public class ScSchoolwork extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 作业主Id
     */
    @TableId(value = "school_work_id", type = IdType.AUTO)
    private String schoolWorkId;

    /**
     * 作业标题
     */
    @Excel(name = "作业标题")
    private String title;

    /**
     * 作业文字内容
     */
    @Excel(name = "作业文字内容")
    private String textcontent;

    /**
     * 作业多图片列表
     */
    @Excel(name = "作业多图片列表")
    private String picurls;

    /** 作业图片地址集合 */
    @TableField(exist = false)
    private List<String> pList;

    /**
     * 学校、组织机构
     */
    @Excel(name = "学校、组织机构")
    private String orgId;

    /**
     * 班级
     */
    @Excel(name = "班级")
    private String classId;

    /**
     * 发布者
     */
    @Excel(name = "发布者")
    private String publisherId;

    /**
     * 是否需要确认反馈
     */
    @Excel(name = "是否需要确认反馈")
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
     * $column.columnComment
     */
    @Excel(name = "创建时间")
    private Integer createDate;

    /**
     * 发布通知时间
     */
    @Excel(name = "发布通知时间")
    private Integer publishDate;

    /**
     * $column.columnComment
     */
    @Excel(name = "最后修改时间时间")
    private Integer modifyDate;

    /**
     * 状态 0未确认(草稿),1已经发布 2,推送中 3推送成功
     */
    @Excel(name = "状态")
    private Integer stateMark;

    /**
     * 发布者姓名
     */
    @Excel(name = "发布者姓名")
    private String publisherName;

    /**
     * 学校名
     */
    @TableField(exist = false)
    private String orgName ;

    /**
     * 班别
     */
    @TableField(exist = false)
    private String className ;
}
