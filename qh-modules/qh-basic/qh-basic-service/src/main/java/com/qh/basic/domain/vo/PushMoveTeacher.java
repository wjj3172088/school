package com.qh.basic.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/2 10:36
 * @Description:
 */
@Data
@NoArgsConstructor
public class PushMoveTeacher {

    /**
     * 家长ID
     */
    private String accId;
    /**
     * 推送别名
     */
    private String alias;
    /**
     * 账号 手机
     */
    private String accNum;
    /**
     * 班级
     */
    private String classId;
    /**
     * 设备ID
     */
    private String gtCid;
    /**
     * 老师姓名
     */
    private String teacherName;
    /**
     * 老师ID
     */
    private String teacherId;

    private String orgId;

    @Generated("SparkTools")
    private PushMoveTeacher(Builder builder) {
        this.accId = builder.acc_id;
        this.alias = builder.alias;
        this.accNum = builder.acc_num;
        this.classId = builder.class_id;
        this.gtCid = builder.gt_cid;
        this.teacherName = builder.teacher_name;
        this.teacherId = builder.teacher_id;
        this.orgId = builder.org_id;
    }
    /**
     * Creates builder to build {@link PushMoveTeacherBean}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }
    /**
     * Builder to build {@link PushMoveTeacherBean}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private String acc_id;
        private String alias;
        private String acc_num;
        private String class_id;
        private String gt_cid;
        private String teacher_name;
        private String teacher_id;
        private String org_id;

        private Builder() {
        }

        public Builder Acc_id(String acc_id) {
            this.acc_id = acc_id;
            return this;
        }

        public Builder Alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder Acc_num(String acc_num) {
            this.acc_num = acc_num;
            return this;
        }

        public Builder Class_id(String class_id) {
            this.class_id = class_id;
            return this;
        }

        public Builder Gt_cid(String gt_cid) {
            this.gt_cid = gt_cid;
            return this;
        }

        public Builder Teacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
            return this;
        }

        public Builder Teacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
            return this;
        }

        public Builder Org_id(String org_id) {
            this.org_id = org_id;
            return this;
        }

        public PushMoveTeacher build() {
            return new PushMoveTeacher(this);
        }
    }
}
