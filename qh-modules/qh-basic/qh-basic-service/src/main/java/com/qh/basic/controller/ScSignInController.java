package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScSignIn;
import com.qh.basic.service.IScSignInService;
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
 * 到校签到Controller
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@RestController
@RequestMapping("/signin" )
public class ScSignInController extends BaseController {

    @Autowired
    private IScSignInService iScSignInService;

    /**
     * 查询到校签到列表
     */
    @PreAuthorize("@ss.hasPermi('basic:signin:list')")
    @GetMapping("/list")
    public R<IPage<ScSignIn>> list(ScSignIn scSignIn)
    {
        IPage<ScSignIn> list = iScSignInService.selectScSignInListByPage(getPage(),scSignIn);
        return R.ok(list);
    }

    /**
     * 导出到校签到列表
     */
    @PreAuthorize("@ss.hasPermi('basic:signin:export')" )
    @Log(title = "到校签到" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScSignIn scSignIn)  throws IOException {
        List<ScSignIn> list = iScSignInService.selectScSignInList(scSignIn);
        ExcelUtil<ScSignIn> util = new ExcelUtil<ScSignIn>(ScSignIn.class);
        util.exportExcel(response, list, "到校签到");
    }

    /**
     * 获取到校签到详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:signin:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) String id) {
        return AjaxResult.success(iScSignInService.getById(id));
    }
}
