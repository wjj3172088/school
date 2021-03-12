package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScOperLog;
import com.qh.basic.model.request.operlog.SearchRequest;
import com.qh.basic.service.IScOperLogService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作记录Controller
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/log")
public class ScOperLogController extends BaseController {

    @Autowired
    private IScOperLogService iScOperLogService;

    /**
     * 查询操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:log:list')")
    @GetMapping("/list")
    public R<IPage<ScOperLog>> list(@Validated SearchRequest request) {
        IPage<ScOperLog> list = iScOperLogService.selectScOperLogListByPage(getPage(), request);
        return R.ok(list);
    }
}
