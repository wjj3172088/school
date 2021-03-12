package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScCurriculum;
import com.qh.basic.service.IScCurriculumService;
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
 * 课程Controller
 * 
 * @author 黄道权
 * @date 2020-11-13
 */
@RestController
@RequestMapping("/curriculum" )
public class ScCurriculumController extends BaseController {

    @Autowired
    private IScCurriculumService iScCurriculumService;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:list')")
    @GetMapping("/list")
    public R<IPage<ScCurriculum>> list(ScCurriculum scCurriculum)
    {
        IPage<ScCurriculum> list = iScCurriculumService.selectScCurriculumListByPage(getPage(),scCurriculum);
        return R.ok(list);
    }

    /**
     * 导出课程列表
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:export')" )
    @Log(title = "课程" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScCurriculum scCurriculum)  throws IOException {
        LambdaQueryWrapper<ScCurriculum> lqw = new LambdaQueryWrapper<ScCurriculum>(scCurriculum);
        List<ScCurriculum> list = iScCurriculumService.list(lqw);
        ExcelUtil<ScCurriculum> util = new ExcelUtil<ScCurriculum>(ScCurriculum. class);
        util.exportExcel(response, list, "课程");
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:query')" )
    @GetMapping(value = "/{curriculumId}" )
    public AjaxResult getInfo(@PathVariable("curriculumId" ) String curriculumId) {
        return AjaxResult.success(iScCurriculumService.getById(curriculumId));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:add')" )
    @Log(title = "课程" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScCurriculum scCurriculum) {
        scCurriculum.setCreateDate(DateUtil.getSystemSeconds());
        scCurriculum.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScCurriculumService.save(scCurriculum) ? 1 : 0);
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:edit')" )
    @Log(title = "课程" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScCurriculum scCurriculum) {
        scCurriculum.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScCurriculumService.updateById(scCurriculum) ? 1 : 0);
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('basic:curriculum:remove')" )
    @Log(title = "课程" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{curriculumIds}" )
    public AjaxResult remove(@PathVariable String[] curriculumIds) {
        return AjaxResult.success(iScCurriculumService.removeByIds(Arrays.asList(curriculumIds)) ? 1 : 0);
    }
}
