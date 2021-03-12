package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.basic.domain.vo.SecurityStaffExportVo;
import com.qh.basic.enums.BasicCodeEnum;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.service.IRemoteDictDataCalcService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.ParamCheckUtil;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.SecurityStaffMapper;
import com.qh.basic.domain.SecurityStaff;
import com.qh.basic.service.ISecurityStaffService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 三防保安信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2021-01-21
 */
@Service
public class SecurityStaffServiceImpl extends ServiceImpl<SecurityStaffMapper, SecurityStaff> implements ISecurityStaffService {
    @Autowired
    private IRemoteDictDataCalcService remoteDictDataCalcService;

    /**
     * 查询三防保安信息集合
     *
     * @param page          分页信息
     * @param securityStaff 操作三防保安信息对象
     * @return 操作三防保安信息集合
     */
    @Override
    public IPage<SecurityStaff> selectSecurityStaffListByPage(IPage<SecurityStaff> page, SecurityStaff securityStaff) {
        return this.page(page, getQuery(securityStaff));
    }

    /**
     * 查询需要导出的数据
     *
     * @param securityStaff 查询条件
     * @return
     */
    @Override
    public List<SecurityStaffExportVo> selectExportList(SecurityStaff securityStaff) {
        List<SecurityStaffExportVo> securityStaffExportVoList = new ArrayList<>();
        QueryWrapper<SecurityStaff> queryWrapper = this.getQuery(securityStaff);
        List<SecurityStaff> list = super.baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return securityStaffExportVoList;
        }

        //获取政治面貌信息
        List<SysDictData> politicalFaceList = remoteDictDataCalcService.selectDictData(DictTypeEnum.POLITICAL_FACE.getValue(), DictTypeEnum.POLITICAL_FACE.getName());
        //获取聘用性质信息
        List<SysDictData> recruitTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.RECRUIT_TYPE.getValue(), DictTypeEnum.RECRUIT_TYPE.getName());
        //获取学历信息
        List<SysDictData> educationTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.EDUCATION_TYPE.getValue(), DictTypeEnum.EDUCATION_TYPE.getName());
        list.forEach(x -> {
            //字典值内存外显
            String politicalFaceName = remoteDictDataCalcService.selectNameByCode(x.getPoliticalFace(), politicalFaceList, DictTypeEnum.POLITICAL_FACE.getName());
            String recruitTypeName = remoteDictDataCalcService.selectNameByCode(x.getRecruitType(), recruitTypeList, DictTypeEnum.RECRUIT_TYPE.getName());
            String educationTypeName = remoteDictDataCalcService.selectNameByCode(x.getEducationType(), educationTypeList, DictTypeEnum.EDUCATION_TYPE.getName());
            String sexName = x.getSex() ? "男" : "女";

            SecurityStaffExportVo securityStaffExportVo = new SecurityStaffExportVo();
            BeanUtils.copyProperties(x, securityStaffExportVo);
            securityStaffExportVo.setSexName(sexName);
            securityStaffExportVo.setPoliticalFaceName(politicalFaceName);
            securityStaffExportVo.setRecruitTypeName(recruitTypeName);
            securityStaffExportVo.setEducationTypeName(educationTypeName);
            securityStaffExportVoList.add(securityStaffExportVo);
        });
        return securityStaffExportVoList;
    }

    /**
     * 新增
     *
     * @param securityStaff 新增三防保安信息
     */
    @Override
    public void add(SecurityStaff securityStaff) {
        //新增前的验证
        this.validSave(securityStaff);
        Boolean exsit = this.countByIdCard(SecurityUtils.getOrgId(), securityStaff.getIdCard());
        if (exsit) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该身份证");
        }
        //新增
        securityStaff.setStaffId(UUIDG.generate());
        securityStaff.setOrgId(SecurityUtils.getOrgId());
        securityStaff.setOrgName(SecurityUtils.getOrgName());
        securityStaff.setCreateDate(DateUtil.getSystemSeconds());
        securityStaff.setUpdateDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(securityStaff);
    }

    /**
     * 修改
     *
     * @param securityStaff 修改三防保安信息
     */
    @Override
    public void modify(SecurityStaff securityStaff) {
        //修改前的验证
        if (StringUtils.isEmpty(securityStaff.getStaffId())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安id");
        }
        this.validSave(securityStaff);
        Boolean exsit = this.countByIdCardAndNotStaffId(SecurityUtils.getOrgId(), securityStaff.getIdCard(), securityStaff.getStaffId());
        if (exsit) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该身份证");
        }
        //修改
        securityStaff.setUpdateDate(DateUtil.getSystemSeconds());
        super.baseMapper.updateById(securityStaff);
    }

    /**
     * 导入
     *
     * @param securityStaffExportVoList 三防保安导入请求
     */
    @Override
    public void importData(List<SecurityStaffExportVo> securityStaffExportVoList) {
        List<SecurityStaff> addList = new ArrayList<>();
        List<SecurityStaff> modifyList = new ArrayList<>();
        //获取政治面貌信息
        List<SysDictData> politicalFaceList = remoteDictDataCalcService.selectDictData(DictTypeEnum.POLITICAL_FACE.getValue(), DictTypeEnum.POLITICAL_FACE.getName());
        //获取聘用性质信息
        List<SysDictData> recruitTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.RECRUIT_TYPE.getValue(), DictTypeEnum.RECRUIT_TYPE.getName());
        //获取学历信息
        List<SysDictData> educationTypeList = remoteDictDataCalcService.selectDictData(DictTypeEnum.EDUCATION_TYPE.getValue(), DictTypeEnum.EDUCATION_TYPE.getName());
        for (SecurityStaffExportVo securityStaffExportVo : securityStaffExportVoList) {
            this.validImportSave(securityStaffExportVo);
            int politicalFace = remoteDictDataCalcService.selectIntCodeByName(securityStaffExportVo.getPoliticalFaceName(), politicalFaceList, DictTypeEnum.POLITICAL_FACE.getName());
            int recruitType = remoteDictDataCalcService.selectIntCodeByName(securityStaffExportVo.getRecruitTypeName(), recruitTypeList, DictTypeEnum.RECRUIT_TYPE.getName());
            int educationType = remoteDictDataCalcService.selectIntCodeByName(securityStaffExportVo.getEducationTypeName(), educationTypeList, DictTypeEnum.EDUCATION_TYPE.getName());
            this.doImportData(securityStaffExportVo, politicalFace, recruitType, educationType, addList, modifyList);
        }

        //批量新增
        if (!CollectionUtils.isEmpty(addList)) {
            super.baseMapper.batchInsert(addList);
        }
        //批量修改
        if (!CollectionUtils.isEmpty(modifyList)) {
            super.baseMapper.batchUpdate(modifyList);
        }
    }

    /**
     * 执行导入保存操作
     *
     * @param securityStaffExportVo 三防保安导入请求
     */
    private void doImportData(SecurityStaffExportVo securityStaffExportVo, int politicalFace, int recruitType,
                              int educationType, List<SecurityStaff> addList, List<SecurityStaff> modifyList) {
        String idCard = securityStaffExportVo.getIdCard();
        boolean sex = "男".equals(securityStaffExportVo.getSexName());

        SecurityStaff securityStaff = this.selectByIdCard(SecurityUtils.getOrgId(), idCard);
        if (securityStaff == null) {
            securityStaff = new SecurityStaff();
            BeanUtils.copyProperties(securityStaffExportVo, securityStaff);
            securityStaff.setStaffId(UUIDG.generate());
            securityStaff.setOrgId(SecurityUtils.getOrgId());
            securityStaff.setOrgName(SecurityUtils.getOrgName());
            securityStaff.setPoliticalFace(politicalFace);
            securityStaff.setRecruitType(recruitType);
            securityStaff.setEducationType(educationType);
            securityStaff.setSex(sex);
            securityStaff.setCreateDate(DateUtil.getSystemSeconds());
            securityStaff.setUpdateDate(DateUtil.getSystemSeconds());
            addList.add(securityStaff);
        } else {
            BeanUtils.copyProperties(securityStaffExportVo, securityStaff);
            securityStaff.setPoliticalFace(politicalFace);
            securityStaff.setRecruitType(recruitType);
            securityStaff.setEducationType(educationType);
            securityStaff.setSex(sex);
            securityStaff.setUpdateDate(DateUtil.getSystemSeconds());
            modifyList.add(securityStaff);
        }
    }

    /**
     * 根据学校id和身份证查询
     *
     * @param orgId  学校id
     * @param idCard 身份证
     * @return
     */
    private SecurityStaff selectByIdCard(String orgId, String idCard) {
        QueryWrapper<SecurityStaff> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id_card", idCard);
        queryWrapper.eq("org_id", orgId);
        return super.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据学校id和身份证查询是否存在
     *
     * @param orgId  学校id
     * @param idCard 身份证
     * @return
     */
    private Boolean countByIdCard(String orgId, String idCard) {
        QueryWrapper<SecurityStaff> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id_card", idCard);
        queryWrapper.eq("org_id", orgId);
        return super.baseMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据学校id和身份证查询是否存在不是当前保安的信息
     *
     * @param orgId   学校id
     * @param idCard  身份证
     * @param staffId 保安id
     * @return
     */
    private Boolean countByIdCardAndNotStaffId(String orgId, String idCard, String staffId) {
        QueryWrapper<SecurityStaff> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id_card", idCard);
        queryWrapper.eq("org_id", orgId);
        queryWrapper.ne("staff_id", staffId);
        return super.baseMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 保存前的验证
     *
     * @param securityStaff
     */
    private void validSave(SecurityStaff securityStaff) {
        //保安姓名
        if (StringUtils.isEmpty(securityStaff.getName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安姓名");
        }
        //性别
        if (securityStaff.getSex() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "性别");
        }
        //联系电话
        if (StringUtils.isEmpty(securityStaff.getPhone())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "联系电话");
        }
        boolean valIdPhone = ParamCheckUtil.verifyPhone(securityStaff.getPhone());
        if (!valIdPhone) {
            throw new BizException(BasicCodeEnum.VALID_PHONE);
        }
        //身份证
        if (StringUtils.isEmpty(securityStaff.getIdCard())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "身份证");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(securityStaff.getIdCard());
        if (!valIdCard) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "身份证(" + securityStaff.getIdCard() + ")验证不通过");
        }
        //学历
        if (securityStaff.getEducationType() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学历");
        }
        //籍贯
        if (StringUtils.isEmpty(securityStaff.getNativePlace())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "籍贯");
        }
        //保安证编号
        if (StringUtils.isEmpty(securityStaff.getStaffNumber())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安证编号");
        }
        //保安公司
        if (StringUtils.isEmpty(securityStaff.getCompany())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安公司");
        }
        //政治面貌
        if (securityStaff.getPoliticalFace() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "政治面貌");
        }
        //聘用性质
        if (securityStaff.getRecruitType() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "聘用性质");
        }
        //从事保安工作时间
        if (securityStaff.getStaffYear() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "从事保安工作时间");
        }
        //聘用日期
        if (securityStaff.getWorkTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "聘用日期");
        }
        //合同有效期
        if (securityStaff.getContractExpire() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "合同有效期");
        }
    }

    /**
     * 导入保存前的验证
     *
     * @param securityStaffExportVo
     */
    private void validImportSave(SecurityStaffExportVo securityStaffExportVo) {
        //保安姓名
        if (StringUtils.isEmpty(securityStaffExportVo.getName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安姓名");
        }
        //性别
        if (StringUtils.isEmpty(securityStaffExportVo.getSexName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "性别");
        }
        //联系电话
        if (StringUtils.isEmpty(securityStaffExportVo.getPhone())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "联系电话");
        }
        boolean valIdPhone = ParamCheckUtil.verifyPhone(securityStaffExportVo.getPhone());
        if (!valIdPhone) {
            throw new BizException(BasicCodeEnum.VALID_PHONE);
        }
        //身份证
        if (StringUtils.isEmpty(securityStaffExportVo.getIdCard())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "身份证");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(securityStaffExportVo.getIdCard());
        if (!valIdCard) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "身份证(" + securityStaffExportVo.getIdCard() + ")验证不通过");
        }
        //学历
        if (StringUtils.isEmpty(securityStaffExportVo.getEducationTypeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学历");
        }
        //籍贯
        if (StringUtils.isEmpty(securityStaffExportVo.getNativePlace())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "籍贯");
        }
        //保安证编号
        if (StringUtils.isEmpty(securityStaffExportVo.getStaffNumber())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安证编号");
        }
        //保安公司
        if (StringUtils.isEmpty(securityStaffExportVo.getCompany())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "保安公司");
        }
        //政治面貌
        if (StringUtils.isEmpty(securityStaffExportVo.getPoliticalFaceName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "政治面貌");
        }
        //聘用性质
        if (StringUtils.isEmpty(securityStaffExportVo.getRecruitTypeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "聘用性质");
        }
        //从事保安工作时间
        if (securityStaffExportVo.getStaffYear() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "从事保安工作时间");
        }
        //聘用日期
        if (securityStaffExportVo.getWorkTime() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "聘用日期");
        }
        //合同有效期
        if (securityStaffExportVo.getContractExpire() == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "合同有效期");
        }
    }

    /**
     * 查询三防保安信息参数拼接
     */
    private QueryWrapper<SecurityStaff> getQuery(SecurityStaff securityStaff) {
        QueryWrapper<SecurityStaff> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(securityStaff.getName()), "name", securityStaff.getName());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(securityStaff.getPhone()), "phone", securityStaff.getPhone());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}