package com.qh.basic.service.impl;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.qh.basic.api.config.SystemPlugConfig;
import com.qh.basic.domain.vo.PushParameter;
import com.qh.basic.service.IActionHandlerService;
import com.qh.basic.utils.push.PushTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/30 11:10
 * @Description:
 */
@Slf4j
@Service
public class PushServiceImpl implements IActionHandlerService {
    @Autowired
    SystemPlugConfig systemPlugConfig;

    @Autowired
    PushTemplate pushTemplate;

    private static IGtPush push = null;

    private IGtPush getPushInstance() {
        try {
            if (push == null || !push.connect()) {
                push = new IGtPush(systemPlugConfig.getHost(), systemPlugConfig.getAppKey(),
                        systemPlugConfig.getMasterSecret());
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return push;
    }

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
        if (ret != null) {
            return ret.getResponse();
        } else {
            log.warn("geTui服务器响应异常");
            return null;
        }
    }

    /**
     * @param msgs
     * @return
     * @author mds
     * @DateTime 2019年5月25日 下午12:24:24
     * @serverComment 批量推送通知
     */
    @Override
    public Map<String, Object> pushBatch(List<PushParameter> msgs) {


        if (msgs != null) {
            // 每次单推限制 1000 条以下，这用 200 条(测试过800,500均提示数据过大)
            List<List<PushParameter>> msgLists = getSubLists(msgs, 200);

            for (List<PushParameter> msgList : msgLists) {
                log.info("pushBatch msgListSize {} ", msgList.size());
                Map<String, Object> map = pushBatchDetail(msgList);
                if (map != null && map.size() > 0) {
                    log.info("pushBatch result:{}", map);
                }
            }

        }
        return null;
    }

    private Map<String, Object> pushBatchDetail(List<PushParameter> msgList) {
        IBatch batch = getPushInstance().getBatch();
        for (PushParameter msg : msgList) {
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(Long.parseLong(systemPlugConfig.getOffline()));
            ITemplate template = pushTemplate.getPushTemplate(msg);
            if (template != null) {
                message.setData(template);
                // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
                message.setPushNetWorkType(0);
                Target target = new Target();
                target.setAppId(systemPlugConfig.getAppId());
                if (msg.isAliasOrCId()) {
                    target.setAlias(msg.getAliasOrCId());
                } else {
                    target.setClientId(msg.getAliasOrCId());
                }
                try {
                    batch.add(message, target);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(), e);
                }
            }
        }
        try {
            IPushResult gtRes = batch.submit();
            if (gtRes != null) {
                return gtRes.getResponse();
            } else {
                log.warn("geTui服务器响应异常");
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private List<List<PushParameter>> getSubLists(List<PushParameter> allData, int size) {
        List<List<PushParameter>> result = new ArrayList();
        for (int begin = 0; begin < allData.size(); begin = begin + size) {
            int end = (begin + size > allData.size() ? allData.size() : begin + size);
            result.add(allData.subList(begin, end));
        }
        return result;
    }
}
