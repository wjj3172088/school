package com.qh.job.service;

import com.qh.common.core.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

/**
 * 健康码状态同步 服务层
 * 
 * @author 
 */
@Service
public class HealthServiceImpl implements IHealthService
{
    private final static String HEALTH_URL="https://www.96time.cn:12088/jiangnan/get";

    /**
     * 健康码状态同步
     * @param idCard
     * @return
     */
    @Override
    public String getHttpHealthState(String idCard){
        String result = HttpClientUtils.sendGet(HEALTH_URL,"idCardNumber="+idCard);
        return result;
    }
}