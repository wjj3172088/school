package com.qh.system.config;

import com.qh.system.api.constant.SystemKeyConstants;

/**
 * @Author: 汪俊杰
 * @Date: 2021/2/2 15:01
 * @Description:
 */
public class SystemCacheExpireConfig {
    private SystemCacheExpireConfig() {
        //空构造方法,防止new调用
    }

    /**
     * 我的快捷方式
     */
    public static final String MY_FAST = SystemKeyConstants.SYSTEM_PREFIX+ "menu:myfast:";
}
