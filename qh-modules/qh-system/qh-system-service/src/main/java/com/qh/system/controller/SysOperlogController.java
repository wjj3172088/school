package com.qh.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.domain.SysOperLog;
import com.qh.system.enums.OperStatusEnum;
import com.qh.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author
 */
@RestController
@RequestMapping("/operlog")
@CheckBackendToken
public class SysOperlogController extends BaseController {
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('system:operlog:list')")
    @GetMapping("/list")
    public R<IPage<SysOperLog>> list(SysOperLog operLog) {
        IPage<SysOperLog> list = operLogService.selectOperLogListByPage(getPage(), operLog);
        return R.ok(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog) throws IOException {
        List<SysOperLog> list = operLogService.selectOperLogListAll(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @PreAuthorize("@ss.hasPermi('system:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public R remove(@PathVariable Long[] operIds) {
        return toResult(operLogService.deleteOperLogByIds(operIds));
    }

    @PreAuthorize("@ss.hasPermi('system:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R clean() {
        operLogService.cleanOperLog();
        return R.ok();
    }

    @PostMapping
    @CheckBackendToken(required = false)
    public R add(@RequestBody SysOperLog operLog) {
        return toResult(operLogService.insertOperlog(operLog));
    }
}
