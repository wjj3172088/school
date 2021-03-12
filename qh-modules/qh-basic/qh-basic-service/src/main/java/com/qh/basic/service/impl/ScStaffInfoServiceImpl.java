package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScMoveAccOther;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.domain.vo.PushMoveStaff;
import com.qh.basic.enums.AccTypeEnum;
import com.qh.basic.enums.BasicCodeEnum;
import com.qh.basic.enums.Status;
import com.qh.basic.enums.SysEnableEnum;
import com.qh.basic.mapper.ScStaffInfoMapper;
import com.qh.basic.model.request.staffinfo.StaffInfoImportRequest;
import com.qh.basic.model.request.staffinfo.StaffInfoSaveRequest;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import com.qh.basic.service.IScMoveAccService;
import com.qh.basic.service.IScStaffInfoService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.ParamCheckUtil;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 职工信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Service
public class ScStaffInfoServiceImpl extends ServiceImpl<ScStaffInfoMapper, ScStaffInfo> implements IScStaffInfoService {
    @Autowired
    private IScMoveAccService moveAccService;
    @Autowired
    private ScStaffInfoMapper staffInfoMapper;
    @Autowired
    private RemoteDictDataService remoteDictDataService;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询职工信息集合
     *
     * @param request 操作职工信息对象
     * @return 操作职工信息集合
     */
    @Override
    public IPage<Map> selectScStaffInfoListByPage(IPage<ScStaffInfo> page, StaffInfoSearchRequest request) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("trueName", request.getTrueName());
        map.put("idCard", request.getIdCard());
        map.put("mobile", request.getMobile());
        return staffInfoMapper.selectListByPage(page, map);
    }

    /**
     * 保存
     *
     * @param request 职工信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStaffInfo(StaffInfoSaveRequest request) {
        String orgId = SecurityUtils.getOrgId();
        //身份证验证
        String idCard = request.getIdCard();
        boolean valIdCard = ParamCheckUtil.validIdCard(idCard);
        if (!valIdCard) {
            throw new BizException(BasicCodeEnum.VALID_CARD);
        }

        ScMoveAccOther moveAccOther = new ScMoveAccOther();
        BeanUtils.copyProperties(request, moveAccOther);

        ScStaffInfo idCardStaffInfo = staffInfoMapper.selectByIdCard(request.getIdCard());
        ScStaffInfo mobileStaff = staffInfoMapper.selectByMobile(request.getMobile());

        //保存职工信息
        ScStaffInfo staffInfo = new ScStaffInfo();
        BeanUtils.copyProperties(request, staffInfo);
        if (StringUtils.isEmpty(request.getAccId())) {
            if (idCardStaffInfo != null) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该身份证");
            }
            if (mobileStaff != null) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该手机号");
            }
            //保存移动端账号信息和账号扩展信息
            String accId = moveAccService.saveMoveAcc(
                    null,
                    request.getTrueName(),
                    request.getMobile(),
                    AccTypeEnum.STAFF.getCode(),
                    orgId,
                    moveAccOther,
                    SysEnableEnum.NO.getValue());

            //新增职工信息
            staffInfo.setAccId(accId);
            staffInfo.setOrgId(orgId);
            staffInfo.setStaffId(UUIDG.generate());
            staffInfo.setStateMark(Status.normal.value());
            staffInfo.setCreateDate(new Date());
            staffInfo.setModifyDate(new Date());
            //获取该学校的最新最大职工的工号
            Long maxJobNumber = staffInfoMapper.selectMaxJobNumberByOrgId(SecurityUtils.getOrgId());
            staffInfo.setJobNumber(maxJobNumber);
            staffInfoMapper.insert(staffInfo);
        } else {
            //保存职工信息
            ScStaffInfo dbStaff = staffInfoMapper.selectByAccId(request.getAccId());
            if (dbStaff == null) {
                throw new BizException(CodeEnum.NOT_EXIST, "该用户");
            }
            if (idCardStaffInfo != null && !idCardStaffInfo.getAccId().equals(dbStaff.getAccId())) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该身份证");
            }
            if (mobileStaff != null && !mobileStaff.getAccId().equals(dbStaff.getAccId())) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该手机号");
            }
            staffInfoMapper.modify(staffInfo);

            moveAccService.saveMoveAcc(
                    dbStaff.getAccId(),
                    request.getTrueName(),
                    request.getMobile(),
                    AccTypeEnum.STAFF.getCode(),
                    orgId,
                    moveAccOther,
                    SysEnableEnum.NO.getValue());
        }
    }

    /**
     * 批量删除
     *
     * @param accIdList 帐户Id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteById(List<String> accIdList) {
        if (accIdList == null || accIdList.size() == 0) {
            return;
        }
        //通过accId删除职工信息
        staffInfoMapper.batchDelByAccId(accIdList);
        //通过accId删除移动账号相关信息
        moveAccService.batchDeleteById(accIdList);
    }

    /**
     * 导入
     *
     * @param staffInfoList 职工信息集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<StaffInfoImportRequest> staffInfoList) {
        //获取班别信息
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode("staffJobTitle");
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADE_NO_CONFIG);
        }
        List<SysDictData> dictDataList = dictDataResult.getData();
        List<String> mobileList = new ArrayList<>();
        List<String> idCardList = new ArrayList<>();

        //获取该学校的最新最大的工号
        Long maxJobNumber = staffInfoMapper.selectMaxJobNumberByOrgId(SecurityUtils.getOrgId());
        for (StaffInfoImportRequest staffInfo : staffInfoList) {
            //导入前的判断
            this.validImportSave(staffInfo);
            //判断手机号和身份证号在导入的表格中是否有重复
            if (mobileList.contains(staffInfo.getMobile())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复手机号(" + staffInfo.getMobile() + ")");
            }
            if (idCardList.contains(staffInfo.getIdCard())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复身份证(" + staffInfo.getIdCard() + ")");
            }

            mobileList.add(staffInfo.getMobile());
            idCardList.add(staffInfo.getIdCard());

            SysDictData dictData = dictDataList.stream().filter(x -> x.getItemName().equals(staffInfo.getJobTitleName())).findAny().orElse(null);
            if (dictData == null) {
                throw new BizException(CodeEnum.NOT_EXIST, "职称");
            }
            int jobTitle = Integer.valueOf(dictData.getItemVal());
            this.saveImportStaff(staffInfo, jobTitle, maxJobNumber);
            maxJobNumber++;
        }
    }

    /**
     * 根据职工id查询
     *
     * @param staffId 职工id
     * @return
     */
    @Override
    public Map queryByStaffId(String staffId) {
        Map<String, Object> result = staffInfoMapper.queryByStaffId(staffId);
        if (result != null) {
            String faceImage = result.get("faceImage") == null ? "" : String.valueOf(result.get("faceImage"));
            result.put("faceImage", picUtils.imageFristDomain(faceImage));
        }
        return result;
    }

    /**
     * 根据姓名或手机号查询
     *
     * @param subType   子类型
     * @param paraValue 姓名或手机号
     * @param markState 职工是否激活 Y：APP激活 ALL：职工不等于已删除就行
     * @return
     */
    @Override
    public List<Map> selectList(String subType, String paraValue, String markState) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("type", subType);
        map.put("paraValue", paraValue);
        map.put("markState", markState);
        return staffInfoMapper.selectList(map);
    }

    /**
     * 需要推送的职工
     *
     * @param orgId    学校id
     * @param staffId  职工id
     * @param jobTitle 职称
     * @return
     */
    @Override
    public List<PushMoveStaff> findMovePushStaff(String orgId, String staffId, String jobTitle) {
        return staffInfoMapper.findMovePushStaff(orgId, staffId, jobTitle);
    }

    /**
     * 导入保存处理
     *
     * @param request
     */
    private void saveImportStaff(StaffInfoImportRequest request, int jobTitle, Long maxJobNumber) {
        String orgId = SecurityUtils.getOrgId();
        //身份证验证
        String idCard = request.getIdCard();

        //根据身份证查询
        ScStaffInfo dbStaffInfo = staffInfoMapper.selectByIdCard(idCard);
        ScMoveAccOther moveAccOther = new ScMoveAccOther();
        BeanUtils.copyProperties(request, moveAccOther);

        //保存职工信息
        ScStaffInfo staffInfo = new ScStaffInfo();
        BeanUtils.copyProperties(request, staffInfo);
        staffInfo.setJobTitle(jobTitle);
        Boolean healthCertificate = request.getHealthCertificate().equals(SysEnableEnum.YES.getName());
        staffInfo.setHealthCertificate(healthCertificate);

        if (dbStaffInfo == null) {
            //新增职工信息
            Long count = staffInfoMapper.countByMobile(request.getMobile());
            if (count > 0) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该手机号");
            }

            //保存移动端账号信息和账号扩展信息
            String accId = moveAccService.saveMoveAcc(
                    null,
                    request.getTrueName(),
                    request.getMobile(),
                    AccTypeEnum.STAFF.getCode(),
                    orgId,
                    moveAccOther,
                    SysEnableEnum.NO.getValue());

            staffInfo.setAccId(accId);
            staffInfo.setOrgId(orgId);
            staffInfo.setStaffId(UUIDG.generate());
            staffInfo.setStateMark(Status.normal.value());
            staffInfo.setCreateDate(new Date());
            staffInfo.setJobNumber(maxJobNumber);
            staffInfoMapper.insert(staffInfo);
        } else {
            Long count = staffInfoMapper.countByMobileNotIdCard(request.getMobile(), request.getIdCard());
            if (count > 0) {
                throw new BizException(CodeEnum.ALREADY_EXIST, "该手机号");
            }

            //修改职工信息
            staffInfo.setAccId(dbStaffInfo.getAccId());
            staffInfoMapper.modify(staffInfo);

            //保存移动端账号信息和账号扩展信息
            moveAccService.saveMoveAcc(
                    dbStaffInfo.getAccId(),
                    request.getTrueName(),
                    request.getMobile(),
                    AccTypeEnum.STAFF.getCode(),
                    orgId,
                    moveAccOther,
                    SysEnableEnum.NO.getValue());
        }
    }

    /**
     * 导入前的判断
     *
     * @param request 导入请求
     */
    private void validImportSave(StaffInfoImportRequest request) {
        //老师姓名
        if (StringUtils.isEmpty(request.getTrueName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "姓名");
        }
        //手机号验证
        String mobile = request.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "手机号");
        }
        boolean valIdPhone = ParamCheckUtil.verifyPhone(mobile);
        if (!valIdPhone) {
            throw new BizException(BasicCodeEnum.VALID_PHONE);
        }
        //身份证验证
        String idCard = request.getIdCard();
        //身份证
        if (StringUtils.isEmpty(idCard)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "身份证");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(idCard);
        if (!valIdCard) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "身份证(" + idCard + ")验证不通过");
        }
        //职称
        if (StringUtils.isEmpty(request.getJobTitleName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "职称");
        }
        //是否有健康证
        if (StringUtils.isEmpty(request.getHealthCertificate())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "是否有健康证");
        }
    }


    /**
     * 根据职工姓名查询
     *
     * @param orgId     学校id
     * @param staffName 职工姓名
     * @param jobNumber 职工工号
     * @return
     */
    @Override
    public ScStaffInfo selectByStaffName(String orgId, String staffName, String jobNumber) {
        return staffInfoMapper.selectByStaffName(orgId, staffName, jobNumber);
    }

    /**
     * 查询所有职工信息集合
     *
     * @param request 操作职工信息对象
     * @return 操作职工信息集合
     */
    @Override
    public List<Map> selectStaffInfoList(StaffInfoSearchRequest request) {
        Map<String, Object> map = new HashMap<>(0);
        return staffInfoMapper.selectListByPage(map);
    }


    /**
     * 同步职工健康码状态
     *
     * @param staffId
     * @param healthState
     * @return
     */
    @Override
    public Integer syncStaffHealthState(String staffId, int healthState) {
        return staffInfoMapper.syncStaffHealthState(staffId, healthState);
    }
}