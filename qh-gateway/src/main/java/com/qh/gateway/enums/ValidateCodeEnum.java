package com.qh.gateway.enums;

import com.qh.common.core.enums.CodeEnum;

public class ValidateCodeEnum {
    public static final CodeEnum CODE_EMPTY = getCodeEnum("1001", "验证码不能为空");
    public static final CodeEnum CODE_EXPIRE = getCodeEnum("1002", "验证码已失效");
    public static final CodeEnum CODE_ERROR = getCodeEnum("1003", "验证码错误");
    public static final CodeEnum CREATE_CODE_FAIL = getCodeEnum("1003", "创建验证码失败");

    public static CodeEnum getCodeEnum(String code, String msg) {
        return new CodeEnum("CODE" + code, msg);
    }


}
