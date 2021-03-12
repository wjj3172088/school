package com.qh.basic.service.impl;

import com.qh.basic.service.IRemoteDictDataCalcService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.web.domain.R;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/21 14:45
 * @Description: 字典处理类
 */
@Service
public class RemoteDictDataCalcServiceImpl implements IRemoteDictDataCalcService {
    @Autowired
    private RemoteDictDataService remoteDictDataService;

    /**
     * 根据code值查询字典信息
     *
     * @param code   字典key
     * @param tipMsg 提示信息
     * @return
     */
    @Override
    public List<SysDictData> selectDictData(String code, String tipMsg) {
        //获取字典信息
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode(code);
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, tipMsg + "未配置");
        }
        return dictDataResult.getData();
    }

    /**
     * 根据字典名字查询值
     *
     * @param name            字典名字
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    @Override
    public int selectIntCodeByName(String name, List<SysDictData> sysDictDataList, String tipMsg) {
        SysDictData dictData = sysDictDataList.stream().filter(x -> x.getItemName().equals(name)).findAny().orElse(null);
        if (dictData == null) {
            throw new BizException(CodeEnum.NOT_EXIST, tipMsg);
        }
        return Integer.valueOf(dictData.getItemVal());
    }

    /**
     * 根据字典值查询名字
     *
     * @param code            字典值
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    @Override
    public String selectNameByCode(Integer code, List<SysDictData> sysDictDataList, String tipMsg) {
        SysDictData jobTitleDictData =
                sysDictDataList.stream().filter(x -> Integer.valueOf(x.getItemVal()).intValue() == code).findAny().orElse(null);
        if (jobTitleDictData == null) {
            throw new BizException(CodeEnum.NOT_EXIST, tipMsg);
        }
        return jobTitleDictData.getItemName();
    }

    /**
     * 根据字典code和字典值查询
     *
     * @param dictCode  字典code
     * @param itemValue 字典值
     * @param tipMsg    提示语
     * @return
     */
    @Override
    public SysDictData selectByDictCodeAndValue(String dictCode, String itemValue, String tipMsg) {
        R<SysDictData> dictDataResult = remoteDictDataService.getDictData(dictCode, itemValue);
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(CodeEnum.NOT_EXIST, tipMsg);
        }
        return dictDataResult.getData();
    }
}
