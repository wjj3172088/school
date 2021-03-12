package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.PhysicalDefense;
import com.qh.basic.domain.vo.PhysicalDefenseExportVo;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.mapper.PhysicalDefenseMapper;
import com.qh.basic.model.request.physicaldefense.PhysicalDefenseImportRequest;
import com.qh.basic.service.IPhysicalDefenseService;
import com.qh.basic.service.IRemoteDictDataCalcService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 物防信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-22
 */
@Service
public class PhysicalDefenseServiceImpl extends ServiceImpl<PhysicalDefenseMapper, PhysicalDefense> implements IPhysicalDefenseService {
    @Autowired
    private IRemoteDictDataCalcService remoteDictDataCalcService;

    /**
     * 查询物防信息集合
     *
     * @param page            分页信息
     * @param physicalDefense 操作物防信息对象
     * @return 操作物防信息集合
     */
    @Override
    public IPage<PhysicalDefense> selectPhysicalDefenseListByPage(IPage<PhysicalDefense> page, PhysicalDefense physicalDefense) {
        return this.page(page, getQuery(physicalDefense));
    }

    /**
     * 查询需要导出的数据
     *
     * @param physicalDefense 查询条件
     * @return
     */
    @Override
    public List<PhysicalDefenseExportVo> selectExportList(PhysicalDefense physicalDefense) {
        List<PhysicalDefenseExportVo> physicalDefenseExportVoList = new ArrayList<>();
        List<PhysicalDefense> list = super.baseMapper.selectList(this.getQuery(physicalDefense));
        if (CollectionUtils.isEmpty(list)) {
            return physicalDefenseExportVoList;
        }
        list.forEach(x -> {
            String enableName = x.getEnable() ? "是" : "否";

            PhysicalDefenseExportVo physicalDefenseExportVo = new PhysicalDefenseExportVo();
            BeanUtils.copyProperties(x, physicalDefenseExportVo);
            physicalDefenseExportVo.setEnableName(enableName);
            physicalDefenseExportVoList.add(physicalDefenseExportVo);
        });
        return physicalDefenseExportVoList;
    }

    /**
     * 导入
     *
     * @param physicalDefenseImportRequestList 导入信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<PhysicalDefenseImportRequest> physicalDefenseImportRequestList) {
        List<PhysicalDefense> addList = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        //获取物防器械类型信息
        List<SysDictData> defenseTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.PHYSICAL_DEFENSE_TYPE.getValue(), DictTypeEnum.PHYSICAL_DEFENSE_TYPE.getName());
        for (PhysicalDefenseImportRequest request : physicalDefenseImportRequestList) {
            this.validImportSave(request);
            int defenseType = remoteDictDataCalcService.selectIntCodeByName(request.getDefenseTypeName(), defenseTypeList, DictTypeEnum.PHYSICAL_DEFENSE_TYPE.getName());
//            if (list.contains(defenseType)) {
//                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格中出现重复的器械类型");
//            }
            list.add(defenseType);
            Boolean enable = "是".equals(request.getEnableName());
            this.doImportData(request, defenseType, enable, addList);
        }

        //批量新增
        if (!CollectionUtils.isEmpty(addList)) {
            super.baseMapper.batchInsert(addList);
        }
    }

    /**
     * 执行导入保存操作
     *
     * @param request     导入请求
     * @param defenseType 器械类型
     * @param enable      是否可用
     * @param addList     新增列表
     */
    private void doImportData(PhysicalDefenseImportRequest request, int defenseType, Boolean enable, List<PhysicalDefense> addList) {
        //转化时间
        Long configTime = request.getConfigTime().getTime() / 1000;
        Long expireTime = request.getExpireTime().getTime() / 1000;

        PhysicalDefense physicalDefense = new PhysicalDefense();
        BeanUtils.copyProperties(request, physicalDefense);
        physicalDefense.setDefenseId(UUIDG.generate());
        physicalDefense.setOrgId(SecurityUtils.getOrgId());
        physicalDefense.setOrgName(SecurityUtils.getOrgName());
        physicalDefense.setEnable(enable);
        physicalDefense.setDefenseType(defenseType);
        physicalDefense.setConfigTime(configTime);
        physicalDefense.setExpireTime(expireTime);
        physicalDefense.setCreateDate(DateUtil.getSystemSeconds());
        physicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        addList.add(physicalDefense);
    }

    /**
     * 导入保存前的验证
     *
     * @param request
     */
    private void validImportSave(PhysicalDefenseImportRequest request) {
        //器械类型
        if (StringUtils.isEmpty(request.getDefenseTypeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械类型");
        }
        //器械名称
        if (StringUtils.isEmpty(request.getName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械名称");
        }
        //配置数量
        if (request.getCount() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "配置数量");
        }
        if (request.getCount() < 0) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "配置数量不能小于0");
        }
        if (request.getCount() > 999999999) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "配置数量不能超过9位数");
        }
        //配置时间
        if (request.getConfigTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "配置时间");
        }
        //质保时间
        if (request.getExpireTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "质保时间");
        }
        //校内位置
        if (StringUtils.isEmpty(request.getLocation())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "校内位置");
        }
        //是否可用
        if (StringUtils.isEmpty(request.getEnableName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "是否可用");
        }
    }

    /**
     * 新增
     *
     * @param physicalDefense 物防信息
     */
    @Override
    public void add(PhysicalDefense physicalDefense) {
        //判断器械类型是否合法
        SysDictData sysDictData = remoteDictDataCalcService.selectByDictCodeAndValue(DictTypeEnum.PHYSICAL_DEFENSE_TYPE.getValue(), physicalDefense.getDefenseType().toString(), "该器械类型");

        physicalDefense.setDefenseId(UUIDG.generate());
        physicalDefense.setDefenseTypeName(sysDictData.getItemName());
        physicalDefense.setOrgId(SecurityUtils.getOrgId());
        physicalDefense.setOrgName(SecurityUtils.getOrgName());
        physicalDefense.setCreateDate(DateUtil.getSystemSeconds());
        physicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(physicalDefense);
    }

    /**
     * 修改
     *
     * @param physicalDefense 物防信息
     */
    @Override
    public void modify(PhysicalDefense physicalDefense) {
        //判断器械类型是否合法
        SysDictData sysDictData = remoteDictDataCalcService.selectByDictCodeAndValue(DictTypeEnum.PHYSICAL_DEFENSE_TYPE.getValue(), physicalDefense.getDefenseType().toString(), "该器械类型");
        physicalDefense.setDefenseTypeName(sysDictData.getItemName());
        physicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        int count = super.baseMapper.updateById(physicalDefense);
        if (count == 0) {
            throw new BizException(CodeEnum.NOT_EXIST, "该物防信息");
        }
    }

    /**
     * 查询物防信息参数拼接
     */
    private QueryWrapper<PhysicalDefense> getQuery(PhysicalDefense physicalDefense) {
        QueryWrapper<PhysicalDefense> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(physicalDefense.getDefenseType() != null, "defense_type", physicalDefense.getDefenseType());
        queryWrapper.like(StringUtils.isNotBlank(physicalDefense.getName()), "name", physicalDefense.getName());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}