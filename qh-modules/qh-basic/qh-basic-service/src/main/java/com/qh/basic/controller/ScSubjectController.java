package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScSubject;
import com.qh.basic.model.request.subject.AddSubjectRequest;
import com.qh.basic.model.request.subject.ModifySubjectRequest;
import com.qh.basic.service.IScSubjectService;
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
import java.util.List;

/**
 * 学校科目Controller
 *
 * @author 汪俊杰
 * @date 2020-12-04
 */
@RestController
@RequestMapping("/subject")
public class ScSubjectController extends BaseController {
    @Autowired
    private IScSubjectService subjectService;

    /**
     * 查询学校科目列表
     */
    @GetMapping("/org/list")
    public R<List<ScSubject>> list() {
        List<ScSubject> list = subjectService.selectCurrentAll();
        return R.ok(list);
    }

    /**
     * 查询学校科目列表
     */
    @PreAuthorize("@ss.hasPermi('basic:subject:list')")
    @GetMapping("/list")
    public R<IPage<ScSubject>> list(ScSubject scSubject) {
        IPage<ScSubject> list = subjectService.selectListByPage(getPage(), scSubject);
        return R.ok(list);
    }

    /**
     * 获取学校科目详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:subject:query')")
    @GetMapping(value = "/{subjectId}")
    public AjaxResult getInfo(@PathVariable("subjectId") String subjectId) {
        return AjaxResult.success(subjectService.getById(subjectId));
    }

    /**
     * 新增学校科目
     */
    @PreAuthorize("@ss.hasPermi('basic:subject:add')")
    @Log(title = "学校科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated AddSubjectRequest request) {
        subjectService.add(request.getSubjectName());
        return AjaxResult.success(1);
    }

    /**
     * 修改学校科目
     */
    @PreAuthorize("@ss.hasPermi('basic:subject:edit')")
    @Log(title = "学校科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ModifySubjectRequest request) {
        subjectService.modify(request.getSubjectId(), request.getSubjectName());
        return AjaxResult.success(1);
    }

    /**
     * 删除学校科目
     */
    @PreAuthorize("@ss.hasPermi('basic:subject:remove')")
    @Log(title = "学校科目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{subjectIds}")
    public AjaxResult remove(@PathVariable String[] subjectIds) {
        return AjaxResult.success(subjectService.removeByIds(Arrays.asList(subjectIds)) ? 1 : 0);
    }
}
