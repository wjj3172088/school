package com.qh.basic.controller;

import com.alibaba.fastjson.JSON;

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
import com.qh.basic.domain.ScDutyRecord;
import com.qh.basic.service.IScDutyRecordService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 值班上报记录Controller
 * 
 * @author huangdaoquan
 * @date 2021-01-19
 */
@RestController
@RequestMapping("/record" )
public class ScDutyRecordController extends BaseController {

    @Autowired
    private IScDutyRecordService iScDutyRecordService;

    /**
     * 查询值班上报记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:record:list')")
    @GetMapping("/list")
    public R<IPage<ScDutyRecord>> list(ScDutyRecord scDutyRecord)
    {
        IPage<ScDutyRecord> list = iScDutyRecordService.selectScDutyRecordListByPage(getPage(),scDutyRecord);
        return R.ok(list);
    }

    /**
     * 导出值班上报记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:record:export')" )
    @Log(title = "值班上报记录" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, ScDutyRecord scDutyRecord)  throws IOException {
        List<ScDutyRecord> list = iScDutyRecordService.selectScDutyRecordList(scDutyRecord);
        ExcelUtil<ScDutyRecord> util = new ExcelUtil<ScDutyRecord>(ScDutyRecord. class);
        util.exportExcel(response, list, "值班上报记录");
    }

    /**
     * 获取值班上报记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:record:query')" )
    @GetMapping(value = "/{recordId}" )
    public AjaxResult getInfo(@PathVariable("recordId" ) String recordId) {
        return AjaxResult.success(iScDutyRecordService.getById(recordId));
    }

    /**
     * 新增值班上报记录
     */
    @PreAuthorize("@ss.hasPermi('basic:record:add')" )
    @Log(title = "值班上报记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScDutyRecord scDutyRecord) {
        scDutyRecord.setOrgName(SecurityUtils.getOrgName());
        scDutyRecord.setOrgId(SecurityUtils.getOrgId());
        scDutyRecord.setCreateDate(DateUtil.getSystemSeconds());
        scDutyRecord.setModifyDate(DateUtil.getSystemSeconds());
        scDutyRecord.setPublisherName(SecurityUtils.getUsername());
        scDutyRecord.setPublisherId(SecurityUtils.getUserId().toString());
        try {
            List<String> picurlsResult = JSON.parseArray(scDutyRecord.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        return AjaxResult.success(iScDutyRecordService.save(scDutyRecord) ? 1 : 0);
    }

    /**
     * 修改值班上报记录
     */
    @PreAuthorize("@ss.hasPermi('basic:record:edit')" )
    @Log(title = "值班上报记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScDutyRecord scDutyRecord) {
        scDutyRecord.setModifyDate(DateUtil.getSystemSeconds());
        try {
            List<String> picurlsResult = JSON.parseArray(scDutyRecord.getPicurl(),String.class);
        }catch (Exception ex){
            return AjaxResult.error("图片格式有异常,请上传正确的图片数组格式");
        }
        return AjaxResult.success(iScDutyRecordService.updateById(scDutyRecord) ? 1 : 0);
    }

    /**
     * 删除值班上报记录
     */
    @PreAuthorize("@ss.hasPermi('basic:record:remove')" )
    @Log(title = "值班上报记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}" )
    public AjaxResult remove(@PathVariable String[] recordIds) {
        return AjaxResult.success(iScDutyRecordService.removeByIds(Arrays.asList(recordIds)) ? 1 : 0);
    }
}
