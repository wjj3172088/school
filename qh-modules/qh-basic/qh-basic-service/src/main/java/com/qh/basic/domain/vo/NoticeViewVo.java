package com.qh.basic.domain.vo;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/14 14:33
 * @Description: 公告已读未读信息
 */
@Data
public class NoticeViewVo {
    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否查阅(0:未查阅、1:已查阅)
     */
    private int look;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 移动账号id
     */
    private String accId;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 名称和关系
     */
    private String concatName;

    /**
     * 手机号
     */
    private String accNum;
}
