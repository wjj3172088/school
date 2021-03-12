package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.PhysicalDefense;
import com.qh.basic.domain.vo.PhysicalDefenseExportVo;
import com.qh.basic.model.request.physicaldefense.AddPhysicalDefenseRequest;
import com.qh.basic.model.request.physicaldefense.ModifyPhysicalDefenseRequest;
import com.qh.basic.model.request.physicaldefense.PhysicalDefenseImportRequest;
import com.qh.basic.service.IPhysicalDefenseService;
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
 * 物防信息Controller
 *
 * @author 汪俊杰
 * @date 2021-01-22
 */
@RestController
@RequestMapping("/physicalDefense")
public class PhysicalDefenseController extends BaseController {

    @Autowired
    private IPhysicalDefenseService physicalDefenseService;

    /**
     * 查询物防信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:list')")
    @GetMapping("/list")
    public R<IPage<PhysicalDefense>> list(PhysicalDefense physicalDefense) {
        IPage<PhysicalDefense> list = physicalDefenseService.selectPhysicalDefenseListByPage(getPage(), physicalDefense);
        return R.ok(list);
    }

    /**
     * 导出物防信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:export')")
    @Log(title = "物防信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhysicalDefense physicalDefense) throws IOException {
        List<PhysicalDefenseExportVo> list = physicalDefenseService.selectExportList(physicalDefense);
        ExcelUtil<PhysicalDefenseExportVo> util = new ExcelUtil<PhysicalDefenseExportVo>(PhysicalDefenseExportVo.class);
        util.exportExcel(response, list, "物防信息");
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "物防信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<PhysicalDefenseImportRequest> util = new ExcelUtil<>(PhysicalDefenseImportRequest.class);
        List<PhysicalDefenseImportRequest> physicalDefenseExportVoList = util.importExcel(file.getInputStream());
        physicalDefenseService.importData(physicalDefenseExportVoList);
        return AjaxResult.success(1);
    }

    /**
     * 获取物防信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:query')")
    @GetMapping(value = "/{defenseId}")
    public AjaxResult getInfo(@PathVariable("defenseId") String defenseId) {
        return AjaxResult.success(physicalDefenseService.getById(defenseId));
    }

    /**
     * 新增物防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:add')")
    @Log(title = "物防信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AddPhysicalDefenseRequest request) {
        PhysicalDefense physicalDefense = new PhysicalDefense();
        BeanUtils.copyProperties(request, physicalDefense);
        physicalDefenseService.add(physicalDefense);
        return AjaxResult.success(1);
    }

    /**
     * 修改物防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:edit')")
    @Log(title = "物防信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModifyPhysicalDefenseRequest request) {
        PhysicalDefense physicalDefense = new PhysicalDefense();
        BeanUtils.copyProperties(request, physicalDefense);
        physicalDefenseService.modify(physicalDefense);
        return AjaxResult.success(1);
    }

    /**
     * 删除物防信息
     */
    @PreAuthorize("@ss.hasPermi('basic:physicalDefense:remove')")
    @Log(title = "物防信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{defenseIds}")
    public AjaxResult remove(@PathVariable String[] defenseIds) {
        return AjaxResult.success(physicalDefenseService.removeByIds(Arrays.asList(defenseIds)) ? 1 : 0);
    }
}
