package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.vo.ClassExportVo;
import com.qh.basic.model.request.scclass.ClassSearchRequest;
import com.qh.basic.service.IScClassService;
import com.qh.common.core.utils.StringUtils;
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
 * 班级Controller
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/scClass")
public class ScClassController extends BaseController {
    @Autowired
    private IScClassService iScClassService;

    /**
     * 查询班级列表
     */
    @PreAuthorize("@ss.hasPermi('basic:class:list')")
    @GetMapping("/list")
    public R<IPage<ScClass>> list(ClassSearchRequest request) {
        return R.ok(iScClassService.selectListByPage(getPage(), request));
    }

    /**
     * 导出班级列表
     */
    @PreAuthorize("@ss.hasPermi('basic:class:export')")
    @Log(title = "班级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClassSearchRequest request) throws IOException {
        List<ClassExportVo> list = iScClassService.findAllByExport(request);
        ExcelUtil<ClassExportVo> util = new ExcelUtil<ClassExportVo>(ClassExportVo.class);
        util.exportExcel(response, list, "班级");
    }

    /**
     * 获取班级详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:class:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") String classId) {
        return AjaxResult.success(iScClassService.queryByClassId(classId));
    }

    /**
     * 新增班级
     */
    @PreAuthorize("@ss.hasPermi('basic:class:add')")
    @Log(title = "班级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScClass scClass) {
        iScClassService.saveClass(scClass);
        return AjaxResult.success(1);
    }

    /**
     * 修改班级
     */
    @PreAuthorize("@ss.hasPermi('basic:class:edit')")
    @Log(title = "班级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScClass scClass) {
        if (StringUtils.isEmpty(scClass.getClassId())) {
            return AjaxResult.error("班级id不能为空");
        }
        iScClassService.saveClass(scClass);
        return AjaxResult.success(1);
    }

    /**
     * 删除班级
     */
    @PreAuthorize("@ss.hasPermi('basic:class:remove')")
    @Log(title = "班级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable String[] classIds) {
        iScClassService.batchDeleteById(Arrays.asList(classIds));
        return AjaxResult.success(1);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "班级", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:class:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ScClass> util = new ExcelUtil<ScClass>(ScClass.class);
        List<ScClass> classList = util.importExcel(file.getInputStream());
        iScClassService.importData(classList);
        return AjaxResult.success(1);
    }

    /**
     * 获取当前登陆用户的学校的班级列表
     */
    @PreAuthorize("@ss.hasPermi('basic:class:list:currentOrgId')")
    @GetMapping("/list/currentOrgId")
    public R getClassListByOrgId() {
        List<ScClass> list = iScClassService.findAllByOrgId();
        return R.ok(list);
    }

    /**
     * 解绑班主任
     */
    @PreAuthorize("@ss.hasPermi('basic:class:unbind')")
    @Log(title = "班主任解绑", businessType = BusinessType.OTHER)
    @PostMapping("/unbind")
    public AjaxResult unbind(@RequestBody List<String> classIdList) {
        iScClassService.unbind(classIdList);
        return AjaxResult.success(1);
    }
}