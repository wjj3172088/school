package com.qh.basic.model.request.notice;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/14 14:51
 * @Description: 已读未读人员请求
 */
@Data
public class NoticeViewRequest {
    /**
     * 公告id
     */
    @NotBlank(message = "公告id不能为空")
    private String bizId;

    /**
     * 已读未读
     */
    private Integer look;
}
