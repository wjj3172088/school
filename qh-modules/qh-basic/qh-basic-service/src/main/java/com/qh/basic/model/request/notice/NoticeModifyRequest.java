package com.qh.basic.model.request.notice;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/3 09:51
 * @Description:
 */
@Data
public class NoticeModifyRequest extends NoticeAddRequest {
    /**
     * 通知id
     */
    @NotBlank(message = "公告id不能为空")
    private String noticeId;
}
