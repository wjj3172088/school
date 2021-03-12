package com.qh.basic.enums;

import lombok.Getter;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 15:10
 * @Description:
 */
public enum PushNewTagEnum {
    /**
     * 考勤
     */
    ATTENDANCE(10, "考勤"),
    /**
     * 安全
     */
    SAFETY(20, "安全"),
    /**
     * 系统
     */
    SYSTEM(30, "系统"),
    /**
     * 通知
     */
    INFORM(40, "通知"),
    /**
     * 防疫
     */
    NCP(50, "防疫"),
    /**
     * 公告
     */
    NOTICE(60, "公告"),
    /**
     * 审批 注意！！app家长显示用审批消息
     */
    Approve(1, "审批");
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
     *
     * @param code
     * @return
     */
    public static String getMessage(int code) {
        // 通过enum.values()获取所有的枚举值
        for (PushNewTagEnum codeAndMessage : PushNewTagEnum.values()) {
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
    public static PushNewTagEnum getCodeAndMessage(int code) {
        for (PushNewTagEnum codeAndMessage : PushNewTagEnum.values()) {
            if (codeAndMessage.getCode() == code) {
                return codeAndMessage;
            }
        }
        return null;
    }

    private PushNewTagEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
