package com.qh.basic.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.qh.basic.api.config.SystemPlugConfig;
import com.qh.basic.domain.vo.PushParameter;
import com.qh.basic.enums.TemplateTypeEnum;
import com.qh.basic.utils.push.PushTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/27 17:02
 * @Description:
 */
@Component
@Slf4j
public class PushUtils {

    private static final Logger logger = LoggerFactory.getLogger(PushUtils.class);

    @Autowired
    SystemPlugConfig systemPlugConfig;

    @Autowired
    PushTemplate pushTemplate;

    private static IGtPush push = null;

    private IGtPush getPushInstance() {
        try {
            if (push == null || !push.connect()) {
                push = new IGtPush(systemPlugConfig.getHost(), systemPlugConfig.getAppKey(), systemPlugConfig.getMasterSecret());
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return push;
    }

    public void pushAndroid(PushParameter pushParameter) {
    }

    public PushParameter pushTransMultiFirmByCid(String title, String alertMsg, String cId, String transContent) {
        PushParameter bean = PushParameter.builder()
                .Title(title)
                .AlertMsg(alertMsg)
                .AliasOrCId(cId)
                .IsAliasOrCId(false)
                .Content(transContent)
                .TemplateTypeEnum(TemplateTypeEnum.TRANS_MULTI_FIRM_TEMPLATE)
                .build();
        return bean;
    }

    public PushParameter pushTransMultiFirmByCidNewThreeLine(String title, String alertMsg, String cId,
                                                                 String transContent, String subTitle) {
        PushParameter bean = PushParameter.builder()
                .Title(title)
                .AlertMsg(alertMsg)
                .AliasOrCId(cId)
                .IsAliasOrCId(false)
                .Content(transContent)
                .SubTitle(subTitle)
                .TemplateTypeEnum(TemplateTypeEnum.TRANS_MULTI_FIRM_TEMPLATE)
                .build();
        return bean;
    }

    /**
     * @author mds
     * @DateTime 2016年12月27日 上午11:43:52
     * @serverComment
     *
     * @param title
     * @param content
     * @param aliasOrCId
     * @param isAliasOrCId true:别名 false:CId
     * @param templateType
     * @return
     */
    private Map<String, Object> pushNotice(PushParameter bean) {
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(Long.parseLong(systemPlugConfig.getOffline()));
        ITemplate template = pushTemplate.getPushTemplate(bean);
        if (template != null) {
            message.setData(template);
        }
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(systemPlugConfig.getAppId());
        if (bean.isAliasOrCId()) {
            target.setAlias(bean.getAliasOrCId());
        } else {
            target.setClientId(bean.getAliasOrCId());
        }
        IPushResult ret = null;
        try {
            ret = getPushInstance().pushMessageToSingle(message, target);
        } catch (RequestException e) {
            logger.error(e.getMessage(), e);
            ret = getPushInstance().pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            return ret.getResponse();
        } else {
            logger.warn("geTui服务器响应异常");
            return null;
        }
    }
}
