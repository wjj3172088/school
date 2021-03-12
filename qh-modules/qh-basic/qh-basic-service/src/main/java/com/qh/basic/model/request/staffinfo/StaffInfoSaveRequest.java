package com.qh.basic.model.request.staffinfo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/20 15:42
 * @Description: 职工保存请求
 */
@Data
public class StaffInfoSaveRequest {
    /**
     * 移动端账号id
     */
    private String accId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String trueName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 职称(2:保安、3:保洁、4:食堂员工)
     */
    @NotNull(message = "职称不能为空")
    private Integer jobTitle;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 机动车牌号
     */
    private String motorNum;

    /**
     * 非机动车牌号
     */
    private String notMotorNum;

    /**
     * 非机动车牌号
     */
    private String motorTagNum;

    /**
     * 非机动车牌号
     */
    private String notMotorTagNum;

    /**
     * 人脸图片
     */
    private String faceImage;

    /**
     * 是否有健康证
     */
    private boolean healthCertificate;
}
