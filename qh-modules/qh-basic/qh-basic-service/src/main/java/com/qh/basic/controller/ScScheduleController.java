package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
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
import com.qh.basic.domain.ScSchedule;
import com.qh.basic.service.IScScheduleService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 教职工排班Controller
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@RestController
@RequestMapping("/schedule" )
public class ScScheduleController extends BaseController {

    @Autowired
    private IScScheduleService iScScheduleService;

    /**
     * 查询教职工排班列表
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:list')")
    @GetMapping("/list")
    public R<IPage<ScSchedule>> list(ScSchedule scSchedule)
    {
        IPage<ScSchedule> list = iScScheduleService.selectScScheduleListByPage(getPage(),scSchedule);
        return R.ok(list);
    }

    /**
     * 导出教职工排班列表
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:export')" )
    @Log(title = "教职工排班" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScSchedule scSchedule)  throws IOException {
        LambdaQueryWrapper<ScSchedule> lqw = new LambdaQueryWrapper<ScSchedule>(scSchedule);
        List<ScSchedule> list = iScScheduleService.list(lqw);
        ExcelUtil<ScSchedule> util = new ExcelUtil<ScSchedule>(ScSchedule. class);
        util.exportExcel(response, list, "教职工排班");
    }

    /**
     * 获取教职工排班详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:query')" )
    @GetMapping(value = "/{scheduleId}" )
    public AjaxResult getInfo(@PathVariable("scheduleId" ) String scheduleId) {
        return AjaxResult.success(iScScheduleService.getById(scheduleId));
    }

    /**
     * 新增教职工排班
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:add')" )
    @Log(title = "教职工排班" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScSchedule scSchedule) {
        scSchedule.setOrgName(SecurityUtils.getOrgName());
        scSchedule.setOrgId(SecurityUtils.getOrgId());
        scSchedule.setCreateDate(DateUtil.getSystemSeconds());
        scSchedule.setModifyDate(DateUtil.getSystemSeconds());
        scSchedule.setPublisherName(SecurityUtils.getUsername());
        scSchedule.setPublisherId(SecurityUtils.getUserId().toString());
        return AjaxResult.success(iScScheduleService.save(scSchedule) ? 1 : 0);
    }

    /**
     * 修改教职工排班
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:edit')" )
    @Log(title = "教职工排班" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScSchedule scSchedule) {
        scSchedule.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScScheduleService.updateById(scSchedule) ? 1 : 0);
    }

    /**
     * 删除教职工排班
     */
    @PreAuthorize("@ss.hasPermi('basic:schedule:remove')" )
    @Log(title = "教职工排班" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{scheduleIds}" )
    public AjaxResult remove(@PathVariable String[] scheduleIds) {
        return AjaxResult.success(iScScheduleService.removeByIds(Arrays.asList(scheduleIds)) ? 1 : 0);
    }
}
