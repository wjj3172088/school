package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.constants.BasicKeyConstants;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.ScStudent;
import com.qh.basic.domain.SchoolExt;
import com.qh.basic.domain.vo.StudentExportVo;
import com.qh.basic.domain.vo.StudentExtendVo;
import com.qh.basic.enums.BasicCodeEnum;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.enums.Status;
import com.qh.basic.mapper.ScStudentMapper;
import com.qh.basic.model.request.student.StudentImportRequest;
import com.qh.basic.model.request.student.StudentModifyRequest;
import com.qh.basic.model.request.student.StudentSearchRequest;
import com.qh.basic.service.*;
import com.qh.basic.utils.StringConstantUtils;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.ParamCheckUtil;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.web.domain.R;
import com.qh.common.redis.service.RedisService;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@Service
public class ScStudentServiceImpl extends ServiceImpl<ScStudentMapper, ScStudent> implements IScStudentService {
    @Autowired
    private ScStudentMapper studentMapper;
    @Autowired
    private IScApplyService applyService;
    @Autowired
    private IScGraduateService graduateService;
    @Autowired
    private IScGraduateDetailService graduateDetailService;
    @Autowired
    private RemoteDictDataService remoteDictDataService;
    @Autowired
    private IScClassService classService;
    @Autowired
    private IScTeacherService teacherService;
    @Autowired
    private IScMoveAccService moveAccService;
    @Autowired
    private IScParentStudentService parentStudentService;
    @Autowired
    private ISchoolExtService schoolExtService;
    @Autowired
    private RedisService redisService;

    /**
     * 查询学生信息集合
     *
     * @param request 操作学生信息对象
     * @return 操作学生信息集合
     */
    @Override
    public IPage<Map> selectScStudentListByPage(IPage<ScStudent> page, StudentSearchRequest request) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("stuName", request.getStuName());
        map.put("classId", request.getClassId());
        map.put("tagNum", request.getTagNum());
        map.put("guardianMobile", request.getGuardianMobile());
        return studentMapper.selectListByPage(page, map);
    }

    /**
     * 根据班级、姓名或手机号查询
     *
     * @param classId   班级
     * @param paraValue 姓名或手机号
     * @return
     */
    @Override
    public List<Map> selectList(String classId, String paraValue) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("classId", classId);
        map.put("paraValue", paraValue);
        return studentMapper.selectList(map);
    }

    /**
     * 判断是否存在
     *
     * @param classId 班级id
     * @return
     */
    @Override
    public boolean existByClassId(String classId) {
        QueryWrapper<ScStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("class_id", classId);
        queryWrapper.ne("state_mark", Status.delete.value());
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 转班
     *
     * @param stuIdList 学生id集合
     * @param classId   转入的班级id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(List<String> stuIdList, String classId) {
        if (StringUtils.isEmpty(classId)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "班级不能为空");
        }
        List<ScStudent> studentList = this.selectByStuIdList(stuIdList);
        if (CollectionUtils.isEmpty(studentList)) {
            throw new BizException(CodeEnum.NOT_EXIST, "所选学生");
        }

        //修改所属班级
        studentMapper.updateClassByStuId(stuIdList, classId);

        //新增申请
        applyService.add(classId, studentList);
    }

    /**
     * 根据年级查询
     * 使用分页查询，学生数量可能会比较多，所以使用分页查询
     *
     * @param orgId       学校id
     * @param orgName     学校名称
     * @param gradeList   年级列表
     * @param classIdList 班级id
     * @return 学生列表
     */
    @Override
    public void graduate(String orgId, String orgName, List<Integer> gradeList, List<String> classIdList) {
        //分批保存毕业详情信息
        int pageIndex = 1;
        int pageSize = 100;
        HashMap<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("gradeList", gradeList);
        queryMap.put("orgId", orgId);
        Page<StudentExtendVo> page = new Page<>(pageIndex, pageSize);
        IPage<StudentExtendVo> pageData = studentMapper.selectStudentExtendVoByPage(page, queryMap);
        if (pageData == null || CollectionUtils.isEmpty(pageData.getRecords())) {
            return;
        }

        // 亲属关系字典列表
        List<SysDictData> guarRelationList = this.selectDictData(DictTypeEnum.GUAR_RELATION.getValue(), DictTypeEnum.GUAR_RELATION.getName());

        // 新增毕业详情
        graduateDetailService.add(pageData.getRecords(), guarRelationList);

        long pages = pageData.getPages();
        long totalCount = pageData.getTotal();
        while (pageIndex < pages && pageSize <= pageData.getSize()) {
            pageIndex++;
            page = new Page<>(pageIndex, pageSize);
            IPage<StudentExtendVo> childPageData = studentMapper.selectStudentExtendVoByPage(page, queryMap);
            if (childPageData == null || CollectionUtils.isEmpty(childPageData.getRecords())) {
                break;
            }
            // 新增毕业详情
            graduateDetailService.add(childPageData.getRecords(), guarRelationList);
        }

        //新增毕业总览信息
        graduateService.add(orgId, orgName, totalCount);

        //设置学生毕业
        for (String classId : classIdList) {
            studentMapper.graduate(orgId, classId);
        }
    }

    /**
     * 导入
     *
     * @param studentImportRequestList 学生导入请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<StudentImportRequest> studentImportRequestList) {
        // 获取年级信息
        List<SysDictData> gradeList = this.selectDictData(DictTypeEnum.GRADE.getValue(), DictTypeEnum.GRADE.getName());
        // 获取家属关系信息
        List<SysDictData> guarRelationList = this.selectDictData(DictTypeEnum.GUAR_RELATION.getValue(), DictTypeEnum.GUAR_RELATION.getName());
        // 获取过期时间
        Date expireTime = this.getExpireTime(SecurityUtils.getOrgId());

        List<String> tagNumList = new ArrayList<>();
        List<String> idCardList = new ArrayList<>();
        for (StudentImportRequest request : studentImportRequestList) {
            this.validImport(request);
            // 判断学生ID卡和身份证号在导入的表格中是否有重复
            if (tagNumList.contains(request.getTagNum())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复学生ID卡(" + request.getTagNum() + ")");
            }
            if (idCardList.contains(request.getIdCard())) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内存在重复身份证(" + request.getIdCard() + ")");
            }
            tagNumList.add(request.getTagNum());
            idCardList.add(request.getIdCard());

            // 年级
            int grade = this.selectIntCodeByName(request.getGradeName(), gradeList, DictTypeEnum.GRADE.getName());
            // 家属关系
            int guardianRelation = this.selectIntCodeByName(request.getGuardianRelation(), guarRelationList, DictTypeEnum.GUAR_RELATION.getName());

            // 实现导入的保存
            this.doImportSave(request, grade, request.getGradeName(), guardianRelation, expireTime);
        }
    }

    /**
     * 修改学生
     *
     * @param request 学生修改请求
     */
    @Override
    public void modify(StudentModifyRequest request) {
        if (request.getTagNum().length() > BasicKeyConstants.TAG_NUM_LENGTH) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内学生ID卡(" + request.getTagNum() + ")超过8" + "位数，请检查后再导入!");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(request.getIdCard());
        if (!valIdCard) {
            throw new BizException(BasicCodeEnum.VALID_CARD);
        }
        boolean tagNumResult = this.exsitByTagNumAndNotStuId(request.getStuId(), request.getTagNum());
        if (tagNumResult) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "系统已存在的学生ID卡(" + request.getTagNum() + ")");
        }
        boolean idCardResult = this.exsitByIdCardAndNotStuId(request.getStuId(), request.getIdCard());
        if (idCardResult) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "系统已存在的身份证号码(" + request.getIdCard() + ")");
        }

        ScStudent student = new ScStudent();
        BeanUtils.copyProperties(request, student);
        student.setModifyDate(new Date());
        this.studentMapper.updateById(student);
    }

    /**
     * 根据条件查询学生导出列表
     *
     * @param request 导出筛选条件
     * @return
     */
    @Override
    public List<StudentExportVo> selectExportList(StudentSearchRequest request) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("stuName", request.getStuName());
        map.put("classId", request.getClassId());
        map.put("tagNum", request.getTagNum());
        map.put("guardianMobile", request.getGuardianMobile());
        return this.baseMapper.selectExportList(map);
    }

    /**
     * 批量删除学生
     *
     * @param stuIdList 学生id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByStuIdList(List<String> stuIdList) {
        if (CollectionUtils.isEmpty(stuIdList)) {
            throw new BizException(CodeEnum.LIST_EMPTY);
        }
        List<ScStudent> studentList = this.selectByStuIdList(stuIdList);
        // 删除学生
        this.baseMapper.delStudent(SecurityUtils.getOrgId(), stuIdList);
        // 设置家长的默认孩子,查询当前关系表中需要删除的学生中是默认孩子的数据，这些数据才需要设置家长的默认孩子，并且需要根据acc_id分组，因为一个家长可能有超过两个孩子的情况，在剩下的孩子找出一个ps_id最小的原先的def=0的作为当前家长的默认孩子
        parentStudentService.setParentDefaultChild(stuIdList);
        // 删除学生和家长关系
        parentStudentService.deleteByStuIdList(stuIdList);
        // 删除redis学生标签信息
        studentList.forEach(student -> {
            redisService.remove(StringConstantUtils.SSCL_STUDENT_INFO, student.getTagNum());
            redisService.deleteKeys(String.format(StringConstantUtils.SSCL_ACC_TO_SETUDENT_LIST, student.getAccId(), "*"));
        });
    }

    /**
     * 毕业人数
     *
     * @param orgId     所属学校id
     * @param gradeList 毕业年级列表
     * @return
     */
    @Override
    public Integer countGraduate(String orgId, List<Integer> gradeList) {
        HashMap<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("gradeList", gradeList);
        queryMap.put("orgId", orgId);
        return studentMapper.countGraduate(queryMap);
    }

    /**
     * 获取过期时间
     *
     * @return
     */
    private Date getExpireTime(String orgId) {
        Date toDay = new Date();
        String strBeFort = DateUtils.getDayBeforeNum(DateUtils.format(toDay, DateUtils.yyyy_MM_dd_HH_mm_ss), 1);
        Date toBeFort = DateUtils.parse(strBeFort + " 23:59:59", DateUtils.yyyy_MM_dd_HH_mm_ss);
        // 获取学校的app使用时间
        SchoolExt schoolExt = schoolExtService.selectSchoolByOrgId(orgId);
        if (schoolExt == null) {
            return toBeFort;
        }
        Date schoolExpire = schoolExt.getProbation();
        if (schoolExpire != null && schoolExpire.after(toBeFort)) {
            // 如果学校的时间比当前天的前一天还晚，就设置成学校的时间
            toBeFort = schoolExpire;
        }
        return toBeFort;
    }

    /**
     * 实现导入的保存
     *
     * @param request          导入请求
     * @param grade            年级
     * @param grade            年级名称
     * @param guardianRelation 家属关系
     * @param expireTime       过期时间
     */
    private void doImportSave(StudentImportRequest request, int grade, String gradeName, int guardianRelation, Date expireTime) {
        String tagNum = request.getTagNum();
        String idCard = request.getIdCard();
        String orgId = SecurityUtils.getOrgId();

        boolean tagNumResult = this.exsitByTagNum(tagNum);
        if (tagNumResult) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统已存在的学生ID卡(" + tagNum + ")");
        }
        boolean idCardResult = this.exsitByIdCard(idCard);
        if (idCardResult) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统已存在的身份证号码(" + idCard + ")");
        }

        // 判断班级是否存在
        ScClass scClass = classService.queryByClassNum(grade, request.getClassName());
        if (scClass == null || scClass.getStateMark().equals(Status.delete.value())) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格出现系统不存在的班级(" + gradeName + request.getClassName() + ")");
        }

        // 判断导入时表格内出现系统已存在的手机号是否是老师身份
        boolean mobileResult = teacherService.exsitByMobile(request.getGuardianMobile());
        if (mobileResult) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内家长手机号(" + request.getGuardianMobile() + ")在系统已经是老师身份，请检查后再导入!");
        }

        // 新增学生信息
        ScStudent student = new ScStudent();
        student.setStuId(UUIDG.generate());
        student.setIdCard(idCard);
        student.setStuName(request.getStuName());
        student.setClassId(scClass.getClassId());
        student.setTagNum(tagNum);
        student.setGuardianName(request.getGuardianName());
        student.setGuardianRelation(guardianRelation);
        student.setGuardianMobile(request.getGuardianMobile());
        student.setStateMark(Status.normal.value());
        student.setAwayState(Status.normal.value());
        student.setOrgId(orgId);
        student.setCreateDate(new Date());
        student.setModifyDate(new Date());
        studentMapper.insert(student);

        // 保存移动账号相关信息
        String accId = moveAccService.saveMoveAcc(orgId, request.getGuardianMobile());

        // 保存家长和学生的关系
        parentStudentService.save(accId, expireTime, student);
    }

    /**
     * 根据标签号查询是否存在
     *
     * @param tagNum 标签号
     * @return
     */
    private boolean exsitByTagNum(String tagNum) {
        QueryWrapper<ScStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("tag_num", tagNum);
        queryWrapper.ne("state_mark", Status.delete.value());
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据身份证号查询是否存在
     *
     * @param idCard 身份证号
     * @return
     */
    private boolean exsitByIdCard(String idCard) {
        QueryWrapper<ScStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("id_card", idCard);
        queryWrapper.ne("state_mark", Status.delete.value());
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据标签号查询是否存在
     *
     * @param tagNum 标签号
     * @return
     */
    private boolean exsitByTagNumAndNotStuId(String stuId, String tagNum) {
        QueryWrapper<ScStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("tag_num", tagNum);
        queryWrapper.ne("state_mark", Status.delete.value());
        queryWrapper.ne("stu_id", stuId);
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据身份证号查询是否存在
     *
     * @param idCard 身份证号
     * @return
     */
    private boolean exsitByIdCardAndNotStuId(String stuId, String idCard) {
        QueryWrapper<ScStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq("id_card", idCard);
        queryWrapper.ne("state_mark", Status.delete.value());
        queryWrapper.ne("stu_id", stuId);
        return studentMapper.selectCount(queryWrapper) > 0;
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
            throw new BizException(CodeEnum.NOT_EXIST, "表格出现系统不存在的" + tipMsg + "(" + dictData.getItemName() + ")");
        }
        return Integer.valueOf(dictData.getItemVal());
    }

    /**
     * 校验导入参数
     *
     * @param request 导入请求
     */
    private void validImport(StudentImportRequest request) {
        if (StringUtils.isEmpty(request.getTagNum())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学生ID卡");
        }
        if (request.getTagNum().length() > BasicKeyConstants.TAG_NUM_LENGTH) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "表格内学生ID卡(" + request.getTagNum() + ")超过8" + "位数，请检查后再导入!");
        }
        if (StringUtils.isEmpty(request.getStuName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学生姓名");
        }
        if (StringUtils.isEmpty(request.getIdCard())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学生身份证");
        }
        boolean valIdCard = ParamCheckUtil.validIdCard(request.getIdCard());
        if (!valIdCard) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "学生身份证(" + request.getIdCard() + ")验证不通过");
        }
        if (StringUtils.isEmpty(request.getGuardianName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "家长姓名");
        }
        if (StringUtils.isEmpty(request.getGuardianRelation())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "亲属关系");
        }
        if (StringUtils.isEmpty(request.getGuardianMobile())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "家长手机号");
        }
        if (StringUtils.isEmpty(request.getGradeName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "年级");
        }
        if (StringUtils.isEmpty(request.getClassName())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "班级");
        }
    }

    /**
     * 根据学生id集合查询
     *
     * @param stuIdList 学生id集合
     * @return
     */
    private List<ScStudent> selectByStuIdList(List<String> stuIdList) {
        if (CollectionUtils.isEmpty(stuIdList)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "学生不能为空");
        }
        String stuIdSql = stuIdList.stream().map(x -> "'" + x + "'").collect(Collectors.joining(","));
        Map<String, Object> map = new HashMap<>(2);
        map.put("orgId", SecurityUtils.getOrgId());
        map.put("stuIdList", stuIdSql);
        return studentMapper.selectStudentByMap(map);
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
}