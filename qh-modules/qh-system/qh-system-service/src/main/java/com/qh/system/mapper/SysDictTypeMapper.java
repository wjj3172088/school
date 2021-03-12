package com.qh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.system.api.domain.SysDictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典表 数据层
 * 
 * @author 
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType>
{

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeAll();

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
     * 校验字典类型称是否唯一
     * 
     * @param dictType 字典类型
     * @return 结果
     */
    SysDictType checkDictTypeUnique(String dictType);
}
