package com.qh.basic.enums;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 16:44
 * @Description:
 */
public enum TemplateTypeEnum {
    /**
     * template IOSTemplate
     */
    IOS_TEMPLATE("IOS"),
    /**
     * TransmissionTemplate
     */
    TRANS_TEMPLATE("Transmission"),
    /**
     * NotificationTemplate
     */
    NOTICE_TEMPLATE("Notification"),
    /**
     * NotyPopLoadTemplate
     */
    NOTYPOPLOAD_TEMPLATE("NotyPopLoad"),
    /**
     * LinkTemplate
     */
    LINK_TEMPLATE("Link"),
    /**
     * 多厂商
     */
    TRANS_MULTI_FIRM_TEMPLATE("TransMultiFirmTemplate");

    private String templateType;

    private TemplateTypeEnum(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}
