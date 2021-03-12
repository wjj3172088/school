package com.qh.basic.utils;

import com.alibaba.fastjson.JSONObject;
import com.qh.basic.enums.MsgCommand;
import com.qh.basic.enums.PushNewMsgTypeEnum;
import com.qh.common.core.utils.http.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 16:27
 * @Description:
 */
public class MsgJointUtils {


    /**
     * @author mds
     * @DateTime 2016年10月22日 上午9:58:13
     * @serverComment 指令处理
     *
     * @param smsCommand
     * @param contents 需要拼接上去的参数
     * @return
     */
    public static String commandHandle(MsgCommand msgCommand, String... contents) {
        return commandHandle(msgCommand, 2, contents);
    }

    /**
     * @author mds
     * @DateTime 2019年8月7日 上午10:06:33
     * @serverComment
     *
     * @param title 标题
     * @param content 内容
     * @param msgType 消息类型
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static String commandHandle(String title, String content, int msgType, String longitude, String latitude) {
        return commandHandle(title, content, msgType, longitude, latitude, null);
    }

    /**
     *
     * @Title:				增加一个3行显示样式
     * @Description: TODO
     * @param title				通知标题
     * @param content			通知内容
     * @param msgType
     * @param longitude
     * @param latitude
     * @param subTitle				子标题，用于公告通知中的公告标题
     * @return
     * @return String
     * @throws
     */
    public static String commandHandle(String title, String content, int msgType, String longitude, String latitude, String subTitle) {
        JSONObject message = new JSONObject();
        message.put("title", title);
        message.put("content", content);
        message.put("msgType", msgType);
        if (subTitle != null) {
            message.put("subTitle", subTitle);
        }
        if (StringUtils.isNotBlank(longitude)) {
            message.put("longitude", longitude);
        }
        if (StringUtils.isNotBlank(latitude)) {
            message.put("latitude", latitude);
        }
        return message.toJSONString();
    }

    /**
     *
     * @Title: commandHandle
     * @Description: 告警透传内容（只针对告警使用）
     * @param @param title
     * @param @param content
     * @param @param msgType
     * @param @param longitude
     * @param @param latitude
     * @param @return 参数
     * @author zhangzhf
     * @date 2019年8月20日
     */
    public static String commandHandleAlarm(String title, String content, int msgType, String longitude, String latitude, String alarmTime, String stationType, String location) {
        JSONObject message = new JSONObject();
        message.put("title", title);
        message.put("content", content);
        message.put("msgType", msgType);
        if (StringUtils.isNotBlank(longitude)) {
            message.put("longitude", longitude);
        }
        if (StringUtils.isNotBlank(latitude)) {
            message.put("latitude", latitude);
        }

        message.put("alarmTime", alarmTime);
        message.put("stationType", stationType);
        message.put("location", location);

        return message.toJSONString();
    }

    /**
     * @author mds
     * @DateTime 2016年12月28日 上午11:33:31
     * @serverComment
     *
     * @param smsCommand
     * @param msgType 0:只发内容，1:添加时间，2：添加签名，3：添加时间和签名
     * @param contents 需要拼接上去的参数
     * @return
     */
    public static String commandHandle(MsgCommand msgCommand, int msgType, String... contents) {
        String message = "";
        switch (msgCommand) {
            case ALERT_MSG:
                message = replace(msgCommand.getCommand(), msgType, contents);
                break;
            default:
                message = replace(msgCommand.getCommand(), msgType, contents);
                break;
        }
        return message;
    }

    public static String commandHandle(MsgCommand msgCommand, PushNewMsgTypeEnum msgType, String... contents) {
        String message = null;
        switch (msgCommand) {
            case ALERT_MSG:
                message = replace(msgCommand.getCommand(), msgType, contents);
                break;
            default:
                message = replace(msgCommand.getCommand(), msgType, contents);
                break;
        }
        return message;
    }

    /**
     * @author mds
     * @DateTime 2016年10月22日 上午9:56:43
     * @serverComment 拼接命令
     *
     * @param res  需要拼接的命令
     * @param str 需要拼接上去的参数
     * @param msgType 1:添加时间，2：添加签名，3：添加时间和签名
     * @return
     */
    private static String replace(String res, int msgType, String... contents) {
        String message = res;
        for (int i = 0; i < contents.length; i++) {
            int index = message.indexOf("?");
            message = message.substring(0, index) + contents[i] + message.substring(index + 1);
        }
        String dateTime = "";
        switch (msgType) {
            case 1:
                dateTime = DateUtils.format(DateUtils.yyyy_MM_dd_HH_mm_ss);
                message = message + dateTime;
                break;
            case 2:
                message = MsgCommand.SIGNATURE.getCommand() + message;
                break;
            case 3:
                dateTime = DateUtils.format(DateUtils.yyyy_MM_dd_HH_mm_ss);
                message = MsgCommand.SIGNATURE.getCommand() + message + dateTime;
                break;
            default:

                break;
        }
        return message;
    }

    private static String replace(String res, PushNewMsgTypeEnum msgType, String... contents) {
        return replace(res, msgType.getCode(), contents);
    }
}
