package com.qh.system.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/26 11:02
 * @Description: 快捷菜单
 */
@Data
public class FastMenu implements Serializable {
    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空")
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否快捷方式（0是 1否）
     */
    @NotBlank(message = "是否快捷方式不能为空")
    private String fast;

    /**
     * 快捷方式图标
     */
    private String fastIcon;

    /**
     * 快捷方式排序
     */
    @NotNull(message = "快捷方式排序不能为空")
    private Integer fastSort;

    /**
     * 是否为默认快捷方式（0是 1否）
     */
    @NotBlank(message = "是否为默认快捷方式不能为空")
    private String fastDefault;

    /**
     * 是否已设置为快捷方式（0是 1否）
     */
    private String isMyFast;
}
