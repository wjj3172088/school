package com.qh.basic.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/24 15:04
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@Builder
public class JoinClassesBean implements Serializable {
    /**
     * @Fields :serialVersionUID : TODO
     */
    private static final long serialVersionUID = 9128304863817013722L;
    /**
     * 申请学生id
     */
    private String studentId;
    /**
     * 学生名称
     */
    private String studentName;
    /**
     * 原班级
     */
    private String oldClassId;
    /**
     * 目标班级
     */
    private String targetClassId;
    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 原班级名称
     */
    private String oldClassName;

    /**
     * 目标班级名称
     */
    private String targetClassName;
}
