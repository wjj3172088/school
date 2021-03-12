package com.qh.basic.model.request.scclass;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 14:44
 * @Description: 班级查询请求
 */
@Data
public class ClassSearchRequest {
    /**
     * 年级
     */
    private String grade;
    /**
     * 班别
     */
    private String classNum;
    /**
     * 教师名称
     */
    private String teachName;
    /**
     * 学校
     */
    private String orgId;
}
