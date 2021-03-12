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
 * 物防信息对象 t_sc_physical_defense
 *
 * @author 汪俊杰
 * @date 2021-01-22
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_physical_defense")
public class PhysicalDefense extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "defense_id")
    private String defenseId;

    /**
     * 器械类型
     */
    private Integer defenseType;

    /**
     * 器械类型名称
     */
    private String defenseTypeName;

    /**
     * 器械名称
     */
    private String name;

    /**
     * 配置数量
     */
    private Long count;

    /**
     * 配置时间
     */
    private Long configTime;

    /**
     * 质保时间
     */
    private Long expireTime;

    /**
     * 图片
     */
    private String images;

    /**
     * 所属学校id
     */
    private String orgId;

    /**
     * 所属学校名称
     */
    private String orgName;

    /**
     * 校内位置
     */
    private String location;

    /**
     * 是否可用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer updateDate;
}
