package com.qh.system.api;

import com.qh.common.core.constant.ServiceNameConstants;
import com.qh.common.core.web.domain.R;
import com.qh.system.api.domain.SysDictData;
import com.qh.system.api.factory.RemoteDictDataFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 21:44
 * @Description:
 */
@FeignClient(contextId = "remoteDictDataService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDictDataFallbackFactory.class)
public interface RemoteDictDataService {
    /**
     * 根据字典代码和值获取信息
     *
     * @param dictCode 字典代码
     * @param itemVal  值
     * @return
     */
    @GetMapping(value = "/dict/data/dictCode/{dictCode}/itemVal/{itemVal}")
    R<SysDictData> getDictData(@PathVariable("dictCode") String dictCode, @PathVariable("itemVal") String itemVal);

    /**
     * 根据字典代码和名称获取信息
     *
     * @param dictCode 字典代码
     * @param itemName  值
     * @return
     */
    @GetMapping(value = "/dict/data/dictCode/{dictCode}/itemName/{itemName}")
    R<SysDictData> getDictDataByItemName(@PathVariable("dictCode") String dictCode, @PathVariable("itemName") String itemName);

    /**
     * 根据字典代码获取信息
     *
     * @param dictCode 字典代码
     * @return
     */
    @GetMapping(value = "/dict/data/dictCode/{dictCode}")
    R<List<SysDictData>> getDictDataByCode(@PathVariable("dictCode") String dictCode);
}
