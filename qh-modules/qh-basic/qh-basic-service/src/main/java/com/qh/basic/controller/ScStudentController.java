package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.StudentExportVo;
import com.qh.basic.model.request.student.*;
import com.qh.basic.service.IScStudentService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 学生信息Controller
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@RestController
@RequestMapping("/student")
public class ScStudentController extends BaseController {

    @Autowired
    private IScStudentService studentService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:student:list')")
    @GetMapping("/list")
    public R<IPage<Map>> list(StudentSearchRequest request) {
        IPage<Map> list = studentService.selectScStudentListByPage(getPage(), request);
        return R.ok(list);
    }

    /**
     * 根据班级、姓名或手机号查询学生信息列表
     */
    @GetMapping("/communication")
    public R<List<Map>> maillist(@Validated StudentCommunicationRequest request) {
        List<Map> list = studentService.selectList(request.getClassId(), request.getParaValue());
        return R.ok(list);
    }

    /**
     * 转班
     */
    @PreAuthorize("@ss.hasPermi('basic:student:transfer')")
    @Log(title = "学生信息", businessType = BusinessType.TRANSFER)
    @PostMapping("/transfer")
    public AjaxResult transfer(@RequestBody @Validated StudentTransferRequest request) {
        studentService.transfer(request.getStuIdList(), request.getClassId());
        return AjaxResult.success(1);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "学生信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:student:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<StudentImportRequest> util = new ExcelUtil<>(StudentImportRequest.class);
        List<StudentImportRequest> teacherList = util.importExcel(file.getInputStream());
        studentService.importData(teacherList);
        return AjaxResult.success(1);
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('basic:student:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StudentModifyRequest request) {
        studentService.modify(request);
        return AjaxResult.success(1);
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('basic:student:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{stuIds}")
    public AjaxResult remove(@PathVariable String[] stuIds) {
        studentService.deleteByStuIdList(Arrays.asList(stuIds));
        return AjaxResult.success(1);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:student:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentSearchRequest request) throws IOException {
        List<StudentExportVo> list = studentService.selectExportList(request);
        ExcelUtil<StudentExportVo> util = new ExcelUtil<StudentExportVo>(StudentExportVo.class);
        util.exportExcel(response, list, "学生信息");
    }
}
