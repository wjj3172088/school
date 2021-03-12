package com.qh.basic.enums;

import lombok.Getter;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/24 15:07
 * @Description:
 */
public enum SchoolApplyEnum {
    /**
     *
     */
    def();

    @Getter
    public enum TagEnum {
        /**
         * 考勤
         */
        ATTENDANCE(1, "考勤"),
        /**
         * 加入
         */
        JOIN(2, "加入");

        private int code;

        private String msg;

        private TagEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum TypeEnum {
        /**
         * 加入班级
         */
        JOIN_CLASS(1, "加入班级"),
        /**
         * 换班
         */
        CHANGE_CLASS(2, "换班"),
        /**
         * 老师加入学校班级
         */
        JOIN_TEACHER(3, "老师加入学校班级");

        private int code;

        private String msg;

        private TypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum StateEnum {
        // 申请状态(0:待审核、1:同意、2:拒绝、3:撤消 99:不存在)
        /**
         * 待审核
         */
        Unreviewed(0, "待审核"),
        /**
         * 同意
         */
        agree(1, "同意"),
        /**
         * 拒绝
         */
        Refuse(2, "拒绝"),
        /**
         * 撤消
         */
        Undo(3, "撤消"),
        /**
         * 不存在
         */
        NoExist(99, "不存在");

        private int code;

        private String msg;

        private StateEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum TargetEnum {
        // 1、班级(班主任) 2、学校(校长或是管理员) 3、个人
        /**
         * 班主任
         */
        Classes(1, "班级(班主任)"),
        /**
         * 学校(校长或是管理员)
         */
        manage(2, "学校(校长或是管理员)"),
        /**
         * 个人
         */
        person(3, "个人");

        private int code;

        private String msg;

        private TargetEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
