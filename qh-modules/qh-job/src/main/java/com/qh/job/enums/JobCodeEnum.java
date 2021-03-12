package com.qh.job.enums;

import com.qh.common.core.enums.CodeEnum;

public class JobCodeEnum {

    public static final CodeEnum CONFIG_ERROR = getCodeEnum("1001", "任务策略： {0} 不能在定时任务中生效");

    public static CodeEnum getCodeEnum(String code, String msg) {
        return new CodeEnum("JOB" + code, msg);
    }


}
