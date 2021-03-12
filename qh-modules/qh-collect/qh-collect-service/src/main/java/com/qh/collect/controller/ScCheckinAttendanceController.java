package com.qh.collect.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import com.qh.common.log.enums.BusinessType;
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
import com.qh.collect.domain.ScCheckinAttendance;
import com.qh.collect.service.IScCheckinAttendanceService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 考勤总每日记录Controller
 * 本Controller接口暂时前台没调用
 * @author huangdaoquan
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/checkinAttendance" )
public class ScCheckinAttendanceController extends BaseController {

    @Autowired
    private IScCheckinAttendanceService iScCheckinAttendanceService;

    /**
     * 查询考勤总每日记录列表
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:list')")
    @GetMapping("/list")
    public R<IPage<ScCheckinAttendance>> list(ScCheckinAttendance scCheckinAttendance)
    {
        IPage<ScCheckinAttendance> list = iScCheckinAttendanceService.selectScCheckinAttendanceListByPage(getPage(),scCheckinAttendance);
        return R.ok(list);
    }

    /**
     * 导出考勤总每日记录列表
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:export')" )
    @Log(title = "考勤总每日记录" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScCheckinAttendance scCheckinAttendance)  throws IOException {
        LambdaQueryWrapper<ScCheckinAttendance> lqw = new LambdaQueryWrapper<ScCheckinAttendance>(scCheckinAttendance);
        List<ScCheckinAttendance> list = iScCheckinAttendanceService.list(lqw);
        ExcelUtil<ScCheckinAttendance> util = new ExcelUtil<ScCheckinAttendance>(ScCheckinAttendance. class);
        util.exportExcel(response, list, "考勤总每日记录");
    }

    /**
     * 获取考勤总每日记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:query')" )
    @GetMapping(value = "/{attendanceId}" )
    public AjaxResult getInfo(@PathVariable("attendanceId" ) Integer attendanceId) {
        return AjaxResult.success(iScCheckinAttendanceService.getById(attendanceId));
    }

    /**
     * 新增考勤总每日记录
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:add')" )
    @Log(title = "考勤总每日记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScCheckinAttendance scCheckinAttendance) {
        return AjaxResult.success(iScCheckinAttendanceService.save(scCheckinAttendance) ? 1 : 0);
    }

    /**
     * 修改考勤总每日记录
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:edit')" )
    @Log(title = "考勤总每日记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScCheckinAttendance scCheckinAttendance) {
        return AjaxResult.success(iScCheckinAttendanceService.updateById(scCheckinAttendance) ? 1 : 0);
    }

    /**
     * 删除考勤总每日记录
     */
    @PreAuthorize("@ss.hasPermi('collect:checkinAttendance:remove')" )
    @Log(title = "考勤总每日记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{attendanceIds}" )
    public AjaxResult remove(@PathVariable Integer[] attendanceIds) {
        return AjaxResult.success(iScCheckinAttendanceService.removeByIds(Arrays.asList(attendanceIds)) ? 1 : 0);
    }
}
