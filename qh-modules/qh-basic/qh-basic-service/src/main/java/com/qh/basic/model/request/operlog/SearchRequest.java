package com.qh.basic.model.request.operlog;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/28 15:59
 * @Description:
 */
@Data
public class SearchRequest {
    /**
     * 操作类型：1：升学
     */
    @NotNull(message = "操作类型不能为空")
    private Integer operType;

    /**
     * 操作人姓名
     */
    private String operName;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;
}
