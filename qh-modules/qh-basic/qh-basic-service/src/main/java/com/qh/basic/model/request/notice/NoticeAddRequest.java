package com.qh.basic.model.request.notice;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/26 16:36
 * @Description: 公告发布新增请求
 */
@Data
public class NoticeAddRequest {
    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @NotBlank(message = "公告内容不能为空")
    private String noticeContent;

    /**
     * 图片列表
     */
    private List<String> noticePictureList;

    /**
     * 公告对象信息列表
     */
    private List<NoticeTarget> noticeTargetList;
}
