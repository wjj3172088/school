package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScGraduate;
import com.qh.basic.service.IScGraduateService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 毕业总览Controller
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/graduate")
public class ScGraduateController extends BaseController {
    @Autowired
    private IScGraduateService graduateService;

    /**
     * 查询毕业总览列表
     */
    @PreAuthorize("@ss.hasPermi('basic:graduate:list')")
    @GetMapping("/list")
    public R<IPage<ScGraduate>> list(ScGraduate scGraduate) {
        IPage<ScGraduate> list = graduateService.selectScGraduateListByPage(getPage(), scGraduate);
        return R.ok(list);
    }
}
