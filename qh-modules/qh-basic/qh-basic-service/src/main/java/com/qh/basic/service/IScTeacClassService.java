package com.qh.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScSubject;
import com.qh.basic.domain.ScTeacClass;
import com.qh.basic.domain.vo.ClassTeacherVo;

import java.util.List;

/**
 * 班级教师关联Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
public interface IScTeacClassService extends IService<ScTeacClass> {
    /**
     * 新增
     *
     * @param classId       班级id
     * @param teachId       教师id
     * @param direct        是否班主任
     * @param subjectName   授课科目信息
     * @param fromTeacClass 调用方是否是TeacClass
     */
    void add(String classId, String teachId, String direct, String subjectName, boolean fromTeacClass);

    /**
     * 批量根据班级id删除
     *
     * @param classIdList 班级id集合
     */
    void batchDeleteByClassId(List<String> classIdList);

    /**
     * 通过teacId删除班级和老师的关系
     *
     * @param idList
     */
    void delClassTeachByTeachId(List<String> idList);

    /**
     * 解绑班主任(通过班级id)
     *
     * @param classIdList 班级id集合
     */
    void updateClassTeacNullByClassId(List<String> classIdList);

    /**
     * 根据年级和班级查询信息
     *
     * @param grade    年级
     * @param classNum 班级
     * @return
     */
    ClassTeacherVo selectByGradeAndClassNum(int grade, String classNum);

    /**
     * 根据班级id查询信息
     *
     * @param classId 班级id
     * @return
     */
    ClassTeacherVo selectByClassId(String classId);

    /**
     * 根据老师id查询信息
     *
     * @param teacId 老师id
     * @return
     */
    ClassTeacherVo selectByTeacId(String teacId);
}
