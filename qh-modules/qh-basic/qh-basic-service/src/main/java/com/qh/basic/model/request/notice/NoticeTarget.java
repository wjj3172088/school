package com.qh.basic.model.request.notice;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/8 17:17
 * @Description:
 */
@Data
public class NoticeTarget {
    /**
     * 大类型：班级：C;老师：T;职工：S
     */
    private String type;

    /**
     * 小类型：班级id；老师：0；职工：保安：2；保洁：3；食堂员工：4
     */
    private String smallType;

    /**
     * 学生id；老师id；职工id
     */
    private String id;
    /**
     * 关系
     */
    private int relationType;
}
