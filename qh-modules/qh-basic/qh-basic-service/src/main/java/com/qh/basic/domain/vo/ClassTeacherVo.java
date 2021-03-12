package com.qh.basic.domain.vo;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/4 09:45
 * @Description:
 */
@Data
public class ClassTeacherVo {
    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 教师id
     */
    private String teacId;

    /**
     * 年级
     */
    private int grade;

    /**
     * 班级
     */
    private String classNum;
}
