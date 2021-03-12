package com.qh.basic.controller;

import com.qh.basic.model.request.school.ModifySchoolExtRequest;
import com.qh.basic.model.response.graduate.GraduateInfoResponse;
import com.qh.basic.service.IScClassService;
import com.qh.basic.service.ISchoolExtService;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/25 14:07
 * @Description: 学校管理
 */
@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private IScClassService classService;
    @Autowired
    private ISchoolExtService schoolExtService;

    /**
     * 升学
     */
    @PreAuthorize("@ss.hasPermi('basic:school:upgrade')")
    @Log(title = "学校", businessType = BusinessType.UPGRADE)
    @PostMapping("/upgrade")
    public AjaxResult upgrade() {
        classService.upgrade();
        return AjaxResult.success(1);
    }

    /**
     * 毕业
     */
    @PreAuthorize("@ss.hasPermi('basic:school:graduate')")
    @Log(title = "学校", businessType = BusinessType.GRADUATE)
    @PostMapping("/graduate")
    public AjaxResult graduate() {
        classService.graduate();
        return AjaxResult.success(1);
    }

    /**
     * 获取毕业信息
     */
    @GetMapping("/graduate/info")
    public R<GraduateInfoResponse> list() {
        GraduateInfoResponse response = classService.selectGraduateInfo();
        return R.ok(response);
    }

    /**
     * 获取学校扩展信息详细信息
     */
    @GetMapping("/info")
    public AjaxResult getInfo() {
        return AjaxResult.success(schoolExtService.selectSchoolByOrgId(SecurityUtils.getOrgId()));
    }

    /**
     * 修改学校扩展信息
     */
    @PreAuthorize("@ss.hasPermi('basic:school:edit')")
    @Log(title = "学校扩展信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ModifySchoolExtRequest request) {
        schoolExtService.modify(request);
        return AjaxResult.success(1);
    }
}
