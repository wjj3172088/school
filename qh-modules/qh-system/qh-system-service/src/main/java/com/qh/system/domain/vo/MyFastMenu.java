package com.qh.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/26 16:35
 * @Description: 我的快捷菜单
 */
@Data
public class MyFastMenu {
    /**
     * 是否初始化状态：0：初始化 1：已自定义
     */
    private Integer type;

    /**
     * 快捷菜单列表
     */
    private List<FastMenu> fastMenuList;
}
