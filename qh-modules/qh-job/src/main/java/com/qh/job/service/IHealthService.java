package com.qh.job.service;

/**
 * 健康码 服务层
 * 
 * @author huangdaoquan
 */
public interface IHealthService
{
    /**
     * 健康码状态同步
     * @param idCard
     * @return
     */
    String getHttpHealthState(String idCard);
}