package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/3 16:54
 * @Description:
 */
public enum SysEnableEnum {
    /**
     * 是
     */
    YES("Y", "是"),

    /**
     * 否
     */
    NO("N", "否");


    private String value;
    private String name;

    private SysEnableEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
