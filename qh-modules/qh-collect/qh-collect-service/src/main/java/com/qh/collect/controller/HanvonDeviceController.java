package com.qh.collect.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.qh.common.core.utils.StringUtils;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.OrgService;
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
import com.qh.collect.domain.HanvonDevice;
import com.qh.collect.service.IHanvonDeviceService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉王考勤机设备管理Controller
 * 
 * @author huangdaoquan
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/hanvondevice" )
public class HanvonDeviceController extends BaseController {

    @Autowired
    private IHanvonDeviceService iHanvonDeviceService;

    @Autowired
    private OrgService orgService;

    /**
     * 查询汉王考勤机设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:list')")
    @GetMapping("/list")
    public R<IPage<HanvonDevice>> list(HanvonDevice hanvonDevice)
    {
        IPage<HanvonDevice> list = iHanvonDeviceService.selectHanvonDeviceListByPage(getPage(),hanvonDevice);
        return R.ok(list);
    }

    /**
     * 导出汉王考勤机设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:export')" )
    @Log(title = "汉王考勤机设备管理" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response, HanvonDevice hanvonDevice)  throws IOException {
        LambdaQueryWrapper<HanvonDevice> lqw = new LambdaQueryWrapper<HanvonDevice>(hanvonDevice);
        List<HanvonDevice> list = iHanvonDeviceService.list(lqw);
        ExcelUtil<HanvonDevice> util = new ExcelUtil<HanvonDevice>(HanvonDevice. class);
        util.exportExcel(response, list, "汉王考勤机设备管理");
    }

    /**
     * 获取汉王考勤机设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:query')" )
    @GetMapping(value = "/{hdId}" )
    public AjaxResult getInfo(@PathVariable("hdId" ) String hdId) {
        return AjaxResult.success(iHanvonDeviceService.getById(hdId));
    }

    /**
     * 新增汉王考勤机设备管理
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:add')" )
    @Log(title = "汉王考勤机设备管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HanvonDevice hanvonDevice) {
        if(StringUtils.isBlank(hanvonDevice.getOrgId())){
            return AjaxResult.error("归属学校不能为空");
        }
        hanvonDevice.setOrgName(orgService.selectOrgNameById(hanvonDevice.getOrgId()));
        hanvonDevice.setCreateUserName(SecurityUtils.getUsername());
        hanvonDevice.setCreateDate(System.currentTimeMillis() / 1000);
        return AjaxResult.success(iHanvonDeviceService.save(hanvonDevice) ? 1 : 0);
    }

    /**
     * 修改汉王考勤机设备管理
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:edit')" )
    @Log(title = "汉王考勤机设备管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HanvonDevice hanvonDevice) {
        if(StringUtils.isBlank(hanvonDevice.getOrgId())){
            return AjaxResult.error("归属学校不能为空");
        }
        hanvonDevice.setOrgName(orgService.selectOrgNameById(hanvonDevice.getOrgId()));
        hanvonDevice.setUpdateUserName(SecurityUtils.getUsername());
        hanvonDevice.setUpdateDate(System.currentTimeMillis() / 1000);
        return AjaxResult.success(iHanvonDeviceService.updateById(hanvonDevice) ? 1 : 0);
    }

    /**
     * 删除汉王考勤机设备管理
     */
    @PreAuthorize("@ss.hasPermi('collect:hanvondevice:remove')" )
    @Log(title = "汉王考勤机设备管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{hdIds}" )
    public AjaxResult remove(@PathVariable String[] hdIds) {
        return AjaxResult.success(iHanvonDeviceService.removeByIds(Arrays.asList(hdIds)) ? 1 : 0);
    }
}
