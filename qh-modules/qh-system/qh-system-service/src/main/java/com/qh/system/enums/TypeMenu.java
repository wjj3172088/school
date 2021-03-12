package com.qh.system.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/26 17:05
 * @Description:
 */
public enum TypeMenu {
    /**
     * 是
     */
    YES(0, "是"),
    /**
     * 否
     */
    NO(1, "否");


    private int code;
    private String value;

    TypeMenu(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
