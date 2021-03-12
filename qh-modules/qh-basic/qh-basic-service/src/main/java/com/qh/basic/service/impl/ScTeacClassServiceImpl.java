package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScTeacClass;
import com.qh.basic.domain.vo.ClassTeacherVo;
import com.qh.basic.enums.SysEnableEnum;
import com.qh.basic.mapper.ScTeacClassMapper;
import com.qh.basic.service.IScTeacClassService;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 班级教师关联Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
@Service
public class ScTeacClassServiceImpl extends ServiceImpl<ScTeacClassMapper, ScTeacClass> implements IScTeacClassService {
    @Autowired
    private ScTeacClassMapper teacClassMapper;

    /**
     * 新增
     *
     * @param classId       班级id
     * @param teachId       教师id
     * @param direct        是否班主任
     * @param subjectName   授课科目信息
     * @param fromTeacClass 调用方是否是TeacClass
     */
    @Override
    public void add(String classId, String teachId, String direct, String subjectName, boolean fromTeacClass) {
        //删除班级老师关系
        if (fromTeacClass) {
            teacClassMapper.delClassTeachByClassId(classId);
        } else {
            teacClassMapper.delClassTeachByTeachId(teachId, direct);
        }

        ScTeacClass classTeach = null;
        if (direct.equals(SysEnableEnum.YES.getValue())) {
            classTeach = this.selectDirectByClassId(classId);
        }

        if (classTeach == null) {
            //新增班级和班主任的关系
            classTeach = new ScTeacClass();
            String tcId = UUIDG.generate();
            classTeach.setTcId(tcId);
            classTeach.setClassId(classId);
            classTeach.setTeacId(teachId);
            classTeach.setSubjectName(subjectName);
//        if (subject != null) {
//            classTeach.setSubjectId(subject.getSubjectId());
//            classTeach.setSubjectName(subject.getSubjectName());
//        }
            //是否班主任
            classTeach.setDirect(direct);
            classTeach.setCreateDate(new Date());
            teacClassMapper.insert(classTeach);
        } else {
            classTeach.setTeacId(teachId);
            classTeach.setSubjectName(subjectName);
            classTeach.setDirect(direct);
            classTeach.setModifyDate(new Date());
            teacClassMapper.updateById(classTeach);
        }
    }

    /**
     * 批量根据班级id删除
     *
     * @param classIdList 班级id集合
     */
    @Override
    public void batchDeleteByClassId(List<String> classIdList) {
        teacClassMapper.batchDeleteByClassId(classIdList);
    }

    /**
     * 通过teacId删除班级和老师的关系
     *
     * @param idList
     */
    @Override
    public void delClassTeachByTeachId(List<String> idList) {
        teacClassMapper.batchDelClassTeachByTeachId(idList);
    }

    /**
     * 解绑班主任(通过班级id)
     *
     * @param classIdList 班级id集合
     */
    @Override
    public void updateClassTeacNullByClassId(List<String> classIdList) {
        teacClassMapper.updateClassTeacNullByClassId(classIdList);
    }

    /**
     * 根据年级和班级查询信息
     *
     * @param grade    年级
     * @param classNum 班级
     * @return
     */
    @Override
    public ClassTeacherVo selectByGradeAndClassNum(int grade, String classNum) {
        return teacClassMapper.selectByGradeAndClassNum(SecurityUtils.getOrgId(), grade, classNum);
    }

    /**
     * 根据班级id查询信息
     *
     * @param classId 班级id
     * @return
     */
    @Override
    public ClassTeacherVo selectByClassId(String classId) {
        return teacClassMapper.selectByClassId(SecurityUtils.getOrgId(), classId);
    }

    /**
     * 根据老师id查询信息
     *
     * @param teacId 老师id
     * @return
     */
    @Override
    public ClassTeacherVo selectByTeacId(String teacId) {
        return teacClassMapper.selectByTeacId(SecurityUtils.getOrgId(), teacId);
    }

    /**
     * 查询班级人和班级关系
     *
     * @param classId 班级id
     * @return
     */
    private ScTeacClass selectDirectByClassId(String classId) {
        QueryWrapper<ScTeacClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("direct", "Y");
        queryWrapper.eq("class_id", classId);
        return super.baseMapper.selectOne(queryWrapper);
    }
}