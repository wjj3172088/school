package com.qh.basic.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScPublicity;
import com.qh.basic.service.IScPublicityService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.oss.PicUtils;
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
 * 安全宣传Controller
 * 
 * @author 黄道权
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/publicity" )
public class ScPublicityController extends BaseController {

    @Autowired
    private IScPublicityService iScPublicityService;

    /**
     * 查询安全宣传列表
     */
    @GetMapping("/list")
    public R<IPage<ScPublicity>> list(ScPublicity scPublicity)
    {
        IPage<ScPublicity> list = iScPublicityService.selectScPublicityListByPage(getPage(),scPublicity);
        return R.ok(list);
    }

    /**
     * 导出安全宣传列表
     */
    @PreAuthorize("@ss.hasPermi('basic:publicity:export')" )
    @Log(title = "安全宣传" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScPublicity scPublicity)  throws IOException {
        LambdaQueryWrapper<ScPublicity> lqw = new LambdaQueryWrapper<ScPublicity>(scPublicity);
        List<ScPublicity> list = iScPublicityService.list(lqw);
        ExcelUtil<ScPublicity> util = new ExcelUtil<ScPublicity>(ScPublicity. class);
        util.exportExcel(response, list, "安全宣传");
    }

    /**
     * 获取安全宣传详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:publicity:query')" )
    @GetMapping(value = "/{publicityId}" )
    public AjaxResult getInfo(@PathVariable("publicityId" ) String publicityId) {
        return AjaxResult.success(iScPublicityService.getById(publicityId));
    }

    /**
     * 新增安全宣传
     */
    @PreAuthorize("@ss.hasPermi('basic:publicity:add')" )
    @Log(title = "安全宣传" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScPublicity scPublicity) {
        if(StringUtils.isBlank(scPublicity.getPicurl())) {
            return AjaxResult.error("图片不能为空,请上传图片");
        }
        try {
            List<String> picurlsResult = JSON.parseArray(scPublicity.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        scPublicity.setOrgId(SecurityUtils.getOrgId());
        scPublicity.setCreateDate(DateUtil.getSystemSeconds());
        scPublicity.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScPublicityService.save(scPublicity) ? 1 : 0);
    }

    /**
     * 修改安全宣传
     */
    @PreAuthorize("@ss.hasPermi('basic:publicity:edit')" )
    @Log(title = "安全宣传" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScPublicity scPublicity) {
        if(StringUtils.isBlank(scPublicity.getPicurl())) {
            return AjaxResult.error("图片不能为空,请上传图片");
        }
        try {
            List<String> picurlsResult = JSON.parseArray(scPublicity.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        scPublicity.setOrgId(SecurityUtils.getOrgId());
        scPublicity.setModifyDate(DateUtil.getSystemSeconds());
        return AjaxResult.success(iScPublicityService.updateById(scPublicity) ? 1 : 0);
    }

    /**
     * 删除安全宣传
     */
    @PreAuthorize("@ss.hasPermi('basic:publicity:remove')" )
    @Log(title = "安全宣传" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{publicityIds}" )
    public AjaxResult remove(@PathVariable String[] publicityIds) {
        return AjaxResult.success(iScPublicityService.removeByIds(Arrays.asList(publicityIds)) ? 1 : 0);
    }
}
