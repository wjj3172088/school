package com.qh.basic.api.domain.vo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 13:25
 * @Description:
 */
@Data
public class TeacherExportVo {

    /**
     * 教师Id
     */
    private String teacId;


    /**
     * 工号
     */
    @Excel(name = "工号")
    private Long jobNumber;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String teacName;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;


    /**
     * 专业(1:语文、2:数学、3:英语)
     */
    @Excel(name = "专业")
    private String specialty;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private String integral;

    /**
     * 手机号
     */
    @Excel(name = "机动车牌号")
    private String motorNum;

    /**
     * 手机号
     */
    @Excel(name = "机动车标签")
    private String motorTagNum;

    /**
     * 手机号
     */
    @Excel(name = "非机动车牌号")
    private String notMotorNum;

    /**
     * 手机号
     */
    @Excel(name = "非机动车标签")
    private String notMotorTagNum;
}
