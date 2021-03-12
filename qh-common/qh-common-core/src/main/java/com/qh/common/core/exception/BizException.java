package com.qh.common.core.exception;


import com.qh.common.core.enums.CodeEnum;

import java.text.MessageFormat;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String code;


    public BizException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public BizException(CodeEnum codeEnum, String... params) {
        super(MessageFormat.format(codeEnum.getMsg(), (Object[]) params));
        this.code = codeEnum.getCode();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = CodeEnum.FAILURE.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode(){
        return code;
    }
}
