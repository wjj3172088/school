package com.qh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.system.api.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictCode 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByCode(String dictCode);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictData selectDictDataByItemNumId(Long dictCode);

    /**
     * 根据字典id和值获取
     *
     * @param dictCode 字典id
     * @param itemVal 值
     * @return
     */
    SysDictData selectDictDataByCodeAndValue(@Param("dictCode") String dictCode, @Param("itemVal") String itemVal);

    /**
     * 根据字典id和值获取
     *
     * @param dictCode 字典id
     * @param itemName 名称
     * @return
     */
    SysDictData selectDictDataByCodeAndName(@Param("dictCode") String dictCode, @Param("itemName") String itemName);

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    public int countDictDataByType(String dictType);
}
