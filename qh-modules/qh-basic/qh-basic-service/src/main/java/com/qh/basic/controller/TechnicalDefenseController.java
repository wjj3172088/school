package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.TechnicalDefense;
import com.qh.basic.domain.vo.TechnicalDefenseExportVo;
import com.qh.basic.model.request.technicaldefense.AddTechnicalDefenseRequest;
import com.qh.basic.model.request.technicaldefense.ImportTechnicalDefenseRequest;
import com.qh.basic.model.request.technicaldefense.ModifyTechnicalDefenseRequest;
import com.qh.basic.service.ITechnicalDefenseService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 技防信息Controller
 *
 * @author 汪俊杰
 * @date 2021-01-25
 */
@RestController
@RequestMapping("/technicalDefense")
public class TechnicalDefenseController extends BaseController {

    @Autowired
    private ITechnicalDefenseService technicalDefenseService;

    /**
     * 查询技防信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:list')")
    @GetMapping("/list")
    public R<IPage<TechnicalDefense>> list(TechnicalDefense technicalDefense) {
        IPage<TechnicalDefense> list = technicalDefenseService.selectTechnicalDefenseListByPage(getPage(), technicalDefense);
        return R.ok(list);
    }

    /**
     * 导出技防信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:export')")
    @Log(title = "技防信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TechnicalDefense technicalDefense) throws IOException {
        List<TechnicalDefenseExportVo> list = technicalDefenseService.selectExportList(technicalDefense);
        ExcelUtil<TechnicalDefenseExportVo> util = new ExcelUtil<>(TechnicalDefenseExportVo.class);
        util.exportExcel(response, list, "技防信息");
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "技防信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ImportTechnicalDefenseRequest> util = new ExcelUtil<>(ImportTechnicalDefenseRequest.class);
        List<ImportTechnicalDefenseRequest> importTechnicalDefenseRequestList = util.importExcel(file.getInputStream());
        technicalDefenseService.importData(importTechnicalDefenseRequestList);
        return AjaxResult.success(1);
    }

    /**
     * 获取技防信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:query')")
    @GetMapping(value = "/{defenseId}")
    public AjaxResult getInfo(@PathVariable("defenseId") String defenseId) {
        return AjaxResult.success(technicalDefenseService.getById(defenseId));
    }

    /**
     * 新增技防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:add')")
    @Log(title = "技防信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AddTechnicalDefenseRequest request) {
        TechnicalDefense technicalDefense = new TechnicalDefense();
        BeanUtils.copyProperties(request, technicalDefense);
        technicalDefenseService.add(technicalDefense);
        return AjaxResult.success(1);
    }

    /**
     * 修改技防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:edit')")
    @Log(title = "技防信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModifyTechnicalDefenseRequest request) {
        TechnicalDefense technicalDefense = new TechnicalDefense();
        BeanUtils.copyProperties(request, technicalDefense);
        technicalDefenseService.modify(technicalDefense);
        return AjaxResult.success(technicalDefenseService.updateById(technicalDefense) ? 1 : 0);
    }

    /**
     * 删除技防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:technicalDefense:remove')")
    @Log(title = "技防信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{defenseIds}")
    public AjaxResult remove(@PathVariable String[] defenseIds) {
        return AjaxResult.success(technicalDefenseService.removeByIds(Arrays.asList(defenseIds)) ? 1 : 0);
    }
}
