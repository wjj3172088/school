package com.qh.basic.api.model.request.teacher;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 14:45
 * @Description: 教师导出请求
 */
@Data
public class TeacherExportSearchRequest {
    /**
     * 年级
     */
    private String teacName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 学校id
     */
    private String orgId;

    /**
     * 手机号
     */
    private String mobile;
}
