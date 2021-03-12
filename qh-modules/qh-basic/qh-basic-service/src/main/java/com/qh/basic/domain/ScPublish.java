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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公告模板对象 t_sc_publish
 *
 * @author 汪俊杰
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_publish")
public class ScPublish extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告模板id
     */
    @TableId(value = "publish_id")
    private String publishId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer modifyDate;

    /**
     * 模板类型
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 状态 1可用 0不可用
     */
    @NotBlank(message = "状态不能为空")
    private String stateMark;

    /**
     * 所属学校
     */
    private String orgId;
}
