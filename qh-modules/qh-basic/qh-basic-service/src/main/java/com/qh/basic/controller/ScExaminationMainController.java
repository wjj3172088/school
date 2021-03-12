package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.qh.common.core.utils.StringUtils;
import javax.servlet.http.HttpServletResponse;
import com.qh.common.log.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qh.common.core.web.domain.R;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.log.annotation.Log;
import com.qh.common.core.web.controller.BaseController;
import com.qh.basic.domain.ScExaminationMain;
import com.qh.basic.service.IScExaminationMainService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 考试成绩Controller
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/examinationmain" )
public class ScExaminationMainController extends BaseController {

    @Autowired
    private IScExaminationMainService iScExaminationMainService;

    /**
     * 查询考试成绩列表
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:list')")
    @GetMapping("/list")
    public R<IPage<ScExaminationMain>> list(ScExaminationMain scExaminationMain)
    {
        IPage<ScExaminationMain> list = iScExaminationMainService.selectScExaminationMainListByPage(getPage(),scExaminationMain);
        return R.ok(list);
    }

    /**
     * 导出考试成绩列表
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:export')" )
    @Log(title = "考试成绩" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScExaminationMain scExaminationMain)  throws IOException {
        LambdaQueryWrapper<ScExaminationMain> lqw = new LambdaQueryWrapper<ScExaminationMain>(scExaminationMain);
        List<ScExaminationMain> list = iScExaminationMainService.list(lqw);
        ExcelUtil<ScExaminationMain> util = new ExcelUtil<ScExaminationMain>(ScExaminationMain. class);
        util.exportExcel(response, list, "考试成绩");
    }

    /**
     * 获取考试成绩详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:query')" )
    @GetMapping(value = "/{examinationId}" )
    public AjaxResult getInfo(@PathVariable("examinationId" ) String examinationId) {
        return AjaxResult.success(iScExaminationMainService.getById(examinationId));
    }

    /**
     * 新增考试成绩
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:add')" )
    @Log(title = "考试成绩" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScExaminationMain scExaminationMain) {
        return AjaxResult.success(iScExaminationMainService.save(scExaminationMain) ? 1 : 0);
    }

    /**
     * 修改考试成绩
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:edit')" )
    @Log(title = "考试成绩" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScExaminationMain scExaminationMain) {
        return AjaxResult.success(iScExaminationMainService.updateById(scExaminationMain) ? 1 : 0);
    }

    /**
     * 删除考试成绩
     */
    @PreAuthorize("@ss.hasPermi('basic:examinationmain:remove')" )
    @Log(title = "考试成绩" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{examinationIds}" )
    public AjaxResult remove(@PathVariable String[] examinationIds) {
        return AjaxResult.success(iScExaminationMainService.removeByIds(Arrays.asList(examinationIds)) ? 1 : 0);
    }
}
