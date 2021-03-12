package com.qh.basic.model.request.newsinfo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/18 20:17
 * @Description: 修改资讯请求
 */
@Data
public class ModifyNewsInfoRequest extends AddNewsInfoRequest {
    /**
     * 资讯分组id
     */
    @NotBlank(message = "资讯分组id不能为空")
    private String newsGroupId;
}
