package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.StaffHealthVo;
import com.qh.basic.domain.*;
import com.qh.basic.domain.vo.ClassTeacherVo;
import com.qh.basic.domain.vo.PushMoveTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.enums.*;
import com.qh.basic.mapper.ScTeacherMapper;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.basic.model.request.teacher.TeacherImportRequest;
import com.qh.basic.api.model.request.teacher.TeacherSaveRequest;
import com.qh.basic.service.*;
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
 * 教师信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@Service
public class ScTeacherServiceImpl extends ServiceImpl<ScTeacherMapper, ScTeacher> implements IScTeacherService {
    @Autowired
    private ScTeacherMapper teacherMapper;
    @Autowired
    private IScTeacClassService teacClassService;
    @Autowired
    private IScMoveAccService moveAccService;
    @Autowired
    private RemoteDictDataService remoteDictDataService;
    @Autowired
    private IScStaffInfoService staffInfoService;
    @Autowired
    private IScClassService classService;
    @Autowired
    private IScSubjectService subjectService;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询教师信息集合
     *
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    @Override
    public IPage<Map> selectScTeacherListByPage(IPage<ScTeacher> page, ScTeacher scTeacher) {
        scTeacher.setOrgId(SecurityUtils.getOrgId());
        return teacherMapper.selectListByPage(page, scTeacher);
    }

    /**
     * 查询教职工健康状态信息集合
     *
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    @Override
    public IPage<StaffHealthVo> selectHealthListByPage(IPage<ScTeacher> page, ScTeacher scTeacher) {
        scTeacher.setOrgId(SecurityUtils.getOrgId());
        return teacherMapper.selectHealthListByPage(page, scTeacher);
    }



    /**
     * 健康码导出
     *
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    @Override
    public List<StaffHealthVo> findHealthExcel(ScTeacher scTeacher) {
        scTeacher.setOrgId(SecurityUtils.getOrgId());
        return teacherMapper.selectHealthListByPage( scTeacher);
    }

    /**
     * 导出
     *
     * @param request 教师导出请求
     * @return
     */
    @Override
    public List<TeacherExportVo> findAllExcelData(TeacherExportSearchRequest request) {
        request.setOrgId(SecurityUtils.getOrgId());
        return teacherMapper.findAllByExport(request);
    }

    /**
     * 保存教师信息
     *
     * @param request 教师信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeacher(TeacherSaveRequest request) {
        //身份证验证
        String idCard = request.getIdCard();
        boolean valIdCard = ParamCheckUtil.validIdCard(idCard);
        if (!valIdCard) {
            throw new BizException(BasicCodeEnum.VALID_CARD);
        }

        //手机号验证
        String mobile = request.getMobile();
        boolean valIdPhone = ParamCheckUtil.verifyPhone(mobile);
        if (!valIdPhone) {
            throw new BizException(BasicCodeEnum.VALID_PHONE);
        }

//        //老师授课科目
//        ScSubject scSubject = subjectService.selectBySubjectName(request.getSpecialtyName());
//        if (scSubject == null) {
//            throw new BizException(CodeEnum.NOT_EXIST, "该授课科目");
//        }
        //获取授课科目信息
        List<SysDictData> specialtyList = this.selectDictData(DictTypeEnum.SPECIALTY.getValue(), DictTypeEnum.SPECIALTY.getName());
        Integer specialty = this.selectIntCodeByName(request.getSpecialtyName(), specialtyList,
                DictTypeEnum.SPECIALTY.getName());

        //判断班级是否存在
        ScClass scClass = classService.queryBasicByClassId(request.getClassId());
        if (scClass == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该班级");
        }

        //保存移动端账号信息和账号扩展信息
        ScMoveAccOther moveAccOther = new ScMoveAccOther();
        BeanUtils.copyProperties(request, moveAccOther);

        //保存职工信息
        ScTeacher teacher = new ScTeacher();
        BeanUtils.copyProperties(request, teacher);
        teacher.setModifyDate(new Date());
        teacher.setSpecialty(specialty);

        //获取该学校的最新最大的工号
        Long maxJobNumber = teacherMapper.selectMaxJobNumberByOrgId(SecurityUtils.getOrgId());
        teacher.setJobNumber(maxJobNumber);

        String teachId = "";
        if (StringUtils.isEmpty(request.getTeacId())) {
            //判断该班级是否已经存在班主任
            if (request.getDirect().equals(SysEnableEnum.YES.getValue())) {
                ClassTeacherVo classTeacherVo = teacClassService.selectByClassId(request.getClassId());
                if (classTeacherVo != null) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该班级已经存在班主任");
                }
            }
            //保存移动端账号信息和账号扩展信息
            String accId = moveAccService.saveMoveAcc(null, request.getTeacName(), request.getMobile(), AccTypeEnum.TEACHER.getCode(), SecurityUtils.getOrgId(), moveAccOther, request.getDirect());

            //新增教师信息
            long count = teacherMapper.countByIdCardAndTeacId(teacher);
            if (count > 0) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该身份证号已存在，请重新输入");
            }
            //验证手机号是否存在
            long countMobile = teacherMapper.countByMobile(teacher.getMobile());
            if (countMobile > 0) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该手机号已存在");
            }
            teachId = UUIDG.generate();
            teacher.setOrgId(SecurityUtils.getOrgId());
            teacher.setAccId(accId);
            teacher.setStateMark(Status.normal.value());
            teacher.setTeacId(teachId);
            teacher.setCreateDate(new Date());
            teacher.setModifyDate(new Date());
            teacherMapper.insert(teacher);
        } else {
            //判断该班级是否已经存在班主任
            if (request.getDirect().equals(SysEnableEnum.YES.getValue())) {
                ClassTeacherVo classTeacherVo = teacClassService.selectByClassId(request.getClassId());
                if (classTeacherVo != null && !classTeacherVo.getTeacId().equals(request.getTeacId())) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该班级已经存在班主任");
                }
            }
            if (request.getDirect().equals(SysEnableEnum.YES.getValue())) {
                ClassTeacherVo classTeacherVo = teacClassService.selectByTeacId(request.getTeacId());
                if (classTeacherVo != null) {
                    if (classTeacherVo.getGrade() != scClass.getGrade() || !classTeacherVo.getClassNum().equals(scClass.getClassNum())) {
                        throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该老师已经是其他班级的班主任");
                    }
                }
            }
            //保存教师信息
            long count = teacherMapper.countByIdCardAndNotTeacId(teacher);
            if (count > 0) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该身份证号已存在，请重新输入");
            }
            //验证手机号是否存在
            long countMobile = teacherMapper.countByMobileAndNotTeacId(teacher);
            if (countMobile > 0) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该手机号已存在");
            }
            ScTeacher dbTeacher = teacherMapper.queryInfoByTeacId(request.getTeacId());
            teachId = dbTeacher.getTeacId();
            teacherMapper.modify(teacher);

            //保存移动端账号信息和账号扩展信息
            moveAccService.saveMoveAcc(dbTeacher.getAccId(), request.getTeacName(), request.getMobile(), AccTypeEnum.TEACHER.getCode(), SecurityUtils.getOrgId(), moveAccOther, request.getDirect());
        }

        //新增班级和老师的关系
        teacClassService.add(scClass.getClassId(), teachId, request.getDirect(), request.getSpecialtyName(), false);
    }

    /**
     * 根据教师id批量删除
     *
     * @param idList 教师id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteById(List<String> idList) {
        if (idList == null || idList.size() == 0) {
            return;
        }

        StringBuilder sbMsg = new StringBuilder();
        List<ScTeacher> teacherList = teacherMapper.queryListByTeacId(idList, null);
        if (teacherList != null && teacherList.size() > 0) {
            for (ScTeacher teacher : teacherList) {
                if (teacher.getDirect() != null && teacher.getDirect().equals(SysEnableEnum.YES.getValue())) {
                    sbMsg.append(teacher.getTeacName() + "是班主任，请不要删除。</br>");
                } else {
                    //移动账号表状态更新为已删除
                    moveAccService.updateStateMark(teacher.getAccId(), teacher.getMobile(), Status.delete.value());
                }
            }
        }

        if (!StringUtils.isEmpty(sbMsg)) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, sbMsg.toString());
        }

        //通过teacId删除班级和老师的关系
        teacClassService.delClassTeachByTeachId(idList);
        //根据教师id删除
        teacherMapper.batchDelByTeachId(idList);
    }

    /**
     * 根据老师id查找
     *
     * @param techId 老师id
     * @return
     */
    @Override
    public ScTeacher selectByTechId(String techId) {
        QueryWrapper<ScTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("teac_id", techId);
        return teacherMapper.selectOne(queryWrapper);
    }

    /**
     * 通过classId查询班主任的信息
     *
     * @param classIdList 班级id
     * @return
     */
    @Override
    public List<ScTeacher> queryHeadmasterByClassId(List<String> classIdList) {
        return teacherMapper.queryHeadmasterByClassId(classIdList);
    }

    /**
     * 根据教师id查询
     *
     * @param teacId 教师id
     * @return
     */
    @Override
    public Map queryByTeacId(String teacId) {
        Map<String, Object> result = teacherMapper.queryByTeacId(teacId);
        if (result != null) {
            String faceImage = result.get("faceImage") == null ? "" : String.valueOf(result.get("faceImage"));
            result.put("faceImage", picUtils.imageFristDomain(faceImage));
        }
        return result;
    }

    /**
     * 导入
     *
     * @param teacherList 教师信息集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<TeacherImportRequest> teacherList) {
        //获取授课科目信息
        List<SysDictData> specialtyList = this.selectDictData(DictTypeEnum.SPECIALTY.getValue(), DictTypeEnum.SPECIALTY.getName());
        //获取教师职称信息
        List<SysDictData> jobTitleList = this.selectDictData(DictTypeEnum.JOB_TITLE.getValue(), DictTypeEnum.JOB_TITLE.getName());
        //获取年级信息
        List<SysDictData> gradeList = this.selectDictData(DictTypeEnum.GRADE.getValue(), DictTypeEnum.GRADE.getName());
        //获取是否班主任信息
        List<SysDictData> directDictDataList = this.selectDictData(DictTypeEnum.YES_NO.getValue(), DictTypeEnum.YES_NO.getName());

        List<String> mobileList = new ArrayList<>();
        List<String> idCardList = new ArrayList<>();
        List<String> directTeacherList = new ArrayList<>();
        //获取该学校的最新最大的工号
        Long maxJobNumber = teacherMapper.selectMaxJobNumberByOrgId(SecurityUtils.getOrgId());
        for (TeacherImportRequest teacher : teacherList) {
            //导入前的判断
            this.validImportSave(teacher);

            //判断手机号和身份证号在导入的表格中是否有重复
            if (mobileList.contains(teacher.getMobile())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复手机号(" + teacher.getMobile() + ")");
            }
            if (idCardList.contains(teacher.getIdCard())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复身份证(" + teacher.getIdCard() + ")");
            }

            mobileList.add(teacher.getMobile());
            idCardList.add(teacher.getIdCard());

            //是否班主任
            String direct = this.selectStringCodeByName(teacher.getDirect(), directDictDataList, DictTypeEnum.YES_NO.getName());
            if (direct.equals(SysEnableEnum.YES.getValue())) {
                //是班主任的时候
                String directInfo = teacher.getGradeName() + " " + teacher.getClassNumName();
                if (directTeacherList.contains(directInfo)) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内班级出现多个班主任信息(" + directInfo + ")");
                }
                directTeacherList.add(directInfo);
            }
            //授课科目
            int specialty = this.selectIntCodeByName(teacher.getSpecialtyName(), specialtyList, DictTypeEnum.SPECIALTY.getName());
            //教师职称
            int jobTitle = this.selectIntCodeByName(teacher.getJobTitle(), jobTitleList, DictTypeEnum.JOB_TITLE.getName());
            //年级
            int grade = this.selectIntCodeByName(teacher.getGradeName(), gradeList, DictTypeEnum.GRADE.getName());

            //保存教师导入信息
            this.saveImportTeacher(teacher, specialty, jobTitle, grade, direct,maxJobNumber);
            maxJobNumber++;
        }
    }

    /**
     * 根据code值查询字典信息
     *
     * @param code   字典key
     * @param tipMsg 提示信息
     * @return
     */
    private List<SysDictData> selectDictData(String code, String tipMsg) {
        //获取字典信息
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode(code);
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, tipMsg + "未配置");
        }
        return dictDataResult.getData();
    }

    /**
     * 根据字典名字查询值
     *
     * @param name            字典名字
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    private int selectIntCodeByName(String name, List<SysDictData> sysDictDataList, String tipMsg) {
        SysDictData dictData =
                sysDictDataList.stream().filter(x -> x.getItemName().equals(name)).findAny().orElse(null);
        if (dictData == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "表格出现系统不存在的" + tipMsg + "(" + name + ")");
        }
        return Integer.valueOf(dictData.getItemVal());
    }

    /**
     * 根据字典名字查询值
     *
     * @param name            字典名字
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    private String selectStringCodeByName(String name, List<SysDictData> sysDictDataList, String tipMsg) {
        SysDictData jobTitleDictData =
                sysDictDataList.stream().filter(x -> x.getItemName().equals(name)).findAny().orElse(null);
        if (jobTitleDictData == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "表格出现系统不存在的" + tipMsg + "(" + jobTitleDictData.getItemName() + ")");
        }
        return jobTitleDictData.getItemVal();
    }

    /**
     * 根据姓名或手机号查询
     *
     * @param type      教师/职工
     * @param subType   子类型
     * @param paraValue 姓名或手机号
     * @return
     */
    @Override
    public List<Map> selectList(String type, String subType, String paraValue) {
        if (type.equals(AccTypeEnum.TEACHER.getCode())) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("orgId", SecurityUtils.getOrgId());
            map.put("paraValue", paraValue);
            //职工是否激活 Y：针对APP激活的 ALL：职工不等于已删除就行
            map.put("markState", "Y");
            return teacherMapper.selectList(map);
        } else if (type.equals(AccTypeEnum.STAFF.getCode())) {
            return staffInfoService.selectList(subType, paraValue,"Y");
        } else {
            return null;
        }
    }


    /**
     * 获取学校总体教职工通讯录
     * @param paraValue 姓名或手机号
     * @return
     */
    @Override
    public List<Map> mailAllList(String paraValue) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("paraValue", paraValue);
        //职工是否激活 Y：针对APP激活的 ALL：职工不等于已删除就行
        map.put("markState", "ALL");
        List<Map> teacherList = teacherMapper.selectList(map);
        List<Map> staffList = staffInfoService.selectList("", paraValue,"ALL");
        return new ArrayList<Map>(){{
            addAll(teacherList);
            addAll(staffList);
        }};
    }

    /**
     * 需要推送的老师
     *
     * @param orgId  学校id
     * @param teacId 老师id
     * @return
     */
    @Override
    public List<PushMoveTeacher> findMovePushTeacher(String orgId, String teacId) {
        return teacherMapper.findMovePushTeacher(orgId, teacId);
    }

    /**
     * 根据教师姓名查询
     *
     * @param orgId    学校id
     * @param teacName 老师姓名
     * @param jobNumber 教师工号
     * @return
     */
    @Override
    public ScTeacher selectByTeacName(String orgId, String teacName, String jobNumber) {
        return teacherMapper.selectByTeacName(orgId, teacName,jobNumber);
    }

    /**
     * 根据手机号查看是否存在
     *
     * @param mobile 手机号
     * @return
     */
    @Override
    public boolean exsitByMobile(String mobile) {
        QueryWrapper<ScTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("mobile", mobile);
        queryWrapper.ne("state_mark", Status.delete.value());
        return teacherMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 导入前的判断
     *
     * @param request 导入请求
     */
    private void validImportSave(TeacherImportRequest request) {
        //老师姓名
        if (StringUtils.isEmpty(request.getTeacName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "老师姓名");
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
        if (StringUtils.isEmpty(idCard)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "身份证");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(idCard);
        if (!valIdCard) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "身份证(" + idCard + ")验证不通过");
        }
        //老师岗位
        if (StringUtils.isEmpty(request.getJobTitle())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "老师岗位");
        }
        //老师授课科目
        if (StringUtils.isEmpty(request.getSpecialtyName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "老师授课科目");
        }
        //年级
        if (StringUtils.isEmpty(request.getGradeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "年级");
        }
        //班级
        if (StringUtils.isEmpty(request.getClassNumName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "班级");
        }
        //是否班主任
        if (StringUtils.isEmpty(request.getDirect())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "是否班主任");
        }
    }

    /**
     * 导入保存处理
     *
     * @param request
     */
    private void saveImportTeacher(TeacherImportRequest request, int specialty, int jobTitle, int grade, String direct,Long jobNumber) {
        String idCard = request.getIdCard();

        //保存移动端账号信息和账号扩展信息
        ScMoveAccOther moveAccOther = new ScMoveAccOther();
        BeanUtils.copyProperties(request, moveAccOther);

        //保存职工信息
        ScTeacher teacher = new ScTeacher();
        BeanUtils.copyProperties(request, teacher);
        teacher.setSpecialty(specialty);
        teacher.setJobTitle(jobTitle);
        teacher.setJobNumber(jobNumber);

//        //老师授课科目
//        ScSubject scSubject = subjectService.selectBySubjectName(request.getSpecialtyName());
//        if (scSubject == null) {
//            throw new BizException(CodeEnum.NOT_EXIST, "授课科目(" + request.getSpecialtyName() + ")");
//        }

        //判断班级是否存在
        ScClass scClass = classService.queryByClassNum(grade, request.getClassNumName());
        if (scClass == null) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统不存在的班级：" + request.getGradeName() + " " + request.getClassNumName());
        }

        if (direct.equals(SysEnableEnum.YES.getValue())) {
            //查找当前年级班级是否存在班主任
            ClassTeacherVo classTeacherVo = teacClassService.selectByGradeAndClassNum(grade, request.getClassNumName());
            if (classTeacherVo != null) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "系统已经存在班主任(" + request.getGradeName() + " " + request.getClassNumName() + ")");
            }
        }

        ScTeacher dbTeacher = teacherMapper.selectByIdCard(idCard);
        if (dbTeacher != null) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统已存在身份证：" + teacher.getIdCard());
        }

        //验证手机号是否存在
        long countMobile = teacherMapper.countByMobile(teacher.getMobile());
        if (countMobile > 0) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统已存在的手机号：" + teacher.getMobile());
        }

        //保存移动端账号信息
        String accId = moveAccService.saveMoveAcc(
                null,
                request.getTeacName(),
                request.getMobile(),
                AccTypeEnum.TEACHER.getCode(),
                SecurityUtils.getOrgId(),
                moveAccOther,
                direct);

        //保存教师信息
        String teachId = UUIDG.generate();
        teacher.setOrgId(SecurityUtils.getOrgId());
        teacher.setAccId(accId);
        teacher.setStateMark(Status.normal.value());
        teacher.setTeacId(teachId);
        teacher.setCreateDate(new Date());
        teacherMapper.insert(teacher);

        //新增班级和老师的关系
        teacClassService.add(scClass.getClassId(), teachId, direct, request.getSpecialtyName(), false);
    }

    /**
     * 获取所有教师信息
     *
     * @param request 教师请求
     * @return
     */
    @Override
    public List<TeacherExportVo> findAllData(TeacherExportSearchRequest request) {
        return teacherMapper.findAllByExport(request);
    }

    /**
     * 同步教师健康码状态
     * @param teacId
     * @param healthState
     * @return
     */
    @Override
    public Integer syncTeacherHealthState(String teacId,int healthState ){
        return teacherMapper.syncTeacherHealthState(teacId,healthState);
    }

}