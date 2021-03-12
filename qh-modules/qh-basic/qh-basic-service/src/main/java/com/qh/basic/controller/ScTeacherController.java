package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.StaffHealthVo;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.model.request.teacher.TeacherCommunicationRequest;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.basic.model.request.teacher.TeacherImportRequest;
import com.qh.basic.api.model.request.teacher.TeacherSaveRequest;
import com.qh.basic.service.IScTeacherService;
import com.qh.common.core.utils.StringUtils;
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
 * 教师信息Controller
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@RestController
@RequestMapping("/teacher")
public class ScTeacherController extends BaseController {

    @Autowired
    private IScTeacherService iScTeacherService;

    /**
     * 查询教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:list')")
    @GetMapping("/list")
    public R<IPage<Map>> list(ScTeacher scTeacher) {
        IPage<Map> list = iScTeacherService.selectScTeacherListByPage(getPage(), scTeacher);
        return R.ok(list);
    }

    /**
     * 查询教职工健康状态信息集合
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:healthAllList')")
    @GetMapping("/healthAllList")
    public R<IPage<StaffHealthVo>> healthAllList(ScTeacher scTeacher) {
        IPage<StaffHealthVo> list = iScTeacherService.selectHealthListByPage(getPage(), scTeacher);
        return R.ok(list);
    }

    /**
     * 导出教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:export')")
    @Log(title = "教师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeacherExportSearchRequest request) throws IOException {
        List<TeacherExportVo> list = iScTeacherService.findAllExcelData(request);
        ExcelUtil<TeacherExportVo> util = new ExcelUtil<TeacherExportVo>(TeacherExportVo.class);
        util.exportExcel(response, list, "教师信息");
    }



    /**
     * 导出教职工健康状态信息集合
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:exportHealth')")
    @PostMapping("/exportHealth")
    public void exportHealth(HttpServletResponse response, ScTeacher scTeacher) throws IOException {
        List<StaffHealthVo> list = iScTeacherService.findHealthExcel(scTeacher);
        ExcelUtil<StaffHealthVo> util = new ExcelUtil<StaffHealthVo>(StaffHealthVo.class);
        util.exportExcel(response, list, "教职工健康码信息");
    }

    /**
     * 获取教师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:query')")
    @GetMapping(value = "/{teacId}")
    public AjaxResult getInfo(@PathVariable("teacId") String teacId) {
        return AjaxResult.success(iScTeacherService.queryByTeacId(teacId));
    }

    /**
     * 新增教师信息
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:add')")
    @Log(title = "教师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TeacherSaveRequest request) {
        iScTeacherService.saveTeacher(request);
        return AjaxResult.success(1);
    }

    /**
     * 修改教师信息
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:edit')")
    @Log(title = "教师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TeacherSaveRequest request) {
        if (StringUtils.isEmpty(request.getTeacId())) {
            return AjaxResult.error("教师id不能为空");
        }
        iScTeacherService.saveTeacher(request);
        return AjaxResult.success(1);
    }

    /**
     * 删除教师信息
     */
    @PreAuthorize("@ss.hasPermi('basic:teacher:remove')")
    @Log(title = "教师信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{teacIds}")
    public AjaxResult remove(@PathVariable String[] teacIds) {
        iScTeacherService.batchDeleteById(Arrays.asList(teacIds));
        return AjaxResult.success(1);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "教师信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:teacher:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<TeacherImportRequest> util = new ExcelUtil<TeacherImportRequest>(TeacherImportRequest.class);
        List<TeacherImportRequest> teacherList = util.importExcel(file.getInputStream());
        iScTeacherService.importData(teacherList);
        return AjaxResult.success(1);
    }

    /**
     * 根据班级、姓名或手机号查询教师或职工信息列表
     */
    @GetMapping("/communication")
    public R<List<Map>> maillist(@Validated TeacherCommunicationRequest request) {
        if (StringUtils.isEmpty(request.getType())) {
            return R.fail("类型不能为空");
        }
        List<Map> list = iScTeacherService.selectList(request.getType(), request.getSubType(), request.getParaValue());
        return R.ok(list);
    }

    /**
     * 获取学校总体教职工通讯录
     */
    @GetMapping("/mailAllList")
    public R<List<Map>> mailAllList(@Validated TeacherCommunicationRequest request) {
        List<Map> list = iScTeacherService.mailAllList(request.getParaValue());
        return R.ok(list);
    }

    /**
     * 根据教师姓名查询
     * @param orgId
     * @param teacName
     * @param jobNumber
     * @return
     */
    @GetMapping("/selectByTeacName")
    public ScTeacher selectByTeacName(String orgId, String teacName, String jobNumber){
        return iScTeacherService.selectByTeacName(orgId,teacName,jobNumber);
    }

    /**
     * 同步教师健康码状态
     * @param teacId
     * @param healthState
     * @return
     */
    @GetMapping("/syncTeacherHealthState")
    public Integer syncTeacherHealthState(String teacId,int healthState){
        return iScTeacherService.syncTeacherHealthState(teacId,healthState);
    }

    /**
     * 根据条件获取所有在职教师信息列表
     */
    @GetMapping("/findAllData")
    public List<TeacherExportVo> findAllData(TeacherExportSearchRequest request) throws IOException {
        List<TeacherExportVo> list = iScTeacherService.findAllData(request);
        return list;
    }
}