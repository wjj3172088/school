package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.constants.BasicKeyConstants;
import com.qh.basic.constants.RedisKeyConstants;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.vo.ClassExportVo;
import com.qh.basic.domain.vo.ClassTeacherVo;
import com.qh.basic.enums.*;
import com.qh.basic.mapper.ScClassMapper;
import com.qh.basic.model.request.scclass.ClassSearchRequest;
import com.qh.basic.model.response.graduate.GraduateInfoResponse;
import com.qh.basic.service.*;
import com.qh.basic.utils.StringConstantUtils;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.RandCodeUtil;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.web.domain.R;
import com.qh.common.redis.service.RedisService;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.RemoteSysConfigService;
import com.qh.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 班级Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
@Service
@Slf4j
public class ScClassServiceImpl extends ServiceImpl<ScClassMapper, ScClass> implements IScClassService {
    @Autowired
    private ScClassMapper classMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IScTeacClassService teacClassService;
    @Autowired
    private RemoteDictDataService remoteDictDataService;
    @Autowired
    private IScTeacherService teacherService;
    @Autowired
    private IScMoveAccService moveAccService;
    @Autowired
    private IScStudentService scStudentService;
    @Autowired
    private RemoteSysConfigService remoteSysConfigService;
    @Autowired
    private IScOperLogService operLogService;

    /**
     * 根据条件分页查询班级列表
     *
     * @param request 班级信息
     * @return 班级信息集合信息
     */
    @Override
    public IPage<ScClass> selectListByPage(IPage<ScClass> page, @Param("scClass") ClassSearchRequest request) {
        request.setOrgId(SecurityUtils.getOrgId());
        return classMapper.selectListByPage(page, request);
    }

    /**
     * 根据条件查询导出数据
     *
     * @param request
     * @return
     */
    @Override
    public List<ClassExportVo> findAllByExport(ClassSearchRequest request) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("grade", request.getGrade());
        map.put("classNum", request.getClassNum());
        map.put("teachName", request.getTeachName());
        map.put("orgId", SecurityUtils.getOrgId());
        return classMapper.findAllByExport(map);
    }

    /**
     * 根据学校查询
     *
     * @return
     */
    @Override
    public List<ScClass> findAllByOrgId() {
        return classMapper.findAllByOrgId(SecurityUtils.getOrgId());
    }

    /**
     * 根据学校查询
     *
     * @return
     */
    @Override
    public List<ScClass> findAllByOrgId(String orgId) {
        return classMapper.findAllByOrgId(orgId);
    }

    /**
     * 保存
     *
     * @param scClass
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveClass(ScClass scClass) {
        //获取班别信息
        R<SysDictData> dictDataResult = remoteDictDataService.getDictData(DictTypeEnum.CLASS_NUM.getValue(),
                scClass.getClassNum());
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(CodeEnum.NOT_EXIST, "该班别");
        }
        String classNum = dictDataResult.getData().getItemName();

        this.doSave(scClass, scClass.getGrade(), classNum);
    }

    /**
     * 根据班级id批量删除
     *
     * @param idList 班级id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteById(List<String> idList) {
        if (idList == null || idList.size() == 0) {
            return;
        }

        //判断当前需要删除的班级中是否存在班主任，如果存在，则需要先进行解绑
        List<ScTeacher> teacherList = teacherService.queryHeadmasterByClassId(idList);
        if (teacherList != null && teacherList.size() > 0) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "班级已分配班主任，请先进行解绑。");
        }

        //判断当前的班级id下是否存在学生信息
        for (String classId : idList) {
            boolean result = scStudentService.existByClassId(classId);
            if (result) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该班级下存在学生信息，不能删除");
            }
        }

        //删除班级信息
        classMapper.batchDelClass(idList);
        //批量根据班级id删除班级和老师的关系
        teacClassService.batchDeleteByClassId(idList);

        //清理缓存中老师和班级的邀请码
        for (String classId : idList) {
            ScClass scClass = classMapper.queryBasicByClassId(classId);
            if (scClass == null) {
                continue;
            }
            String teacCode = scClass.getTeacCode();
            String classCode = scClass.getClassCode();
            if (StringUtils.isNotBlank(teacCode)) {
                redisService.deleteObject(String.format(StringConstantUtils.SSCL_TEAC_CODE, teacCode));
            }
            if (StringUtils.isNotBlank(classCode)) {
                redisService.deleteObject(String.format(StringConstantUtils.SSCL_CLASS_CODE, classCode));
            }
        }
    }

    /**
     * 导入班级
     *
     * @param classList 班级信息集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<ScClass> classList) {
        //获取班别信息
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode(DictTypeEnum.GRADE.getValue());
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADE_NO_CONFIG);
        }
        List<SysDictData> dictDataList = dictDataResult.getData();

        for (ScClass scClass : classList) {
            SysDictData dictData = dictDataList.stream().filter(x -> x.getItemName().equals(scClass.getGradeName())).findAny().orElse(null);
            if (dictData == null) {
                throw new BizException(CodeEnum.NOT_EXIST, "该年级");
            }
            Integer grade = Integer.valueOf(dictData.getItemVal());
            this.doSaveImport(scClass, grade);
        }
    }

    /**
     * 根据Map条件读取班级信息
     *
     * @param map
     * @return
     */
    @Override
    public ScClass getClassByMap(Map<String, Object> map) {
        return classMapper.queryExistClass(map);
    }

    /**
     * 根据Map条件读取班级信息
     *
     * @param grade    年级
     * @param classNum 班级名称
     * @return
     */
    @Override
    public ScClass queryByClassNum(int grade, String classNum) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("grade", grade);
        map.put("classNum", classNum);
        map.put("orgId", SecurityUtils.getOrgId());
        return classMapper.queryExistClass(map);
    }

    /**
     * 根据班级id查询详情
     *
     * @param classId 班级id
     * @return
     */
    @Override
    public Map queryByClassId(String classId) {
        return classMapper.queryByClassId(classId);
    }

    /**
     * 解绑班主任
     *
     * @param classIdList 班级id集合
     */
    @Override
    public void unbind(List<String> classIdList) {
        if (CollectionUtils.isEmpty(classIdList)) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "没有需要解绑的数据");
        }
        //解绑班主任(通过老师id)
        teacClassService.updateClassTeacNullByClassId(classIdList);
    }

    /**
     * 根据班级id查询
     *
     * @param classId 班级id
     * @return
     */
    @Override
    public ScClass queryBasicByClassId(String classId) {
        return classMapper.queryBasicByClassId(classId);
    }

    /**
     * 根据班级id查询可用的班级信息
     *
     * @param classId 班级id
     * @return
     */
    @Override
    public ScClass queryEnableExtentByClassId(String classId) {
        return classMapper.queryEnableExtentByClassId(classId);
    }

    /**
     * 升学
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upgrade() {
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode("graduate_grade");
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADE_NO_CONFIG);
        }

        //获取配置的升学操作限制时间
        R<String> upgradeDateResult = remoteSysConfigService.getStringConfigByKey(BasicKeyConstants.UPGRADE_DATE);
        if (StringUtils.isNull(upgradeDateResult) || StringUtils.isNull(upgradeDateResult.getData())) {
            throw new BizException(BasicCodeEnum.UPGRADE_DATE_NO_CONFIG);
        }
        String upgradeDateStr = upgradeDateResult.getData();
        Date upgradeDate = DateUtil.parse(DateUtil.getSysYear().concat("-").concat(upgradeDateStr), "yyyy-MM-dd");
        if (upgradeDate == null || upgradeDate.getTime() > System.currentTimeMillis()) {
            throw new BizException(BasicCodeEnum.UPGRADE_DATE_LIMIT, DateUtil.format(upgradeDate, "yyyy-MM-dd"));
        }

        List<Integer> gradeList = dictDataResult.getData().stream().map(SysDictData::getItemVal).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        //验证是否还有班级需要先毕业
        int count = classMapper.countByGrade(SecurityUtils.getOrgId(), gradeList);
        if (count > 0) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "请先进行毕业操作");
        }

        //升学限制操作次数
        Boolean result = redisService.getCacheObject(RedisKeyConstants.UPGRADE);
        if (result != null && result) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "操作太频繁");
        }
        //获取配置的升学操作限制时间
        R<Integer> upgradeResult = remoteSysConfigService.getIntConfigByKey(BasicKeyConstants.UPGRADE_COUNT_LIMIT);
        if (StringUtils.isNull(upgradeResult) || StringUtils.isNull(upgradeResult.getData())) {
            throw new BizException(BasicCodeEnum.UPGRADE_NO_CONFIG);
        }

        //毕业之后才能进行升学操作
        classMapper.upgrade(SecurityUtils.getOrgId(), gradeList);

        //升学记录
        operLogService.add(SecurityUtils.getUserId().toString(), SecurityUtils.getUsername(),
                OperTypeEnum.UPGRADE.getCode(), OperUserTypeEnum.BACKEND.getCode());

        //设置升学操作锁定
        redisService.setCacheObject(RedisKeyConstants.UPGRADE, true, upgradeResult.getData(), TimeUnit.MINUTES);
    }

    /**
     * 毕业
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void graduate() {
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode("graduate_grade");
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADE_NO_CONFIG);
        }
        //获取配置的升学操作限制时间
        R<String> graduateDateResult = remoteSysConfigService.getStringConfigByKey(BasicKeyConstants.GRADUATE_DATE);
        if (StringUtils.isNull(graduateDateResult) || StringUtils.isNull(graduateDateResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADUATE_DATE_NO_CONFIG);
        }
        String graduateDateStr = graduateDateResult.getData();
        Date graduateDate = DateUtil.parse(DateUtil.getSysYear().concat("-").concat(graduateDateStr), "yyyy-MM-dd");
        if (graduateDate == null || graduateDate.getTime() > System.currentTimeMillis()) {
            throw new BizException(BasicCodeEnum.GRADUATE_DATE_LIMIT, DateUtil.format(graduateDate, "yyyy-MM-dd"));
        }

        String orgId = SecurityUtils.getOrgId();
        String orgName = SecurityUtils.getOrgName();
        List<Integer> gradeList = dictDataResult.getData().stream().map(SysDictData::getItemVal).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        // 根据学校id和年级查询需要毕业的班级id信息，需要获取到班级id来进行接触班主任信息
        Map<String, Object> map = new HashMap<>(2);
        map.put("gradeList", gradeList);
        map.put("orgId", orgId);
        List<ScClass> classList = classMapper.selectClassByGrade(map);
        if (CollectionUtils.isEmpty(classList)) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "没有毕业班级");
        }
        List<String> classIdList = classList.stream().map(ScClass::getClassId).collect(Collectors.toList());

        // 设置学生毕业
        scStudentService.graduate(orgId, orgName, gradeList, classIdList);

        // 毕业班的班主任关系解除
        teacClassService.updateClassTeacNullByClassId(classIdList);

        // 班级设置已删除
        classMapper.graduate(orgId, gradeList);
    }

    /**
     * 获取毕业信息
     *
     * @return
     */
    @Override
    public GraduateInfoResponse selectGraduateInfo() {
        R<List<SysDictData>> dictDataResult = remoteDictDataService.getDictDataByCode("graduate_grade");
        if (StringUtils.isNull(dictDataResult) || StringUtils.isNull(dictDataResult.getData())) {
            throw new BizException(BasicCodeEnum.GRADE_NO_CONFIG);
        }
        String orgId = SecurityUtils.getOrgId();
        List<Integer> gradeList = dictDataResult.getData().stream().map(SysDictData::getItemVal).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        Integer graduateCount = scStudentService.countGraduate(orgId, gradeList);

        GraduateInfoResponse response = new GraduateInfoResponse();
        response.setYear(DateUtil.getSysYear());
        response.setGraduateCount(graduateCount);
        return response;
    }

    /**
     * 实现保存功能
     *
     * @param scClass  班级信息
     * @param grade    班别
     * @param classNum 班级名称
     */
    private void doSave(ScClass scClass, Integer grade, String classNum) {
        //登录者所属学校id
        String orgId = SecurityUtils.getOrgId();

        if (grade == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "年级");
        }
        if (StringUtils.isEmpty(classNum)) {
            throw new BizException(CodeEnum.NOT_EMPTY, "班别");
        }

        String classId = UUIDG.generate();
        scClass.setClassNum(classNum);
        scClass.setGrade(grade);
        scClass.setOrgId(orgId);
        scClass.setModifyDate(new Date());

        //判断是否存在班主任
        if (StringUtils.isNotEmpty(scClass.getTeachId())) {
            ClassTeacherVo classTeacherVoTeach = teacClassService.selectByTeacId(scClass.getTeachId());
            if (classTeacherVoTeach != null) {
                if (classTeacherVoTeach.getGrade() != scClass.getGrade() || !classTeacherVoTeach.getClassNum().equals(scClass.getClassNum())) {
                    throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该老师已经是其他班级的班主任");
                }
            }
        }

        if (StringUtils.isEmpty(scClass.getClassId())) {
            this.add(scClass, orgId, grade, classNum, classId);
        } else {
            ScClass dbScClass = this.update(scClass);
            classId = dbScClass.getClassId();
        }

        //新增班级和老师的关系,如果没有老师id，则也需要给班级和老师关系表里添加一笔数据，为了兼容老的后台
        teacClassService.add(classId, scClass.getTeachId(), SysEnableEnum.YES.getValue(), null, true);

        //修改老师移动账号状态为正常状态
        if (StringUtils.isNotEmpty(scClass.getTeachId())) {
            ScTeacher teacher = teacherService.selectByTechId(scClass.getTeachId());
            if (teacher == null) {
                throw new BizException(CodeEnum.NOT_EXIST, "该老师");
            }
            moveAccService.updateStateMark(teacher.getAccId(), teacher.getMobile(), SysEnableEnum.YES.getValue());
        }
    }

    /**
     * 修改
     *
     * @param scClass 班级信息
     * @return
     */
    private ScClass update(ScClass scClass) {
        ScClass dbScClass = classMapper.queryBasicByClassId(scClass.getClassId());
        if (dbScClass == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该班级");
        }
        ScClass existScClass = this.queryByClassNum(scClass.getGrade(), scClass.getClassNum());
        if (existScClass != null && !dbScClass.getClassId().equals(existScClass.getClassId())) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该班级");
        }

        String teacCode = "";
        String classCode = "";
        if (StringUtils.isBlank(scClass.getTeacCode())) {
            //获取老师邀请码
            teacCode = this.getCode(StringConstantUtils.SSCL_TEAC_CODE);
            scClass.setTeacCode(teacCode);
        }
        if (StringUtils.isBlank(scClass.getClassCode())) {
            //获取班级邀请码
            classCode = this.getCode(StringConstantUtils.SSCL_CLASS_CODE);
            scClass.setClassCode(classCode);
        }
        classMapper.modify(scClass);
        return dbScClass;
    }

    /**
     * 新增
     *
     * @param scClass  班级信息
     * @param orgId    学校id
     * @param grade    年级
     * @param classNum 班级
     * @param classId  班级id
     */
    private void add(ScClass scClass, String orgId, Integer grade, String classNum, String classId) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("grade", grade);
        map.put("classNum", classNum);
        map.put("orgId", orgId);
        ScClass dbScClass = classMapper.queryExistClass(map);
        if (dbScClass != null) {
            throw new BizException(CodeEnum.ALREADY_EXIST, "该班级");
        }

        //判断是否存在班主任
        if (StringUtils.isNotEmpty(scClass.getTeachId())) {
            ClassTeacherVo classTeacherVo = teacClassService.selectByGradeAndClassNum(grade, classNum);
            if (classTeacherVo != null) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该班级已经存在班主任");
            }
        }

        scClass.setStateMark(Status.normal.value());
        scClass.setClassId(classId);
        //新增时候默认学生为0
        scClass.setStuCount(0L);
        scClass.setCreateDate(new Date());
        //获取老师邀请码
        String teacCode = this.getCode(StringConstantUtils.SSCL_TEAC_CODE);
        //获取班级邀请码
        String classCode = this.getCode(StringConstantUtils.SSCL_CLASS_CODE);
        scClass.setTeacCode(teacCode);
        scClass.setClassCode(classCode);
        //第几届学生
        scClass.setStage(DateUtils.getSysYear());
        classMapper.insert(scClass);
    }

    /**
     * 保存导入数据
     *
     * @param scClass
     * @param grade
     */
    private void doSaveImport(ScClass scClass, Integer grade) {
        //登录者所属学校id
        String orgId = SecurityUtils.getOrgId();

        if (grade == null) {
            throw new BizException(CodeEnum.NOT_EMPTY, "年级");
        }
        if (StringUtils.isEmpty(scClass.getClassNum())) {
            throw new BizException(CodeEnum.NOT_EMPTY, "班别");
        }

        HashMap<String, Object> map = new HashMap<>(3);
        map.put("grade", grade);
        map.put("classNum", scClass.getClassNum());
        map.put("orgId", orgId);
        ScClass dbScClass = classMapper.queryExistClass(map);
        String classId = UUIDG.generate();
        if (dbScClass == null) {
            scClass.setStateMark(Status.normal.value());
            scClass.setClassId(classId);
            //新增时候默认学生为0
            scClass.setStuCount(0L);
            scClass.setCreateDate(new Date());
            //获取老师邀请码
            String teacCode = this.getCode(StringConstantUtils.SSCL_TEAC_CODE);
            //获取班级邀请码
            String classCode = this.getCode(StringConstantUtils.SSCL_CLASS_CODE);
            scClass.setTeacCode(teacCode);
            scClass.setClassCode(classCode);
            scClass.setClassNum(scClass.getClassNum());
            scClass.setGrade(grade);
            scClass.setOrgId(orgId);
            //第几届学生
            scClass.setStage(DateUtils.getSysYear());
            classMapper.insert(scClass);
        } else {
            String teacCode = "";
            String classCode = "";
            classId = dbScClass.getClassId();
            if (StringUtils.isBlank(dbScClass.getTeacCode())) {
                //获取老师邀请码
                teacCode = this.getCode(StringConstantUtils.SSCL_TEAC_CODE);
                dbScClass.setTeacCode(teacCode);
            }
            if (StringUtils.isBlank(dbScClass.getClassCode())) {
                //获取班级邀请码
                classCode = this.getCode(StringConstantUtils.SSCL_CLASS_CODE);
                dbScClass.setClassCode(classCode);
            }
            dbScClass.setModifyDate(new Date());
            classMapper.modify(dbScClass);
        }

        //新增班级和老师的关系,如果没有老师id，则也需要给班级和老师关系表里添加一笔数据，为了兼容老的后台
        teacClassService.add(classId, scClass.getTeachId(), SysEnableEnum.YES.getValue(), null, true);
    }

    /**
     * 获取邀请码
     *
     * @return
     */
    private String getCode(String codeFormat) {
        String code = "";
        while (true) {
            code = RandCodeUtil.randNumberCodeByCustom("1", 6);
            String value = redisService.getString(String.format(codeFormat, code));
            if (org.apache.commons.lang.StringUtils.isBlank(value)) {
                System.out.println(code);
                break;
            }
        }
        return code;
    }
}
