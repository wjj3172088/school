package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/9 16:13
 * @Description:
 */
public enum NoticeTargetEnum {
    /**
     * 班级
     */
    CLASS("C", "班级"),

    /**
     * 老师
     */
    TEACHER("T", "老师"),

    /**
     * 职工
     */
    STAFF("S", "职工"),

    /**
     * 职工
     */
    ALL_STUDENT("A", "全校学生");

    private String code;
    private String name;

    private NoticeTargetEnum(String code, String name) {
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
