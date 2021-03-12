package com.qh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.api.domain.SysDictData;
import com.qh.system.mapper.SysDictDataMapper;
import com.qh.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 * 
 * @author 
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public IPage<SysDictData> selectDictDataListByPage(IPage<SysDictData> page, SysDictData dictData)
    {
        return this.page(page, getQuery(dictData));
    }

    private QueryWrapper<SysDictData> getQuery(SysDictData dictData){
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(dictData.getDictCode()), "dict_code", dictData.getDictCode());
        queryWrapper.like(StringUtils.isNotBlank(dictData.getItemName()), "item_name", dictData.getItemName());
        queryWrapper.eq(StringUtils.isNotBlank(dictData.getStateMark()), "statue_mark", dictData.getStateMark());
        return queryWrapper;
    }

    /**
     * 根据条件查询所有字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataListAll(SysDictData dictData){
        return this.list(getQuery(dictData));
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param itemNumId 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataByItemNumId(Long itemNumId)
    {
        return dictDataMapper.selectDictDataByItemNumId(itemNumId);
    }
}
