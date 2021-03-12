package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/26 21:08
 * @Description:
 */
public enum PushStateMarkEnum {
    /**
     * 草稿箱
     */
    UNCONFIRMED(0, "未确认"),
    /**
     * 确认
     */
    CONFIRMED(1, "确认"),
    /**
     * 推送中
     */
    PUSHING(30, "推送中"),
    /**
     * 推送成功
     */
    PUSH_SUCCESS(40, "推送成功");

    // 2,推送中 3推送成功

    private int code;
    private String message;

    /**
     * 获取 bare_field_comment
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置 bare_field_comment
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取 bare_field_comment
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置 bare_field_comment
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.name() + "(" + this.code + "," + this.message + ")";
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessage(int code) {
        // 通过enum.values()获取所有的枚举值
        for (PushStateMarkEnum codeAndMessage : PushStateMarkEnum.values()) {
            // 通过enum.get获取字段值
            if (codeAndMessage.getCode() == code) {
                return codeAndMessage.message;
            }
        }
        return null;
    }

    /**
     * 根据code获取CodeAndMessage
     *
     * @param code
     * @return
     */
    public static PushStateMarkEnum getCodeAndMessage(int code) {
        for (PushStateMarkEnum codeAndMessage : PushStateMarkEnum.values()) {
            if (codeAndMessage.getCode() == code) {
                return codeAndMessage;
            }
        }
        return null;
    }

    private PushStateMarkEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
