package com.qh.basic.model.request.newsinfo;

import com.qh.basic.domain.ScNewsInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/19 16:36
 * @Description: 新增资讯请求
 */
@Data
public class AddNewsInfoRequest {
    /**
     * 资讯类型
     */
    @NotNull(message = "资讯类型不能为空")
    private Integer type;

    /**
     * 资讯集合
     */
    private List<ScNewsInfo> newsInfoList;
}
