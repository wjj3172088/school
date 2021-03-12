package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/3 17:00
 * @Description: 字典类型
 */
public enum DictTypeEnum {
    /**
     * 授课科目
     */
    SPECIALTY("specialty", "授课科目"),

    /**
     * 教师职称
     */
    JOB_TITLE("jobTitle", "教师职称"),
    /**
     * 年级
     */
    GRADE("grade", "年级"),
    /**
     * 班级
     */
    CLASS_NUM("classNum", "班级"),
    /**
     * 是否班主任
     */
    YES_NO("sys_yes_no", "是否班主任"),
    /**
     * 家属关系
     */
    GUAR_RELATION("guarRelation", "家属关系"),


    /**
     * 系统参数字典
     */
    SYS_PARAM("sysParam", "系统参数字典"),
    /**
     * 政治面貌
     */
    POLITICAL_FACE("political_face", "政治面貌"),
    /**
     * 聘用性质
     */
    RECRUIT_TYPE("recruit_type", "聘用性质"),
    /**
     * 学历
     */
    EDUCATION_TYPE("education_type", "学历"),
    /**
     * 物防器械类型
     */
    PHYSICAL_DEFENSE_TYPE("physical_defense_type", "物防器械类型"),
    /**
     * 技防器械类型
     */
    TECHNICAL_DEFENSE_TYPE("technical_defense_type", "技防器械类型");


    private String value;
    private String name;

    private DictTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
