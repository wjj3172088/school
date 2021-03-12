package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.SecurityStaff;
import com.qh.basic.domain.vo.SecurityStaffExportVo;
import com.qh.basic.service.ISecurityStaffService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 三防保安信息Controller
 *
 * @author 汪俊杰
 * @date 2021-01-21
 */
@RestController
@RequestMapping("/securityStaff")
public class SecurityStaffController extends BaseController {

    @Autowired
    private ISecurityStaffService securityStaffService;

    /**
     * 查询三防保安信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:list')")
    @GetMapping("/list")
    public R<IPage<SecurityStaff>> list(SecurityStaff securityStaff) {
        IPage<SecurityStaff> list = securityStaffService.selectSecurityStaffListByPage(getPage(), securityStaff);
        return R.ok(list);
    }

    /**
     * 导出三防保安信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:export')")
    @Log(title = "三防保安信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityStaff securityStaff) throws IOException {
        List<SecurityStaffExportVo> list = securityStaffService.selectExportList(securityStaff);
        ExcelUtil<SecurityStaffExportVo> util = new ExcelUtil<SecurityStaffExportVo>(SecurityStaffExportVo.class);
        util.exportExcel(response, list, "三防保安信息");
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "三防保安信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SecurityStaffExportVo> util = new ExcelUtil<>(SecurityStaffExportVo.class);
        List<SecurityStaffExportVo> securityStaffExportVoList = util.importExcel(file.getInputStream());
        securityStaffService.importData(securityStaffExportVoList);
        return AjaxResult.success(1);
    }

    /**
     * 获取三防保安信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") String staffId) {
        return AjaxResult.success(securityStaffService.getById(staffId));
    }

    /**
     * 新增三防保安信息
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:add')")
    @Log(title = "三防保安信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityStaff securityStaff) {
        securityStaffService.add(securityStaff);
        return AjaxResult.success(1);
    }

    /**
     * 修改三防保安信息
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:edit')")
    @Log(title = "三防保安信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityStaff securityStaff) {
        securityStaffService.modify(securityStaff);
        return AjaxResult.success(1);
    }

    /**
     * 删除三防保安信息
     */
    @PreAuthorize("@ss.hasPermi('basic:securityStaff:remove')")
    @Log(title = "三防保安信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{staffIds}")
    public AjaxResult remove(@PathVariable String[] staffIds) {
        return AjaxResult.success(securityStaffService.removeByIds(Arrays.asList(staffIds)) ? 1 : 0);
    }
}
