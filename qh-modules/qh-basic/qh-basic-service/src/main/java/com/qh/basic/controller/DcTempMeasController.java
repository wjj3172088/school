package com.qh.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.vo.TempMeasClassVo;
import com.qh.basic.domain.vo.TempMeasStuVo;
import com.qh.basic.model.request.statistics.TempMeasReq;
import com.qh.basic.service.IDcTempMeasService;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 体温测量记录Controller
 * 
 * @author huangdaoquan
 * @date 2020-12-31
 */
@RestController
@RequestMapping("/tempMeas" )
public class DcTempMeasController extends BaseController {

    @Autowired
    private IDcTempMeasService iDcTempMeasService;

    /**
     * 查询体温测量记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:tempMeas:list')")
    @GetMapping("/list")
    public R<IPage<TempMeasClassVo>> list(String classId, String recordTime)
    {
        IPage<TempMeasClassVo> list = iDcTempMeasService.queryMeasClassList(getPage(),classId, recordTime);
        return R.ok(list);
    }


    /**
     * 查询体温测量记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:tempMeas:stuList')")
    @GetMapping("/stuList")
    public R<IPage<TempMeasStuVo>> stuList(TempMeasReq tempMeasReq)
    {
        IPage<TempMeasStuVo> list = iDcTempMeasService.queryMeasStuList(getPage(),tempMeasReq);
        return R.ok(list);
    }

    /**
     * 导出体温测量记录列表
     */
    @PreAuthorize("@ss.hasPermi('basic:tempMeas:export')" )
    @Log(title = "体温测量记录导出" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    public void export(HttpServletResponse response,TempMeasReq tempMeasReq)  throws IOException {

        List<TempMeasStuVo> list = iDcTempMeasService.exportMeasStuList(tempMeasReq);
        ExcelUtil<TempMeasStuVo> util = new ExcelUtil<TempMeasStuVo>(TempMeasStuVo.class);
        util.exportExcel(response, list, "测温记录");
    }

    /**
     * 获取体温测量记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:tempMeas:query')" )
    @GetMapping(value = "/{measId}" )
    public AjaxResult getInfo(@PathVariable("measId" ) String measId) {
        return AjaxResult.success(iDcTempMeasService.getById(measId));
    }
}
