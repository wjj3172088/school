package com.qh.basic.domain.vo;

import lombok.Data;

/**
 * @Author: huangdaoquan
 * @Date: 2021/2/1 15:31
 * @Description: 值班记录中 对带班人和值班人json保存对象
 */
@Data
public class DutyRecordPeopleVo {

    /**
     * 工号/手机号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;
}
