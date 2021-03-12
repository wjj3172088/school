package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScNewsGroup;
import com.qh.basic.model.request.newsinfo.AddNewsInfoRequest;
import com.qh.basic.model.request.newsinfo.ModifyNewsInfoRequest;
import com.qh.basic.service.IScNewsGroupService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 咨询分组Controller
 *
 * @author 汪俊杰
 * @date 2021-01-18
 */
@RestController
@RequestMapping("/newsGroup")
public class ScNewsGroupController extends BaseController {

    @Autowired
    private IScNewsGroupService newsGroupService;

    /**
     * 查询咨询分组列表
     */
    @GetMapping("/list")
    public R<IPage<ScNewsGroup>> list(ScNewsGroup scNewsGroup) {
        IPage<ScNewsGroup> list = newsGroupService.selectScNewsGroupListByPage(getPage(), scNewsGroup);
        return R.ok(list);
    }

    /**
     * 新增咨询分组
     */
    @PreAuthorize("@ss.hasPermi('basic:newsGroup:add')")
    @Log(title = "咨询分组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddNewsInfoRequest request) {
        newsGroupService.add(request.getType(), request.getNewsInfoList());
        return AjaxResult.success(1);
    }

    /**
     * 修改咨询分组
     */
    @PreAuthorize("@ss.hasPermi('basic:newsGroup:edit')")
    @Log(title = "咨询分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModifyNewsInfoRequest request) {
        newsGroupService.modify(request.getNewsGroupId(), request.getType(), request.getNewsInfoList());
        return AjaxResult.success(1);
    }

    /**
     * 删除咨询分组
     */
    @PreAuthorize("@ss.hasPermi('basic:newsGroup:remove')")
    @Log(title = "咨询分组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{newsGroupIds}")
    public AjaxResult remove(@PathVariable String[] newsGroupIds) {
        newsGroupService.delete(Arrays.asList(newsGroupIds));
        return AjaxResult.success(1);
    }

    /**
     * 根据资讯分组id获取资讯列表
     */
    @PreAuthorize("@ss.hasPermi('basic:newsGroup:query')")
    @GetMapping(value = "/{newsGroupId}")
    public AjaxResult getInfo(@PathVariable("newsGroupId") String newsGroupId) {
        return AjaxResult.success(newsGroupService.selectByNewGroupId(newsGroupId));
    }
}
