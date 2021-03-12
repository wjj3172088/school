package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 16:29
 * @Description:
 */
public enum Status {

    /**
     * 正常
     */
    normal("Y", "正常"),
    /**
     * 停用
     */
    disenable("N", "停用"),
    /**
     * 删除
     */
    delete("D", "删除"),

    /**
     * 注销状态
     */
    cancel("C", "注销"),

    /**
     * 未开放
     */
    init("O", "未开放");


    private String value;
    private String name;

    private Status(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String value() {
        return value;
    }

    public String getName() {
        return name;
    }
}
