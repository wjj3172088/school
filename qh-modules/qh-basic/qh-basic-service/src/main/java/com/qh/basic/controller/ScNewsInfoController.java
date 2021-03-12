package com.qh.basic.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScNewsInfo;
import com.qh.basic.service.IScNewsInfoService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 新闻资讯Controller
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/newsInfo" )
public class ScNewsInfoController extends BaseController {

    @Autowired
    private IScNewsInfoService iScNewsInfoService;

    /**
     * 查询新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:list')")
    @GetMapping("/list")
    public R<IPage<ScNewsInfo>> list(ScNewsInfo scNewsInfo)
    {
        IPage<ScNewsInfo> list = iScNewsInfoService.selectScNewsInfoListByPage(getPage(),scNewsInfo);
        return R.ok(list);
    }

    /**
     * 导出新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:export')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScNewsInfo scNewsInfo)  throws IOException {
        LambdaQueryWrapper<ScNewsInfo> lqw = new LambdaQueryWrapper<ScNewsInfo>(scNewsInfo);
        List<ScNewsInfo> list = iScNewsInfoService.list(lqw);
        ExcelUtil<ScNewsInfo> util = new ExcelUtil<ScNewsInfo>(ScNewsInfo. class);
        util.exportExcel(response, list, "新闻资讯");
    }

    /**
     * 获取新闻资讯详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:query')" )
    @GetMapping(value = "/{publicityId}" )
    public AjaxResult getInfo(@PathVariable("publicityId" ) String publicityId) {
        return AjaxResult.success(iScNewsInfoService.getById(publicityId));
    }

    /**
     * 新增新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:add')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScNewsInfo scNewsInfo) {
        if(StringUtils.isBlank(scNewsInfo.getPicurl())) {
            return AjaxResult.error("图片不能为空,请上传图片");
        }
        try {
            List<String> picurlsResult = JSON.parseArray(scNewsInfo.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        scNewsInfo.setOrgId(SecurityUtils.getOrgId());
        scNewsInfo.setCreateDate(DateUtil.getSystemSeconds());
        scNewsInfo.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScNewsInfoService.save(scNewsInfo) ? 1 : 0);
    }

    /**
     * 修改新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:edit')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScNewsInfo scNewsInfo) {
        if(StringUtils.isBlank(scNewsInfo.getPicurl())) {
            return AjaxResult.error("图片不能为空,请上传图片");
        }
        try {
            List<String> picurlsResult = JSON.parseArray(scNewsInfo.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        scNewsInfo.setOrgId(SecurityUtils.getOrgId());
        scNewsInfo.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScNewsInfoService.updateById(scNewsInfo) ? 1 : 0);
    }

    /**
     * 删除新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('basic:newsInfo:remove')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{publicityIds}" )
    public AjaxResult remove(@PathVariable String[] publicityIds) {
        return AjaxResult.success(iScNewsInfoService.removeByIds(Arrays.asList(publicityIds)) ? 1 : 0);
    }
}
