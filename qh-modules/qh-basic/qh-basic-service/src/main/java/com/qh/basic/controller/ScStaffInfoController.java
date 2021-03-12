package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.model.request.staffinfo.StaffInfoImportRequest;
import com.qh.basic.model.request.staffinfo.StaffInfoSaveRequest;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import com.qh.basic.service.IScStaffInfoService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 职工信息Controller
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@RestController
@RequestMapping("/staffInfo")
public class ScStaffInfoController extends BaseController {

    @Autowired
    private IScStaffInfoService iScStaffInfoService;

    /**
     * 查询职工信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:list')")
    @GetMapping("/list")
    public R<IPage<Map>> list(StaffInfoSearchRequest request) {
        IPage<Map> list = iScStaffInfoService.selectScStaffInfoListByPage(getPage(), request);
        return R.ok(list);
    }

    /**
     * 获取职工信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") String staffId) {
        return AjaxResult.success(iScStaffInfoService.queryByStaffId(staffId));
    }

    /**
     * 新增职工信息
     */
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:add')")
    @Log(title = "职工信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StaffInfoSaveRequest request) {
        iScStaffInfoService.saveStaffInfo(request);
        return AjaxResult.success(1);
    }

    /**
     * 修改职工信息
     */
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:edit')")
    @Log(title = "职工信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StaffInfoSaveRequest request) {
        iScStaffInfoService.saveStaffInfo(request);
        return AjaxResult.success(1);
    }

    /**
     * 删除职工信息
     */
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:remove')")
    @Log(title = "职工信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accIds}")
    public AjaxResult remove(@PathVariable String[] accIds) {
        iScStaffInfoService.batchDeleteById(Arrays.asList(accIds));
        return AjaxResult.success(1);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "职工信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:staffInfo:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<StaffInfoImportRequest> util = new ExcelUtil<StaffInfoImportRequest>(StaffInfoImportRequest.class);
        List<StaffInfoImportRequest> staffInfoList = util.importExcel(file.getInputStream());
        iScStaffInfoService.importData(staffInfoList);
        return AjaxResult.success(1);
    }

    @GetMapping("/selectByStaffName")
    public ScStaffInfo selectByStaffName(String orgId, String staffName, String jobNumber){
        return iScStaffInfoService.selectByStaffName(orgId,staffName,jobNumber);
    }

    /**
     * 同步职工健康码状态
     * @param staffId
     * @param healthState
     * @return
     */
    @GetMapping("/syncStaffHealthState")
    public Integer syncStaffHealthState(String staffId,int healthState){
        return iScStaffInfoService.syncStaffHealthState(staffId,healthState);
    }

    /**
     * 根据条件获取所有在职职工信息列表
     */
    @GetMapping("/findAllData")
    public List<Map> findAllData(StaffInfoSearchRequest request) throws IOException {
        List<Map> list = iScStaffInfoService.selectStaffInfoList(request);
        return list;
    }
}
