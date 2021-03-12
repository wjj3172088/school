package com.qh.basic.model.request.staffinfo;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/24 08:46
 * @Description:
 */
@Data
public class StaffInfoImportRequest {
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Excel(name = "姓名")
    private String trueName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 职称(2:保安、3:保洁、4:食堂员工)
     */
    @NotBlank(message = "职称不能为空")
    @Excel(name = "职称")
    private String jobTitleName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 是否有健康证
     */
    @Excel(name = "是否有健康码")
    private String healthCertificate;

    /**
     * 机动车牌号
     */
    @Excel(name = "机动车号码")
    private String motorNum;

    /**
     * 非机动车牌号
     */
    @Excel(name = "非机动车号码")
    private String notMotorNum;

    /**
     * 非机动车牌号
     */
    @Excel(name = "机动车标签号")
    private String motorTagNum;

    /**
     * 非机动车牌号
     */
    @Excel(name = "非机动车标签号")
    private String notMotorTagNum;
}
