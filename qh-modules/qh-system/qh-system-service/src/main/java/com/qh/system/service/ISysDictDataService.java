package com.qh.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.system.api.domain.SysDictData;

import java.util.List;

/**
 * 字典 业务层
 * 
 * @author 
 */
public interface ISysDictDataService extends IService<SysDictData>
{
    /**
     * 根据条件分页查询字典数据
     * @param page 分页对象
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    IPage<SysDictData> selectDictDataListByPage(IPage<SysDictData> page, SysDictData dictData);

    /**
     * 根据条件查询所有字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataListAll(SysDictData dictData);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictData selectDictDataByItemNumId(Long dictCode);
}
