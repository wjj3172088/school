package com.qh.basic.domain.vo;

import com.qh.basic.domain.ScStudent;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/29 10:12
 * @Description: 教师扩展信息
 */
@Data
public class StudentExtendVo extends ScStudent {
    /**
     * 所属学校名称
     */
    private String orgName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班主任id
     */
    private String teacId;

    /**
     * 班主任名称
     */
    private String teacName;

    /**
     * 班主任手机号
     */
    private String teacMobile;
}
