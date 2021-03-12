package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/23 11:20
 * @Description: 用户类型
 */
public enum AccTypeEnum {
    /**
     * 校长
     */
    PRINCIPAL("R", "校长"),

    /**
     * 老师
     */
    TEACHER("T", "老师"),

    /**
     * 家长
     */
    PARENT("P", "家长"),

    /**
     * 职工
     */
    STAFF("S", "职工");

    private String code;
    private String name;

    private AccTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.code;
    }
}
