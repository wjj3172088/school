package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.TechnicalDefense;
import com.qh.basic.domain.vo.TechnicalDefenseExportVo;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.mapper.TechnicalDefenseMapper;
import com.qh.basic.model.request.technicaldefense.ImportTechnicalDefenseRequest;
import com.qh.basic.service.IRemoteDictDataCalcService;
import com.qh.basic.service.ITechnicalDefenseService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 技防信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-25
 */
@Service
public class TechnicalDefenseServiceImpl extends ServiceImpl<TechnicalDefenseMapper, TechnicalDefense> implements ITechnicalDefenseService {
    @Autowired
    private IRemoteDictDataCalcService remoteDictDataCalcService;

    /**
     * 查询技防信息集合
     *
     * @param page             分页信息
     * @param technicalDefense 操作技防信息对象
     * @return 操作技防信息集合
     */
    @Override
    public IPage<TechnicalDefense> selectTechnicalDefenseListByPage(IPage<TechnicalDefense> page, TechnicalDefense technicalDefense) {
        return this.page(page, getQuery(technicalDefense));
    }

    /**
     * 查询需要导出的数据
     *
     * @param technicalDefense 查询条件
     * @return
     */
    @Override
    public List<TechnicalDefenseExportVo> selectExportList(TechnicalDefense technicalDefense) {
        List<TechnicalDefenseExportVo> physicalDefenseExportVoList = new ArrayList<>();
        List<TechnicalDefense> list = super.baseMapper.selectList(this.getQuery(technicalDefense));
        if (CollectionUtils.isEmpty(list)) {
            return physicalDefenseExportVoList;
        }
        list.forEach(x -> {
            String abnormalName = x.getBeAbnormal() ? "是" : "否";
            String connectPublicSecurity = x.getBeConnectPublicSecurity() ? "是" : "否";

            TechnicalDefenseExportVo technicalDefenseExportVo = new TechnicalDefenseExportVo();
            BeanUtils.copyProperties(x, technicalDefenseExportVo);
            technicalDefenseExportVo.setAbnormalName(abnormalName);
            technicalDefenseExportVo.setConnectPublicSecurityName(connectPublicSecurity);
            physicalDefenseExportVoList.add(technicalDefenseExportVo);
        });
        return physicalDefenseExportVoList;
    }

    /**
     * 导入
     *
     * @param importTechnicalDefenseRequestList 导入请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<ImportTechnicalDefenseRequest> importTechnicalDefenseRequestList) {
        List<TechnicalDefense> addList = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        //获取物防器械类型信息
        List<SysDictData> defenseTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.TECHNICAL_DEFENSE_TYPE.getValue(), DictTypeEnum.TECHNICAL_DEFENSE_TYPE.getName());
        for (ImportTechnicalDefenseRequest request : importTechnicalDefenseRequestList) {
            this.validImportSave(request);
            int defenseType = remoteDictDataCalcService.selectIntCodeByName(request.getDefenseTypeName(), defenseTypeList, DictTypeEnum.TECHNICAL_DEFENSE_TYPE.getName());
//            if (list.contains(defenseType)) {
//                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格中出现重复的器械类型");
//            }
            list.add(defenseType);
            Boolean abnormal = "是".equals(request.getAbnormalName());
            Boolean connectPublicSecurity = "是".equals(request.getConnectPublicSecurityName());
            this.doImportData(request, defenseType, abnormal, connectPublicSecurity, addList);
        }

        //批量新增
        if (!CollectionUtils.isEmpty(addList)) {
            super.baseMapper.batchInsert(addList);
        }
    }

    /**
     * 执行导入保存操作
     *
     * @param request               导入请求
     * @param defenseType           器械类型
     * @param abnormal              是否异常
     * @param connectPublicSecurity 是否110联网
     * @param addList               新增列表
     */
    private void doImportData(ImportTechnicalDefenseRequest request, int defenseType, Boolean abnormal, Boolean connectPublicSecurity, List<TechnicalDefense> addList) {
        //转化时间
        Long configTime = request.getConfigTime().getTime() / 1000;
        Long expireTime = request.getExpireTime().getTime() / 1000;
        TechnicalDefense technicalDefense = new TechnicalDefense();
        BeanUtils.copyProperties(request, technicalDefense);
        BigDecimal price = new BigDecimal(request.getPrice());
        technicalDefense.setPrice(price);
        technicalDefense.setDefenseId(UUIDG.generate());
        technicalDefense.setOrgId(SecurityUtils.getOrgId());
        technicalDefense.setOrgName(SecurityUtils.getOrgName());
        technicalDefense.setBeAbnormal(abnormal);
        technicalDefense.setBeConnectPublicSecurity(connectPublicSecurity);
        technicalDefense.setDefenseType(defenseType);
        technicalDefense.setConfigTime(configTime);
        technicalDefense.setExpireTime(expireTime);
        technicalDefense.setCreateDate(DateUtil.getSystemSeconds());
        technicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        addList.add(technicalDefense);
    }

    /**
     * 导入保存前的验证
     *
     * @param request
     */
    private void validImportSave(ImportTechnicalDefenseRequest request) {
        //器械类型
        if (StringUtils.isEmpty(request.getDefenseTypeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械类型");
        }
        //器械名称
        if (StringUtils.isEmpty(request.getName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械名称");
        }
        //器械型号
        if (StringUtils.isEmpty(request.getModel())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械型号");
        }
        //器械数量
        if (request.getCount() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "器械数量");
        }
        if (request.getCount() < 0) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "器械数量不能小于0");
        }
        if (request.getCount() > 999999999) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "器械数量不能超过9位数");
        }
        //配置时间
        if (request.getConfigTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "配置时间");
        }
        //质保时间
        if (request.getExpireTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "质保时间");
        }
        //是否异常
        if (StringUtils.isEmpty(request.getAbnormalName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "是否异常");
        }
        //是否110联网
        if (StringUtils.isEmpty(request.getConnectPublicSecurityName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "是否110联网");
        }
        if (StringUtils.isNotEmpty(request.getPrice())) {
            try {
                BigDecimal price = new BigDecimal(request.getPrice());
                if (price.compareTo(BigDecimal.valueOf(999999999)) > 0) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "资金投入不能超过9位数");
                }
                if (price.scale() > 2) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "资金投入不能超过两位小数");
                }
            } catch (NumberFormatException e) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "资金投入只能填数字");
            }
        }
    }

    /**
     * 新增
     *
     * @param technicalDefense 技防信息
     */
    @Override
    public void add(TechnicalDefense technicalDefense) {
        //判断器械类型是否合法
        SysDictData sysDictData = remoteDictDataCalcService.selectByDictCodeAndValue(DictTypeEnum.TECHNICAL_DEFENSE_TYPE.getValue(), technicalDefense.getDefenseType().toString(), "该器械类型");

        technicalDefense.setDefenseId(UUIDG.generate());
        technicalDefense.setDefenseTypeName(sysDictData.getItemName());
        technicalDefense.setOrgId(SecurityUtils.getOrgId());
        technicalDefense.setOrgName(SecurityUtils.getOrgName());
        technicalDefense.setCreateDate(DateUtil.getSystemSeconds());
        technicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(technicalDefense);
    }

    /**
     * 修改
     *
     * @param technicalDefense 物防信息
     */
    @Override
    public void modify(TechnicalDefense technicalDefense) {
        //判断器械类型是否合法
        SysDictData sysDictData = remoteDictDataCalcService.selectByDictCodeAndValue(DictTypeEnum.TECHNICAL_DEFENSE_TYPE.getValue(), technicalDefense.getDefenseType().toString(), "该器械类型");
        technicalDefense.setDefenseTypeName(sysDictData.getItemName());
        technicalDefense.setUpdateDate(DateUtil.getSystemSeconds());
        int count = super.baseMapper.updateByDefenseId(technicalDefense);
        if (count == 0) {
            throw new BizException(CodeEnum.NOT_EXIST, "该物防信息");
        }
    }

    /**
     * 查询技防信息参数拼接
     */
    private QueryWrapper<TechnicalDefense> getQuery(TechnicalDefense technicalDefense) {
        QueryWrapper<TechnicalDefense> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(technicalDefense.getDefenseType() != null, "defense_type", technicalDefense.getDefenseType());
        queryWrapper.like(StringUtils.isNotBlank(technicalDefense.getName()), "name", technicalDefense.getName());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}