package com.qh.basic.api.model.request.staff;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/22 00:27
 * @Description:
 */
@Data
public class StaffInfoSearchRequest {
    /**
     * 姓名
     */
    private String trueName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;
}
