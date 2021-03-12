package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 咨询分组对象 t_sc_news_group
 *
 * @author 汪俊杰
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_news_group")
public class ScNewsGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "news_group_id")
    private String newsGroupId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型：1：草稿箱 2：已发布
     */
    private Integer type;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 图片路径
     */
    private String picurl;

    /**
     * 所属学校
     */
    private String orgId;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer modifyDate;

    /**
     * 资讯基础信息
     */
    private String newsData;
}
