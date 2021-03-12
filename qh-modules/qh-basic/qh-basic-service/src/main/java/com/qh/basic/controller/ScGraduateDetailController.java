package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScGraduateDetail;
import com.qh.basic.service.IScGraduateDetailService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 毕业详情Controller
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/graduate/detail")
public class ScGraduateDetailController extends BaseController {

    @Autowired
    private IScGraduateDetailService graduateDetailService;

    /**
     * 查询毕业详情列表
     */
    @PreAuthorize("@ss.hasPermi('basic:graduate:detail:list')")
    @GetMapping("/list")
    public R<IPage<ScGraduateDetail>> list(ScGraduateDetail scGraduateDetail) {
        IPage<ScGraduateDetail> list = graduateDetailService.selectScGraduateDetailListByPage(getPage(), scGraduateDetail);
        return R.ok(list);
    }

    /**
     * 导出毕业详情列表
     */
    @PreAuthorize("@ss.hasPermi('basic:graduate:detail:export')")
    @Log(title = "毕业详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScGraduateDetail scGraduateDetail) throws IOException {
        List<ScGraduateDetail> list = graduateDetailService.selectExport(scGraduateDetail);
        ExcelUtil<ScGraduateDetail> util = new ExcelUtil<ScGraduateDetail>(ScGraduateDetail.class);
        util.exportExcel(response, list, scGraduateDetail.getYear() + "届毕业信息");
    }

    /**
     * 获取毕业详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:graduate:detail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(graduateDetailService.getById(id));
    }
}
