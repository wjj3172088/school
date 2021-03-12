package com.qh.basic.model.request.statistics;

import com.qh.common.core.web.domain.BaseEntity;

/**
 * @Description: 注册统计入参实体
 * @Author: huangdaoquan
 * @Date: 2020/11/27 14:13
 */
public class RegisterStatisticsReq extends BaseEntity {

    /**
     * 学校Id
     */
    private String orgId;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 班级Name
     */
    private String className;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 搜索条件 学生标签
     */
    private String tagNum;

    /**
     * 激活状态 0:未激活 1：已激活
     */
    private Integer activateState;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getTagNum() {
        return tagNum;
    }

    public void setTagNum(String tagNum) {
        this.tagNum = tagNum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getActivateState() {
        return activateState;
    }

    public void setActivateState(Integer activateState) {
        this.activateState = activateState;
    }
}
