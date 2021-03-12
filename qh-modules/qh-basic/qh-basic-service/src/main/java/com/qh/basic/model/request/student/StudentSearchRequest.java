package com.qh.basic.model.request.student;

import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/23 13:23
 * @Description:
 */
@Data
public class StudentSearchRequest {
    /**
     * 学生名称
     */
    private String stuName;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 标签编号
     */
    private String tagNum;

    /**
     * 监护人手机
     */
    private String guardianMobile;
}
