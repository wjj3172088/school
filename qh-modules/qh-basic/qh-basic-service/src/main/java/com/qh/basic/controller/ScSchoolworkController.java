package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScSchoolwork;
import com.qh.basic.service.IScSchoolworkService;
import com.qh.common.core.utils.http.DateUtil;
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
import java.util.Arrays;
import java.util.List;

/**
 * 作业管理Controller
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/schoolwork" )
public class ScSchoolworkController extends BaseController {

    @Autowired
    private IScSchoolworkService iScSchoolworkService;

    /**
     * 查询作业管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:list')")
    @GetMapping("/list")
    public R<IPage<ScSchoolwork>> list(ScSchoolwork scSchoolwork)
    {
        IPage<ScSchoolwork> list = iScSchoolworkService.selectScSchoolworkListByPage(getPage(),scSchoolwork);
        return R.ok(list);
    }

    /**
     * 导出作业管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:export')" )
    @Log(title = "作业管理" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScSchoolwork scSchoolwork)  throws IOException {
        LambdaQueryWrapper<ScSchoolwork> lqw = new LambdaQueryWrapper<ScSchoolwork>(scSchoolwork);
        List<ScSchoolwork> list = iScSchoolworkService.list(lqw);
        ExcelUtil<ScSchoolwork> util = new ExcelUtil<ScSchoolwork>(ScSchoolwork. class);
        util.exportExcel(response, list, "作业管理");
    }

    /**
     * 获取作业管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:query')" )
    @GetMapping(value = "/{schoolWorkId}" )
    public AjaxResult getInfo(@PathVariable("schoolWorkId" ) String schoolWorkId) {
        return AjaxResult.success(iScSchoolworkService.getById(schoolWorkId));
    }

    /**
     * 新增作业管理
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:add')" )
    @Log(title = "作业管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScSchoolwork scSchoolwork) {
        return AjaxResult.success(iScSchoolworkService.save(scSchoolwork) ? 1 : 0);
    }

    /**
     * 修改作业管理
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:edit')" )
    @Log(title = "作业管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScSchoolwork scSchoolwork) {
        scSchoolwork.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScSchoolworkService.updateById(scSchoolwork) ? 1 : 0);
    }

    /**
     * 删除作业管理
     */
    @PreAuthorize("@ss.hasPermi('basic:schoolwork:remove')" )
    @Log(title = "作业管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{schoolWorkIds}" )
    public AjaxResult remove(@PathVariable String[] schoolWorkIds) {
        return AjaxResult.success(iScSchoolworkService.removeByIds(Arrays.asList(schoolWorkIds)) ? 1 : 0);
    }
}
