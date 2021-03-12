package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScTeacClass;
import com.qh.basic.domain.vo.ClassTeacherVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级教师关联Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
public interface ScTeacClassMapper extends BaseMapper<ScTeacClass> {
    /**
     * 根据班级id删除班级老师关系
     *
     * @param classId 班级
     * @return
     */
    int delClassTeachByClassId(@Param("classId") String classId);

    /**
     * 根据老师id删除班级老师关系
     *
     * @param teacId 老师id
     * @param direct 是否班主任
     * @return
     */
    int delClassTeachByTeachId(@Param("teacId") String teacId, @Param("direct") String direct);

    /**
     * 删除班级和老师的关系
     *
     * @param classIdList 班级id
     * @return
     */
    int batchDeleteByClassId(List<String> classIdList);

    /**
     * 通过teacId删除班级和老师的关系
     *
     * @param idList 老师id
     * @return
     */
    int batchDelClassTeachByTeachId(List<String> idList);

    /**
     * 解绑班主任(通过班级id)
     *
     * @param classIdList 班级id集合
     * @return
     */
    int updateClassTeacNullByClassId(@Param("classIdList") List<String> classIdList);

    /**
     * 根据班级id查询信息
     *
     * @param orgId   学校id
     * @param classId 班级id
     * @return
     */
    ClassTeacherVo selectByClassId(@Param("orgId") String orgId, @Param("classId") String classId);


    /**
     * 根据班级id查询信息
     *
     * @param orgId    学校id
     * @param grade    年级
     * @param classNum 班级
     * @return
     */
    ClassTeacherVo selectByGradeAndClassNum(@Param("orgId") String orgId, @Param("grade") int grade,
                                            @Param("classNum") String classNum);

    /**
     * 根据老师id查询信息
     *
     * @param orgId  学校id
     * @param teacId 老师id
     * @return
     */
    ClassTeacherVo selectByTeacId(@Param("orgId") String orgId, @Param("teacId") String teacId);
}
