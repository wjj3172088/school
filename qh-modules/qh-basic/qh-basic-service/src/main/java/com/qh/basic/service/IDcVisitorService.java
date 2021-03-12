package com.qh.basic.service;

import com.qh.basic.domain.DcVisitor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.DcVisitorVo;
import com.qh.basic.model.request.visitor.VisitorSearchRequest;

/**
 * 访客信息Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-14
 */
public interface IDcVisitorService extends IService<DcVisitor> {
    /**
     * 查询访客信息集合
     *
     * @param page    分页信息
     * @param request 操作访客信息对象
     * @return 操作访客信息集合
     */
    IPage<DcVisitorVo> selectDcVisitorListByPage(IPage<DcVisitor> page, VisitorSearchRequest request);
}
