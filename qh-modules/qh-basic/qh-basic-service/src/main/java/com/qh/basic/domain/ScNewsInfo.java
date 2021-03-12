package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 新闻资讯对象 t_sc_news_info
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_news_info")
public class ScNewsInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 校园资讯主id
     */
    @TableId(value = "publicity_id", type = IdType.ID_WORKER_STR)
    private String publicityId;

    /**
     * 标题
     */
    private String title;

    /**
     * 说明
     */
    private String content;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 图片路径
     */
    private String picurl;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer modifyDate;

    /**
     * 排序id
     */
    private Integer sortId;

    /**
     * 所属机构
     */
    private String orgId;

    /**
     * 资讯分组id
     */
    private String newsGroupId;

    /**
     * 学校名
     */
    @TableField(exist = false)
    private String orgName;


    /**
     * 新闻资讯图片地址集合
     */
    @TableField(exist = false)
    private List<String> pList;
}
