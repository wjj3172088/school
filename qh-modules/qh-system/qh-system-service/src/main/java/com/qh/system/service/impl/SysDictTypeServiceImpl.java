package com.qh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.constant.UserConstants;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.api.domain.SysDictData;
import com.qh.system.api.domain.SysDictType;
import com.qh.system.mapper.SysDictDataMapper;
import com.qh.system.mapper.SysDictTypeMapper;
import com.qh.system.service.ISysDictTypeService;
import com.qh.system.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
        for (SysDictType dictType : dictTypeList) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByCode(dictType.getDictCode());
            DictUtils.setDictCache(dictType.getDictCode(), dictDatas);
        }
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public IPage<SysDictType> selectDictTypeList(IPage<SysDictType> page, SysDictType dictType) {
        return this.page(page, getQuery(dictType));
    }

    private QueryWrapper<SysDictType> getQuery(SysDictType dictType) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(dictType.getDictName()), "dict_name", dictType.getDictName());
        queryWrapper.eq(StringUtils.isNotBlank(dictType.getStateMark()), "state_mark", dictType.getStateMark());
        queryWrapper.like(StringUtils.isNotBlank(dictType.getDictCode()), "dict_code", dictType.getDictCode());
        queryWrapper.ge(StringUtils.isNotBlank(dictType.getBeginTime()), "date_format(create_time,'%y%m%d')", DateUtils.formatYmd(dictType.getBeginTime()));
        queryWrapper.le(StringUtils.isNotBlank(dictType.getEndTime()), "date_format(create_time,'%y%m%d')", DateUtils.formatYmd(dictType.getEndTime()));
        return queryWrapper;
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeListAll(SysDictType dictType) {
        return this.list(getQuery(dictType));
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictCode 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByCode(String dictCode) {
        List<SysDictData> dictDatas = dictDataMapper.selectDictDataByCode(dictCode);
        if (StringUtils.isNotNull(dictDatas) && dictDatas.size() > 0) {
            DictUtils.setDictCache(dictCode, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典代码和值获取信息
     *
     * @param dictCode 字典代码
     * @param itemVal  值
     * @return
     */
    @Override
    public SysDictData selectByCodeAndValue(String dictCode, String itemVal) {
        return dictDataMapper.selectDictDataByCodeAndValue(dictCode, itemVal);
    }


    /**
     * 根据字典代码和名称获取信息
     *
     * @param dictCode 字典代码
     * @param itemName  名称
     * @return
     */
    @Override
    public SysDictData selectByCodeAndName(String dictCode, String itemName) {
        return dictDataMapper.selectDictDataByCodeAndName(dictCode, itemName);
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(String dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        DictUtils.clearDictCache();
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        String dictId = dict.getDictId();
        SysDictType dictCode = dictTypeMapper.checkDictTypeUnique(dict.getDictCode());
        if (StringUtils.isNotNull(dictCode) && !dictCode.getDictId().equals(dictId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通讯录教职工类型
     *
     * @return
     */
    @Override
    public List<SysDictData> selectCommunicationList(int type) {
        if (type == 0) {
            //初始化教师
            SysDictData dictData = new SysDictData();
            dictData.setDictCode("T");
            dictData.setItemName("全部教师");
            dictData.setItemVal("0");
            List<SysDictData> list = new ArrayList<>();
            list.add(dictData);

            //职工类型
            List<SysDictData> staffList = this.selectDictDataByCode("staffJobTitle");
            if (staffList != null && staffList.size() > 0) {
                staffList.forEach(x -> x.setDictCode("S"));
                list.addAll(staffList);
            }
            return list;
        } else {
            //初始做全校节点
            SysDictData dictData = new SysDictData();
            dictData.setDictCode("A");
            dictData.setItemName("全校");
            dictData.setItemVal("0");
            List<SysDictData> list = new ArrayList<>();
            list.add(dictData);
            return list;
        }
    }
}
