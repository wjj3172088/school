package com.qh.basic.domain.vo;

import com.qh.basic.domain.ScMoveAcc;
import com.qh.basic.domain.ScNotice;
import com.qh.basic.enums.PushNewTagEnum;
import com.qh.basic.enums.PushNoticeNewTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 10:43
 * @Description:
 */
@Data
public class NoticePush {
    private String pushName;

    /**
     * 发布者信息
     */
    private ScMoveAcc moveAcc;

    /**
     * 接收者学生家长信息
     */
    private List<PushMoveAcc> list;

    /**
     * 接收者 老师信息
     */
    private List<PushMoveTeacher> teacherList;

    /**
     * 接受者 职工信息
     */
    private List<PushMoveStaff> staffList;

    /**
     * 通知内容
     */
    private ScNotice notice;

    /**
     * 通知大类
     */
    private PushNewTagEnum tag = PushNewTagEnum.NOTICE;

    /**
     * 通知小类
     */
    private PushNoticeNewTypeEnum newsType;
}
