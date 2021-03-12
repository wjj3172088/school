package com.qh.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.system.api.domain.SysDictData;
import com.qh.system.api.domain.SysDictType;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author
 */
public interface ISysDictTypeService extends IService<SysDictType> {
    /**
     * 根据条件分页查询字典类型
     * @param page 分页对象
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    IPage<SysDictType> selectDictTypeList(IPage<SysDictType> page, SysDictType dictType);

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeListAll(SysDictType dictType);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByCode(String dictType);

    /**
     * 根据字典代码和值获取信息
     *
     * @param dictCode 字典代码
     * @param itemVal  值
     * @return
     */
    SysDictData selectByCodeAndValue(String dictCode, String itemVal);

    /**
     * 根据字典代码和名称获取信息
     *
     * @param dictCode 字典代码
     * @param itemName  值
     * @return
     */
    SysDictData selectByCodeAndName(String dictCode, String itemName);

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    SysDictType selectDictTypeById(String dictId);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    SysDictType selectDictTypeByType(String dictType);

    /**
     * 清空缓存数据
     */
    void clearCache();

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    String checkDictTypeUnique(SysDictType dictType);

    /**
     * 通讯录教职工类型
     * @param type
     * @return
     */
    List<SysDictData> selectCommunicationList(int type);
}
