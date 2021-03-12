package com.qh.basic.service;

import com.qh.system.api.domain.SysDictData;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/21 14:45
 * @Description: 字典处理类
 */
public interface IRemoteDictDataCalcService {
    /**
     * 根据code值查询字典信息
     *
     * @param code   字典key
     * @param tipMsg 提示信息
     * @return
     */
    List<SysDictData> selectDictData(String code, String tipMsg);

    /**
     * 根据字典名字查询值
     *
     * @param name            字典名字
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    int selectIntCodeByName(String name, List<SysDictData> sysDictDataList, String tipMsg);

    /**
     * 根据字典值查询名字
     *
     * @param code            字典值
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    String selectNameByCode(Integer code, List<SysDictData> sysDictDataList, String tipMsg);

    /**
     * 根据字典code和字典值查询
     *
     * @param dictCode  字典code
     * @param itemValue 字典值
     * @param tipMsg    提示语
     * @return
     */
    SysDictData selectByDictCodeAndValue(String dictCode, String itemValue, String tipMsg);
}
