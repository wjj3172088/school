package com.qh.system.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/28 11:05
 * @Description: 操作类型枚举
 */
public enum OperStatusEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 异常
     */
    ERROR(0, "异常");


    private int code;
    private String value;

    OperStatusEnum(int code, String value) {
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
