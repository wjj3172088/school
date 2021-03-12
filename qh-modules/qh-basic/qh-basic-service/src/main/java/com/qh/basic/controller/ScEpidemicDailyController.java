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
import com.qh.basic.api.domain.ScEpidemicDaily;
import com.qh.basic.service.IScEpidemicDailyService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 疫情日报Controller
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@RestController
@RequestMapping("/daily" )
public class ScEpidemicDailyController extends BaseController {

    @Autowired
    private IScEpidemicDailyService iScEpidemicDailyService;

    /**
     * 查询疫情日报列表
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:list')")
    @GetMapping("/list")
    public R<IPage<ScEpidemicDaily>> list(ScEpidemicDaily scEpidemicDaily)
    {
        IPage<ScEpidemicDaily> list = iScEpidemicDailyService.selectScEpidemicDailyListByPage(getPage(),scEpidemicDaily);
        return R.ok(list);
    }

    /**
     * 导出疫情日报列表
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:export')" )
    @Log(title = "疫情日报" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScEpidemicDaily scEpidemicDaily)  throws IOException {
        List<ScEpidemicDaily> list = iScEpidemicDailyService.selectScEpidemicDailyList(scEpidemicDaily);
        ExcelUtil<ScEpidemicDaily> util = new ExcelUtil<ScEpidemicDaily>(ScEpidemicDaily. class);
        util.exportExcel(response, list, "疫情日报");
    }

    /**
     * 获取疫情日报详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:query')" )
    @GetMapping(value = "/{dailyId}" )
    public AjaxResult getInfo(@PathVariable("dailyId" ) String dailyId) {
        return AjaxResult.success(iScEpidemicDailyService.getById(dailyId));
    }

    /**
     * 新增疫情日报
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:add')" )
    @Log(title = "疫情日报" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScEpidemicDaily scEpidemicDaily) {
        scEpidemicDaily.setOrgName(SecurityUtils.getOrgName());
        scEpidemicDaily.setOrgId(SecurityUtils.getOrgId());
        scEpidemicDaily.setCreateDate(DateUtil.getSystemSeconds());
        scEpidemicDaily.setModifyDate(DateUtil.getSystemSeconds());
        scEpidemicDaily.setPublisherName(SecurityUtils.getUsername());
        scEpidemicDaily.setPublisherId(SecurityUtils.getUserId().toString());
        return AjaxResult.success(iScEpidemicDailyService.save(scEpidemicDaily) ? 1 : 0);
    }

    /**
     * 修改疫情日报
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:edit')" )
    @Log(title = "疫情日报" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScEpidemicDaily scEpidemicDaily) {
        scEpidemicDaily.setModifyDate(DateUtil.getSystemSeconds());
        scEpidemicDaily.setPublisherName(SecurityUtils.getUsername());
        scEpidemicDaily.setPublisherId(SecurityUtils.getUserId().toString());
        scEpidemicDaily.setStateMark(1);
        return AjaxResult.success(iScEpidemicDailyService.updateById(scEpidemicDaily) ? 1 : 0);
    }

    /**
     * 删除疫情日报
     */
    @PreAuthorize("@ss.hasPermi('basic:daily:remove')" )
    @Log(title = "疫情日报" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{dailyIds}" )
    public AjaxResult remove(@PathVariable String[] dailyIds) {
        return AjaxResult.success(iScEpidemicDailyService.removeByIds(Arrays.asList(dailyIds)) ? 1 : 0);
    }

    /**
     * 根据所有学校批量 创建生成当天的疫情日报
     * @return
     */
    @Log(title = "疫情日报" , businessType = BusinessType.INSERT)
    @PostMapping("/batchTodayAddByOrg" )
    public int batchTodayAddByOrg(){
        return iScEpidemicDailyService.batchTodayAddByOrg();
    }
}
