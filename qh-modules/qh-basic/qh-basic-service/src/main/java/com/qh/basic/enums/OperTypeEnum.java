package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/28 14:26
 * @Description:
 */
public enum OperTypeEnum {
    /**
     * 升学
     */
    UPGRADE(1, "升学");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OperTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(int code) {
        for (OperTypeEnum data : OperTypeEnum.values()) {
            // 通过enum.get获取字段值
            if (data.getCode() == code) {
                return data.message;
            }
        }
        return null;
    }
}
