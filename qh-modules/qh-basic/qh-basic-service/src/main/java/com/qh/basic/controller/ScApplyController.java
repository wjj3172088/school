package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScApply;
import com.qh.basic.enums.SchoolApplyEnum;
import com.qh.basic.service.IScApplyService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息数据申请Controller
 *
 * @author 汪俊杰
 * @date 2020-12-24
 */
@RestController
@RequestMapping("/apply")
public class ScApplyController extends BaseController {

    @Autowired
    private IScApplyService iScApplyService;

    /**
     * 查询转班列表
     */
    @PreAuthorize("@ss.hasPermi('basic:apply:transfer:list')")
    @GetMapping("/transfer/list")
    public R<IPage<ScApply>> list(ScApply scApply) {
        scApply.setApplyType(SchoolApplyEnum.TypeEnum.CHANGE_CLASS.getCode());
        IPage<ScApply> list = iScApplyService.selectScApplyListByPage(getPage(), scApply);
        return R.ok(list);
    }

    /**
     * 获取信息数据申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:apply:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") Long applyId) {
        return AjaxResult.success(iScApplyService.getById(applyId));
    }
}
