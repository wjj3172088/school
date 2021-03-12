package com.qh.basic.utils.push;

import com.qh.basic.api.config.SystemPlugConfig;
import com.qh.basic.domain.vo.PushParameter;
import org.springframework.beans.factory.annotation.Autowired;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.dto.GtReq.NotifyInfo.Type;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.springframework.stereotype.Component;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 17:05
 * @Description:
 */
@Component
public class PushTemplate {

    @Autowired
    SystemPlugConfig systemPlugConfig;
    private APNPayload getApnPayload(PushParameter vo) {
        APNPayload payload = new APNPayload();
        payload.setAutoBadge("+1");
        payload.setContentAvailable(0);
        payload.setSound(vo.getSound());
        // "$由客户端定义"
        payload.setCategory(vo.getCategory());
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(vo.getTitle());
        alertMsg.setSubtitle(vo.getSubTitle());
        // 内容
        alertMsg.setBody(vo.getAlertMsg());
        payload.setAlertMsg(alertMsg);
        payload.addCustomMsg("payload", vo.getContent());
        return payload;
    }

    private LinkTemplate getLinkTemplate(PushParameter vo) {
        // LINK
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(systemPlugConfig.getAppId());
        template.setAppkey(systemPlugConfig.getAppKey());
        // 设置通知栏标题与内容
        template.setTitle(vo.getTitle());
        template.setText(vo.getContent());
        // 配置通知栏图标
        template.setLogo(systemPlugConfig.getLogo());
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl("");
        return template;
    }

    private NotificationTemplate getNotificationTemplate(PushParameter vo) {
        NotificationTemplate notificationTemplate = new NotificationTemplate();
        // 设置APPID与APPKEY
        notificationTemplate.setAppId(systemPlugConfig.getAppId());
        notificationTemplate.setAppkey(systemPlugConfig.getAppKey());
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        notificationTemplate.setTransmissionType(vo.getTransType());
        notificationTemplate.setTransmissionContent(vo.getContent());
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(vo.getTitle());
        style.setText(vo.getContent());
        // 配置通知栏图标
        style.setLogo(systemPlugConfig.getLogo());
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        notificationTemplate.setStyle(style);
        return notificationTemplate;
    }
    /**
     * @author mds
     * @DateTime 2019年5月25日 下午12:05:15
     * @serverComment
     *			获取模板
     * @param vo
     * @return
     */
    public ITemplate getPushTemplate(PushParameter vo) {
        // ITemplate template = null;
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(systemPlugConfig.getAppId());
        template.setAppkey(systemPlugConfig.getAppKey());
        template.setTransmissionContent(vo.getContent());
        template.setTransmissionType(vo.getTransType());

        switch (vo.getTemplateTypeEnum()) {
            case IOS_TEMPLATE:
                template.setAPNInfo(getApnPayload(vo));
                break;
            case TRANS_TEMPLATE:
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                break;
            case NOTICE_TEMPLATE:
                // 设置定时展示时间
                return getNotificationTemplate(vo);
            case NOTYPOPLOAD_TEMPLATE:

                break;
            case TRANS_MULTI_FIRM_TEMPLATE:
                // 多厂商
                // android
                Notify notify = new Notify();
                // 请输入通知栏标题
                notify.setTitle(vo.getTitle());
                // 请输入通知栏内容
                notify.setContent(vo.getAlertMsg());
                String packages = "com.vanv.campusafety";
                String component = packages + "/.ui.SplashActivity";
                notify.setIntent("intent:#Intent;launchFlags=0x04000000;package=" + packages + ";component=" + component + ";end");
                System.out.println(notify.getIntent());
                notify.setType(Type._intent);
                // 设置第三方通知
                template.set3rdNotifyInfo(notify);
                template.setAPNInfo(getApnPayload(vo));
                break;
            default:
                return getLinkTemplate(vo);
        }

        return template;
    }
}
