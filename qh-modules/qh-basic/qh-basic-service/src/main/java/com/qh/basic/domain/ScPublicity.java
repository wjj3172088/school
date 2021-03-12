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
 * 安全宣传对象 t_sc_publicity
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_publicity")
public class ScPublicity extends BaseEntity {

private static final long serialVersionUID=1L;


    /** 安全宣传主id */
    @TableId(value = "publicity_id", type = IdType.ID_WORKER_STR)
    private String publicityId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 说明 */
    @Excel(name = "说明")
    private String content;

    /** 链接地址 */
    @Excel(name = "链接地址")
    private String url;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String picurl;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 创建时间 */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer createDate;

    /** 修改时间 */
    @Excel(name = "修改时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
    private Integer modifyDate;

    /** 排序id */
    @Excel(name = "排序id")
    private Integer sortId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer stateMark;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String orgId;

    /**
     * 学校名
     */
    @TableField(exist = false)
    private String orgName ;


    /** 安全宣传图片地址集合 */
    @TableField(exist = false)
    private List<String> pList;
}
