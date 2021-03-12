package com.qh.basic.model.request.visitor;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/15 11:24
 * @Description: 访客查询
 */
@Data
public class VisitorSearchRequest {
    /**
     * 访客姓名
     */
    private String xm;

    /**
     * 身份证号码
     */
    private String gmsfhm;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;
}
