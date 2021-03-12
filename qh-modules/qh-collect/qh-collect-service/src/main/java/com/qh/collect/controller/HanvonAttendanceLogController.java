package com.qh.collect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.collect.domain.HanvonAttendanceLog;
import com.qh.collect.service.IHanvonAttendanceLogService;
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
 * 汉王考勤记录Controller
 * 
 * @author huangdaoquan
 * @date 2020-12-25
 */
@RestController
@RequestMapping("/hanvonAttendance" )
public class HanvonAttendanceLogController extends BaseController {

    @Autowired
    private IHanvonAttendanceLogService iHanvonAttendanceLogService;

    /**
     * 查询汉王考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:list')")
    @GetMapping("/list")
    public R<IPage<HanvonAttendanceLog>> list(HanvonAttendanceLog hanvonAttendanceLog)
    {
        IPage<HanvonAttendanceLog> list = iHanvonAttendanceLogService.selectHanvonAttendanceLogListByPage(getPage(),hanvonAttendanceLog);
        return R.ok(list);
    }

    /**
     * 导出汉王考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:export')" )
    @Log(title = "汉王考勤记录" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, HanvonAttendanceLog hanvonAttendanceLog)  throws IOException {
        List<HanvonAttendanceLog> list = iHanvonAttendanceLogService.exportHanvonAttendanceLog(hanvonAttendanceLog);
        ExcelUtil<HanvonAttendanceLog> util = new ExcelUtil<HanvonAttendanceLog>(HanvonAttendanceLog. class);
        util.exportExcel(response, list, "汉王考勤记录");
    }

    /**
     * 获取汉王考勤记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:query')" )
    @GetMapping(value = "/{logId}" )
    public AjaxResult getInfo(@PathVariable("logId" ) Long logId) {
        return AjaxResult.success(iHanvonAttendanceLogService.getById(logId));
    }

    /**
     * 新增汉王考勤记录
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:add')" )
    @Log(title = "汉王考勤记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HanvonAttendanceLog hanvonAttendanceLog) {
        return AjaxResult.success(iHanvonAttendanceLogService.save(hanvonAttendanceLog) ? 1 : 0);
    }

    /**
     * 修改汉王考勤记录
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:edit')" )
    @Log(title = "汉王考勤记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HanvonAttendanceLog hanvonAttendanceLog) {
        return AjaxResult.success(iHanvonAttendanceLogService.updateById(hanvonAttendanceLog) ? 1 : 0);
    }

    /**
     * 删除汉王考勤记录
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvonAttendance:remove')" )
    @Log(title = "汉王考勤记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}" )
    public AjaxResult remove(@PathVariable Long[] logIds) {
        return AjaxResult.success(iHanvonAttendanceLogService.removeByIds(Arrays.asList(logIds)) ? 1 : 0);
    }
}
