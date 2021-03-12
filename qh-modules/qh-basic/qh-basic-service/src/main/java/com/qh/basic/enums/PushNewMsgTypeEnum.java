package com.qh.basic.enums;

import lombok.Getter;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 16:28
 * @Description:
 */
public enum PushNewMsgTypeEnum {
    /**
     * 添加时间
     */
    ADD_TIME(1,"添加时间"),
    /**
     * 添加签名
     */
    ADD_SIGN(2,"添加签名"),
    /**
     * 添加时间和签名
     */
    ADD_TIME_AND_SIGN(3,"添加时间和签名");
    @Getter
    private int code;
    @Getter
    private String message;

    @Override
    public String toString() {
        return this.name() + "(" + this.code + "," + this.message + ")";
    }

    /**
     * 根据code获取message
     * @param code
     * @return
     */
    public static String getMessage(int code) {
        // 通过enum.values()获取所有的枚举值
        for (PushNewMsgTypeEnum codeAndMessage : PushNewMsgTypeEnum.values()) {
            // 通过enum.get获取字段值
            if (codeAndMessage.getCode() == code) {
                return codeAndMessage.message;
            }
        }
        return null;
    }

    /**
     * 根据code获取CodeAndMessage
     * @param code
     * @return
     */
    public static PushNewMsgTypeEnum getCodeAndMessage(int code) {
        for (PushNewMsgTypeEnum codeAndMessage : PushNewMsgTypeEnum.values()) {
            if (codeAndMessage.getCode() == code) {
                return codeAndMessage;
            }
        }
        return null;
    }

    private PushNewMsgTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
