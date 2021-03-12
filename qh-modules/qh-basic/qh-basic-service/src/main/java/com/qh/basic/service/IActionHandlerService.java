package com.qh.basic.service;

import com.qh.basic.domain.vo.PushParameter;

import java.util.List;
import java.util.Map;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/30 11:12
 * @Description:
 */
public interface IActionHandlerService {
    /**
     * 批量推送通知
     *
     * @param msgs 推送内容
     * @return
     */
    Map<String, Object> pushBatch(List<PushParameter> msgs);
}
