package com.qh.basic.model.response.graduate;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/5 17:40
 * @Description: 毕业信息
 */
@Data
public class GraduateInfoResponse {
    /**
     * 毕业年份
     */
    private String year;

    /**
     * 毕业人数
     */
    private Integer graduateCount;
}
