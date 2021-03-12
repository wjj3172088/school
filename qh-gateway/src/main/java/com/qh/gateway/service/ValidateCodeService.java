package com.qh.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.qh.common.core.web.domain.R;

/**
 * 验证码处理
 * 
 * @author
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    public R<JSONObject> createCapcha();

    /**
     * 校验验证码
     */
    public void checkCapcha(String key, String value);
}
