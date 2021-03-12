package com.qh.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.system.api.domain.SysDictData;
import com.qh.system.service.ISysDictDataService;
import com.qh.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author
 */
@RestController
@RequestMapping("/dict/data")
@CheckBackendToken
public class SysDictDataController extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public R<IPage<SysDictData>> list(SysDictData dictData) {
        IPage<SysDictData> page = dictDataService.selectDictDataListByPage(getPage(), dictData);
        return R.ok(page);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData) throws IOException {
        List<SysDictData> list = dictDataService.selectDictDataListAll(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{itemNumId}")
    public R<SysDictData> getInfo(@PathVariable Long itemNumId) {
        return R.ok(dictDataService.selectDictDataByItemNumId(itemNumId));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/dictCode/{dictCode}")
    public R<List<SysDictData>> getDictDataByCode(@PathVariable String dictCode) {
        return R.ok(dictTypeService.selectDictDataByCode(dictCode));
    }

    /**
     * 根据字典代码和值获取信息
     */
    @GetMapping(value = "/dictCode/{dictCode}/itemVal/{itemVal}")
    @CheckBackendToken(required = false)
    public R<SysDictData> getDictData(@PathVariable("dictCode") String dictCode, @PathVariable("itemVal") String itemVal) {
        return R.ok(dictTypeService.selectByCodeAndValue(dictCode, itemVal));
    }

    /**
     * 根据字典代码和值获取信息
     */
    @GetMapping(value = "/dictCode/{dictCode}/itemName/{itemName}")
    @CheckBackendToken(required = false)
    public R<SysDictData> getDictDataByItemName(@PathVariable("dictCode") String dictCode, @PathVariable("itemName") String itemName) {
        return R.ok(dictTypeService.selectByCodeAndName(dictCode, itemName));
    }

    /**
     * 通讯录教职工类型
     */
    @GetMapping(value = "/communication/{type}")
    public R<List<SysDictData>> selectCommunicationList(@PathVariable("type") int type) {
        return R.ok(dictTypeService.selectCommunicationList(type));
    }
}
