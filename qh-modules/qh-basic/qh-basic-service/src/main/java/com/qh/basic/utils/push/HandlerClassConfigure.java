package com.qh.basic.utils.push;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/30 11:03
 * @Description:
 */
public class HandlerClassConfigure {
    /**
     * 标志对应处理类名称
     */
    private static Map<String, String> classForName = new ConcurrentHashMap<>();

    static {
        //个推
        classForName.put(MessageSignConfigure.GT_MSG_PUSH, "pushService");
        classForName.put(MessageSignConfigure.GT_MSG_PUSH_BATCH, "pushBatchService");

        classForName.put(MessageSignConfigure.STATISTICS_USER_REQUEST, "statisticsUserOperatingService");

    }

    public static String getClassName(String keys) {
        if (classForName == null || classForName.size() == 0 || StringUtils.isEmpty(keys) || !classForName.containsKey(keys)) {
            return null;
        }
        return classForName.get(keys);
    }

    public class MessageSignConfigure {
        public static final String GT_MSG_PUSH = "0101001";

        public static final String GT_MSG_PUSH_BATCH = "0101002";

        public static final String STATISTICS_USER_REQUEST = "0101003";
    }
}
