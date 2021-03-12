package com.qh.basic.enums;

import com.qh.common.core.enums.CodeEnum;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 16:20
 * @Description:
 */
public class BasicCodeEnum {
    public static final CodeEnum GRADE_NO_CONFIG = new CodeEnum("1000", "年级信息未配置");
    public static final CodeEnum VALID_CARD = new CodeEnum("1001", "身份证验证不通过");
    public static final CodeEnum VALID_PHONE = new CodeEnum("1002", "手机号验证不通过");
    public static final CodeEnum UPGRADE_NO_CONFIG = new CodeEnum("1003", "升学操作限制频率未配置");
    public static final CodeEnum UPGRADE_DATE_LIMIT = new CodeEnum("1004", "请于{0}后进行升学操作");
    public static final CodeEnum UPGRADE_DATE_NO_CONFIG = new CodeEnum("1005", "升学时间未配置");
    public static final CodeEnum GRADUATE_DATE_LIMIT = new CodeEnum("1004", "请于{0}后进行毕业操作");
    public static final CodeEnum GRADUATE_DATE_NO_CONFIG = new CodeEnum("1005", "毕业时间未配置");

    public static CodeEnum getCodeEnum(String code, String msg) {
        return new CodeEnum("BASIC" + code, msg);
    }
}
