package com.qh.basic.domain.vo;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/26 21:39
 * @Description:
 */
@Data
public class PushMoveAcc {
    /**
     * 推送别名
     */
    private String alias;
    /**
     * 账号 手机
     */
    private String accNum;
    /**
     * 家长ID
     */
    private String accId;
    /**
     * 学生ID
     */
    private String stuId;
    /**
     * 家长关系
     */
    private String relation;
    /**
     * 学生班级
     */
    private String classId;
    /**
     * 设备ID
     */
    private String gtCid;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 家长关系
     */
    private int relationType;
}
