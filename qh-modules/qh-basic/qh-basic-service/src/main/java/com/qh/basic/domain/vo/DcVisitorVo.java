package com.qh.basic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/14 15:31
 * @Description: 访客信息
 */
@Data
public class DcVisitorVo {
    /**
     * 访客姓名
     */
    private String xm;

    /**
     * 访客头像
     */
    private String picurl;

    /**
     * 性别(0：未知 1：男 2：女)
     */
    private String xbdm;

    /**
     * 身份证号码
     */
    private String gmsfhm;

    /**
     * 到访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
