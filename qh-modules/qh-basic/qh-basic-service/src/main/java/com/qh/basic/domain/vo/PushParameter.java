package com.qh.basic.domain.vo;

import com.qh.basic.enums.TemplateTypeEnum;
import lombok.Data;

import javax.annotation.Generated;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 16:43
 * @Description:
 */
@Data
public class PushParameter {
    /**
     * 标题
     */
    private String title;
    /**
     * 子标题，用于公告通知中的公告标题
     */
    private String subTitle;
    /**
     * 内容  透传内容
     */
    private String content;
    /**
     * 别名或cid
     */
    private String aliasOrCId;
    /**
     * true:别名、false:cid
     */
    private boolean isAliasOrCId;

    /**
     * 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启
     */
    private int transType = 2;

    ///////////////- IOS配置- /
    /**
     * 声音
     */
    private String sound;
    /**
     * 分类
     */
    private String category;
    /**
     * 弹出信息
     */
    private String alertMsg;

    //////////////////////-end-/////////////////
    /**
     * 消息模板类型
     */
    private TemplateTypeEnum templateTypeEnum;

    /**
     * 内容格式化
     */
    private String contentCmd;

    @Generated("SparkTools")
    private PushParameter(Builder builder) {
        this.title = builder.title;
        this.subTitle = builder.subTitle;
        this.content = builder.content;
        this.aliasOrCId = builder.aliasOrCId;
        this.isAliasOrCId = builder.isAliasOrCId;
        this.transType = builder.transType;
        this.sound = builder.sound;
        this.category = builder.category;
        this.alertMsg = builder.alertMsg;
        this.templateTypeEnum = builder.templateTypeEnum;
        this.contentCmd = builder.contentCmd;
    }

    public boolean isAliasOrCId() {
        return isAliasOrCId;
    }

    public void setAliasOrCId(boolean isAliasOrCId) {
        this.isAliasOrCId = isAliasOrCId;
    }

    /**
     * Creates builder to build {@link PushParameter}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link PushParameter}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private String title;
        private String subTitle;
        private String content;
        private String aliasOrCId;
        private boolean isAliasOrCId;
        private int transType;
        private String sound;
        private String category;
        private String alertMsg;
        private TemplateTypeEnum templateTypeEnum;
        private String contentCmd;

        private Builder() {
        }

        public Builder Title(String title) {
            this.title = title;
            return this;
        }

        public Builder SubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder Content(String content) {
            this.content = content;
            return this;
        }

        public Builder AliasOrCId(String aliasOrCId) {
            this.aliasOrCId = aliasOrCId;
            return this;
        }

        public Builder IsAliasOrCId(boolean isAliasOrCId) {
            this.isAliasOrCId = isAliasOrCId;
            return this;
        }

        public Builder TransType(int transType) {
            this.transType = transType;
            return this;
        }

        public Builder Sound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder Category(String category) {
            this.category = category;
            return this;
        }

        public Builder AlertMsg(String alertMsg) {
            this.alertMsg = alertMsg;
            return this;
        }

        public Builder TemplateTypeEnum(TemplateTypeEnum templateTypeEnum) {
            this.templateTypeEnum = templateTypeEnum;
            return this;
        }

        public Builder ContentCmd(String contentCmd) {
            this.contentCmd = contentCmd;
            return this;
        }

        public PushParameter build() {
            return new PushParameter(this);
        }
    }

}
