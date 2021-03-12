package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.DcVisitorVo;
import com.qh.basic.model.request.visitor.VisitorSearchRequest;
import com.qh.basic.service.IDcVisitorService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访客信息Controller
 *
 * @author 汪俊杰
 * @date 2021-01-14
 */
@RestController
@RequestMapping("/visitor")
public class DcVisitorController extends BaseController {

    @Autowired
    private IDcVisitorService iDcVisitorService;

    /**
     * 查询访客信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:visitor:list')")
    @GetMapping("/list")
    public R<IPage<DcVisitorVo>> list(VisitorSearchRequest request) {
        IPage<DcVisitorVo> list = iDcVisitorService.selectDcVisitorListByPage(getPage(), request);
        return R.ok(list);
    }
}
