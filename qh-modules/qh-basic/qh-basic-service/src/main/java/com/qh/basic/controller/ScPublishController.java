package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScPublish;
import com.qh.basic.service.IScPublishService;
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
 * 公告模板Controller
 * 
 * @author 汪俊杰
 * @date 2020-11-24
 */
@RestController
@RequestMapping("/publish" )
public class ScPublishController extends BaseController {

    @Autowired
    private IScPublishService publishService;

    /**
     * 查询公告模板列表
     */
    @PreAuthorize("@ss.hasPermi('basic:publish:list')")
    @GetMapping("/list")
    public R<IPage<ScPublish>> list(ScPublish scPublish)
    {
        IPage<ScPublish> list = publishService.selectScPublishListByPage(getPage(),scPublish);
        return R.ok(list);
    }

    /**
     * 获取公告模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:publish:query')" )
    @GetMapping(value = "/{publishId}" )
    public AjaxResult getInfo(@PathVariable("publishId" ) String publishId) {
        return AjaxResult.success(publishService.getById(publishId));
    }

    /**
     * 新增公告模板
     */
    @PreAuthorize("@ss.hasPermi('basic:publish:add')" )
    @Log(title = "公告模板" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ScPublish scPublish) {
        publishService.add(scPublish);
        return AjaxResult.success();
    }

    /**
     * 修改公告模板
     */
    @PreAuthorize("@ss.hasPermi('basic:publish:edit')" )
    @Log(title = "公告模板" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ScPublish scPublish) {
        publishService.modify(scPublish);
        return AjaxResult.success(1);
    }

    /**
     * 删除公告模板
     */
    @PreAuthorize("@ss.hasPermi('basic:publish:remove')" )
    @Log(title = "公告模板" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{publishIds}" )
    public AjaxResult remove(@PathVariable String[] publishIds) {
        return AjaxResult.success(publishService.removeByIds(Arrays.asList(publishIds)) ? 1 : 0);
    }
}
