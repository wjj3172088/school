package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/10 14:07
 * @Description:
 */
public enum AttachStateMarkEnum {
    /**
     * 正常
     */
    NORMAL("Y", "正常"),

    /**
     * 注销，删除
     */
    CANCEL("C", "注销，删除");

    private String code;
    private String name;

    private AttachStateMarkEnum(String code, String name) {
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
