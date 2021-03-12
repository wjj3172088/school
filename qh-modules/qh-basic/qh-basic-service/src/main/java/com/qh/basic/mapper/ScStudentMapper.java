package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScStudent;
import com.qh.basic.domain.vo.StudentExportVo;
import com.qh.basic.domain.vo.StudentExtendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 学生信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
public interface ScStudentMapper extends BaseMapper<ScStudent> {
    /**
     * 根据条件分页查询列表
     *
     * @param page 分页信息
     * @param map  学生信息
     * @return 学生信息集合信息
     */
    IPage<Map> selectListByPage(IPage<ScStudent> page, @Param("map") Map<String, Object> map);

    /**
     * 根据条件分页查询列表
     *
     * @param page 分页信息
     * @param map  学生信息
     * @return 学生信息集合信息
     */
    IPage<StudentExtendVo> selectStudentExtendVoByPage(IPage<StudentExtendVo> page, @Param("map") Map<String, Object> map);

    /**
     * 根据条件查询列表
     *
     * @param map 学生信息
     * @return 学生信息集合信息
     */
    List<Map> selectList(@Param("map") Map<String, Object> map);

    /**
     * 根据条件查询列表
     *
     * @param map 学生信息
     * @return 学生信息集合信息
     */
    List<ScStudent> selectStudentByMap(Map<String, Object> map);

    /**
     * 根据学生id修改学生班级
     *
     * @param stuIdList 学生id
     * @param classId   班级id
     */
    void updateClassByStuId(@Param("stuIdList") List<String> stuIdList, @Param("classId") String classId);

    /**
     * 毕业
     *
     * @param orgId   所属学校id
     * @param classId 需要毕业的班级id
     */
    void graduate(@Param("orgId") String orgId, @Param("classId") String classId);

    /**
     * 根据条件查询导出信息
     *
     * @param map 学生信息
     * @return 学生信息集合信息
     */
    List<StudentExportVo> selectExportList(@Param("map") Map<String, Object> map);

    /**
     * 删除学生
     *
     * @param orgId     所属学校id
     * @param stuIdList 学生id
     */
    void delStudent(@Param("orgId") String orgId, @Param("stuIdList") List<String> stuIdList);

    /**
     * 毕业人数
     *
     * @param map 毕业条件
     * @return
     */
    Integer countGraduate(Map<String, Object> map);
}
