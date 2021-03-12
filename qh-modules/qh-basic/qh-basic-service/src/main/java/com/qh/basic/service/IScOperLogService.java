package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScOperLog;
import com.qh.basic.model.request.operlog.SearchRequest;

/**
 * 操作记录Service接口
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
public interface IScOperLogService extends IService<ScOperLog> {
    /**
     * 查询操作记录集合
     *
     * @param page    分页信息
     * @param request 操作操作记录对象
     * @return 操作操作记录集合
     */
    IPage<ScOperLog> selectScOperLogListByPage(IPage<ScOperLog> page, SearchRequest request);

    /**
     * 操作记录
     *
     * @param userId       操作人id
     * @param userName     操作人姓名
     * @param operType     操作类型
     * @param operUserType 操作人类型
     */
    void add(String userId, String userName, int operType, int operUserType);
}
