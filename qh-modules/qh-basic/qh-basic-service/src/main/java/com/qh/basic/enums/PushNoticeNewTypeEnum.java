package com.qh.basic.enums;

import lombok.Getter;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 15:11
 * @Description:
 */
public enum PushNoticeNewTypeEnum {
    //对应tag 60
    NOTICE_TEACHER(61,"老师公告"),
    NOTICE_SCHOOL(62,"学校公告");

    @Getter
    private int code;
    @Getter
    private String message;

    @Override
    public String toString() {
        return this.name() + "(" + this.code + "," + this.message + ")";
    }

    private PushNoticeNewTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
