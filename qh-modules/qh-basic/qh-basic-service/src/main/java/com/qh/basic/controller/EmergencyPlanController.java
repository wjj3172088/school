package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.EmergencyPlan;
import com.qh.basic.service.IEmergencyPlanService;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 应急预案Controller
 *
 * @author 汪俊杰
 * @date 2021-01-20
 */
@RestController
@RequestMapping("/emergencyPlan")
public class EmergencyPlanController extends BaseController {

    @Autowired
    private IEmergencyPlanService emergencyPlanService;

    /**
     * 查询应急预案列表
     */
    @PreAuthorize("@ss.hasPermi('basic:emergencyPlan:list')")
    @GetMapping("/list")
    public R<IPage<EmergencyPlan>> list(EmergencyPlan emergencyPlan) {
        IPage<EmergencyPlan> list = emergencyPlanService.selectEmergencyPlanListByPage(getPage(), emergencyPlan);
        return R.ok(list);
    }

    /**
     * 获取应急预案详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:emergencyPlan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") String planId) {
        return AjaxResult.success(emergencyPlanService.getById(planId));
    }

    /**
     * 新增应急预案
     */
    @PreAuthorize("@ss.hasPermi('basic:emergencyPlan:add')")
    @Log(title = "应急预案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmergencyPlan emergencyPlan) {
        emergencyPlan.setPlanId(UUIDG.generate());
        emergencyPlan.setOrgId(SecurityUtils.getOrgId());
        emergencyPlan.setOrgName(SecurityUtils.getOrgName());
        emergencyPlan.setCreateDate(DateUtil.getSystemSeconds());
        emergencyPlan.setUpdateDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(emergencyPlanService.save(emergencyPlan) ? 1 : 0);
    }

    /**
     * 修改应急预案
     */
    @PreAuthorize("@ss.hasPermi('basic:emergencyPlan:edit')")
    @Log(title = "应急预案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmergencyPlan emergencyPlan) {
        emergencyPlanService.modify(emergencyPlan);
        return AjaxResult.success(1);
    }

    /**
     * 删除应急预案
     */
    @PreAuthorize("@ss.hasPermi('basic:emergencyPlan:remove')")
    @Log(title = "应急预案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable String[] planIds) {
        return AjaxResult.success(emergencyPlanService.removeByIds(Arrays.asList(planIds)) ? 1 : 0);
    }
}
