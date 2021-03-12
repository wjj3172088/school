package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 16:01
 * @Description:
 */
public enum MsgCommand {

    /**短信签名*/
    SIGNATURE("【平安校园】"),

    CODE(" 您本次操作的验证码为：? ，请于5分钟内输入。如非您本人操作，请忽略该短信。"),

    HOME_HINT("?您好，?同学于 ? 在家附近！"),


    TEAC_CHECK_RESULT(" ? 到校情况：到校 ? 人，未到 ? 人，请假 ? 人。"),

    TEAC_LEAVE_RESULT(" ? 离校情况：已离校 ? 人，未离校 ? 人，请假 ? 人。"),

    TEAC_CHECK_TIP("老师您好，您今天还未确认学生到校情况，点击进入早上到校。"),

    TEAC_ASK_LEAVE("?提交了请假申请，点击查看详细信息。"),

    TEAC_ASK_JOIN("?申请加入班级，请点击进行审批。"),

    MASTER_ASK_JOIN_AGREE("您好！?校长同意您加入?，请查收"),
    MASTER_ASK_JOIN_NOAGREE("您好！?校长没有同意您加入?，请查收"),

    //	PAR_ASK_JOIN_AGREE("?您好，班主任已同意您的加入申请"),
    //您好！老师姓名+老师同意/不同意学生姓名加入班级名称（例如六年级（1）班），请查收
    PAR_ASK_JOIN_AGREE("您好！?老师同意?加入?，请查收"),

    //	PAR_ASK_JOIN_NOAGREE("?您好，班主任已拒绝您的加入申请"),
    //您好！老师姓名+老师同意/不同意学生姓名加入班级名称（例如六年级（1）班），请查收2020-07-01 17:35
    PAR_ASK_JOIN_NOAGREE("您好！?老师不同意?加入?，请查收"),

    //	STU_ATTEND_NORMAL("?您好，?同学今天 ? 到校。"),
//	您的孩子学生姓名2020-07-01 08:35已到达校园，请放心。
    STU_ATTEND_NORMAL("您的孩子? ? 已到达校园，请放心。"),


    STU_ATTEND_UNNORMAL("?您好，?同学今天 ? 到校考勤异常，请联系班主任了解情况。"),

    //	您的孩子学生姓名2020-07-01 17:35已离开校园，请放心
//	STU_LEAVE_NORMAL("?您好，?同学今天 ? 离校。"),
    STU_LEAVE_NORMAL("您的孩子? ?已离开校园，请放心。"),

    STU_LEAVE_UNNORMAL("?您好，?同学未离校。"),

    STU_ASK_LEAVE("?您好，您提交的请假申请，班主任已同意。"),

    STU_ASK_LEAVE_RETUEN("?您好，您提交的请假申请，班主任已拒绝，如有疑问请与班主任联系。"),

    TO_JSON("?"),

    //ALERT_MSG("?您的孩子在危险区域逗留，点击查看>>"),
    ALERT_MSG("您的孩子? ? 在危险区域附近，请尽快核查！"),
    //您的孩子姓名2020-07-01 17:35在易溺区域（这里是分类：危险区域，易溺区域，娱乐场所等）附近，请尽快核查！
    //ALERT_DANGER_MSG("您的孩子? ? 在易溺区域（这里是分类：危险区域，易溺区域，娱乐场所等）附近，请尽快核查！"),
    //20200529
    //学生姓名+关系，您好！老师姓名+老师发布了今天的作业，请查收2020-07-01 17:35
    SCHOOL_WORK_INFO("?您好！?老师发布了今天的作业，请查收"),
    //20200601 公告家长端
    SCHOOL_NOTICE_STUDENT("?,?"),
    //20200601 公告老师端
    SCHOOL_NOTICE_TEACHER("?老师,?"),

    APPROVE_ASK_JOIN_AGREE("您提交的加入?的申请,?已同意"),
    APPROVE_ASK_JOIN_NOAGREE("您提交的加入?的申请,?不同意"),

    APPROVE_ASK_LEAVE_AGREE("您提交的请假，班主任已同意"),
    APPROVE_ASK_LEAVE_NOAGREE("您提交的请假，班主任不同意");

    private String command;

    private MsgCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
