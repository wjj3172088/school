package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScClass;
import com.qh.basic.model.request.scclass.ClassSearchRequest;
import com.qh.basic.domain.vo.ClassExportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
public interface ScClassMapper extends BaseMapper<ScClass> {
    /**
     * 根据条件分页查询列表
     *
     * @param page    分页信息
     * @param request 班级信息
     * @return 班级信息集合信息
     */
    IPage<ScClass> selectListByPage(IPage<ScClass> page, @Param("scClass") ClassSearchRequest request);

    /**
     * 查询需要导出的数据
     *
     * @param param
     * @return
     */
    List<ClassExportVo> findAllByExport(Map<String, Object> param);

    /**
     * 查询需要导出的数据
     *
     * @param orgId 所属学校
     * @return
     */
    List<ScClass> findAllByOrgId(String orgId);

    /**
     * 根据条件查询班级信息
     *
     * @param map 条件
     * @return
     */
    ScClass queryExistClass(Map<String, Object> map);

    /**
     * 根据条件查询班级信息
     *
     * @param map 条件
     * @return
     */
    List<ScClass> selectClassByGrade(Map<String, Object> map);

    /**
     * 修改
     *
     * @param scClass 班级信息
     */
    void modify(ScClass scClass);

    /**
     * 根据id批量删除
     *
     * @param ids
     * @return
     */
    int batchDelClass(List<String> ids);

    /**
     * 根据班级id查询
     *
     * @param classId 班级id
     * @return
     */
    Map queryByClassId(String classId);

    /**
     * 根据班级id查询
     *
     * @param classId 班级id
     * @return
     */
    ScClass queryBasicByClassId(String classId);

    /**
     * 查询需毕业的班级数量
     *
     * @param orgId     学校id
     * @param gradeList 需要毕业的年级
     * @return
     */
    int countByGrade(@Param("orgId") String orgId, @Param("gradeList") List<Integer> gradeList);

    /**
     * 升学
     *
     * @param orgId     所属学校id
     * @param gradeList 需要毕业的年级
     */
    void upgrade(@Param("orgId") String orgId, @Param("gradeList") List<Integer> gradeList);

    /**
     * 毕业
     *
     * @param orgId     所属学校id
     * @param gradeList 需要毕业的年级
     */
    void graduate(@Param("orgId") String orgId, @Param("gradeList") List<Integer> gradeList);

    /**
     * 根据班级id查询可用的班级信息
     *
     * @param classId 班级id
     * @return
     */
    ScClass queryEnableExtentByClassId(String classId);
}
