package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScNotice;
import com.qh.basic.domain.vo.NoticeViewVo;
import com.qh.basic.model.request.notice.NoticeAddRequest;
import com.qh.basic.model.request.notice.NoticeModifyRequest;
import com.qh.basic.model.request.notice.NoticeViewRequest;
import com.qh.basic.service.IScNoticeService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 学校公告记录Controller
 *
 * @author 汪俊杰
 * @date 2020-11-25
 */
@RestController
@RequestMapping("/notice")
public class ScNoticeController extends BaseController {

    @Autowired
    private IScNoticeService iScNoticeService;

    /**
     * 查询学校公告记录列表
     */
    @GetMapping("/list")
    public R<IPage<ScNotice>> list(ScNotice scNotice) {
        IPage<ScNotice> list = iScNoticeService.selectScNoticeListByPage(getPage(), scNotice);
        return R.ok(list);
    }

    /**
     * 获取学校公告记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") String noticeId) {
        return AjaxResult.success(iScNoticeService.selectDetail(noticeId));
    }

    /**
     * 新增学校公告记录
     */
    @PreAuthorize("@ss.hasPermi('basic:notice:add')")
    @Log(title = "学校公告记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody NoticeAddRequest request) {
        iScNoticeService.addNotice(request, SecurityUtils.getLoginUser());
        return AjaxResult.success(1);
    }

    /**
     * 修改学校公告记录
     */
    @PreAuthorize("@ss.hasPermi('basic:notice:edit')")
    @Log(title = "学校公告记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NoticeModifyRequest request) {
        iScNoticeService.modifyNotice(request, SecurityUtils.getLoginUser());
        return AjaxResult.success(1);
    }

    /**
     * 删除学校公告记录
     */
    @PreAuthorize("@ss.hasPermi('basic:notice:remove')")
    @Log(title = "学校公告记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeId}")
    public AjaxResult remove(@PathVariable String noticeId) {
        iScNoticeService.deleteNotice(noticeId);
        return AjaxResult.success(1);
    }

    /**
     * 分页查询已读未读人员列表
     */
    @GetMapping("/view/list")
    public R<IPage<NoticeViewVo>> list(@Validated NoticeViewRequest request) {
        IPage<NoticeViewVo> list = iScNoticeService.selectViewListByPage(getPage(), request.getBizId(), request.getLook());
        return R.ok(list);
    }
}
