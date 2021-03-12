package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/28 15:06
 * @Description:
 */
public enum OperUserTypeEnum {
    /**
     * 后台用户
     */
    BACKEND(1, "后台用户");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OperUserTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
