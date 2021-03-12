package com.qh.basic.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/9 14:03
 * @Description:
 */
@Data
@NoArgsConstructor
public class PushMoveStaff {

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
     * 职工姓名
     */
    private String staffName;
    /**
     * 职工ID
     */
    private String staffId;

    private String orgId;

    @Generated("SparkTools")
    private PushMoveStaff(PushMoveStaff.Builder builder) {
        this.accId = builder.acc_id;
        this.alias = builder.alias;
        this.accNum = builder.acc_num;
        this.classId = builder.class_id;
        this.gtCid = builder.gt_cid;
        this.staffName = builder.staff_name;
        this.staffId = builder.staff_id;
        this.orgId = builder.org_id;
    }
    /**
     * Creates builder to build {@link PushMoveStaff}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static PushMoveStaff.Builder builder() {
        return new PushMoveStaff.Builder();
    }
    /**
     * Builder to build {@link PushMoveStaff}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private String acc_id;
        private String alias;
        private String acc_num;
        private String class_id;
        private String gt_cid;
        private String staff_name;
        private String staff_id;
        private String org_id;

        private Builder() {
        }

        public PushMoveStaff.Builder Acc_id(String acc_id) {
            this.acc_id = acc_id;
            return this;
        }

        public PushMoveStaff.Builder Alias(String alias) {
            this.alias = alias;
            return this;
        }

        public PushMoveStaff.Builder Acc_num(String acc_num) {
            this.acc_num = acc_num;
            return this;
        }

        public PushMoveStaff.Builder Class_id(String class_id) {
            this.class_id = class_id;
            return this;
        }

        public PushMoveStaff.Builder Gt_cid(String gt_cid) {
            this.gt_cid = gt_cid;
            return this;
        }

        public PushMoveStaff.Builder Staff_name(String staff_name) {
            this.staff_name = staff_name;
            return this;
        }

        public PushMoveStaff.Builder Staff_id(String staff_id) {
            this.staff_id = staff_id;
            return this;
        }

        public PushMoveStaff.Builder Org_id(String org_id) {
            this.org_id = org_id;
            return this;
        }

        public PushMoveStaff build() {
            return new PushMoveStaff(this);
        }
    }
}
