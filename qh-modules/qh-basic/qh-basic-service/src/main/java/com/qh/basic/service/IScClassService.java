package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.vo.ClassExportVo;
import com.qh.basic.model.request.scclass.ClassSearchRequest;
import com.qh.basic.model.response.graduate.GraduateInfoResponse;

import java.util.List;
import java.util.Map;

/**
 * 班级Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-17
 */
public interface IScClassService extends IService<ScClass> {
    /**
     * 根据条件分页查询班级列表
     *
     * @param page    分页信息
     * @param request 班级信息
     * @return 班级信息集合信息
     */
    IPage<ScClass> selectListByPage(IPage<ScClass> page, ClassSearchRequest request);

    /**
     * 根据学校查询
     *
     * @param request
     * @return
     */
    List<ClassExportVo> findAllByExport(ClassSearchRequest request);

    /**
     * 根据学校查询
     *
     * @return
     */
    List<ScClass> findAllByOrgId();

    /**
     * 根据学校查询
     *
     * @param orgId 学校id
     * @return
     */
    List<ScClass> findAllByOrgId(String orgId);

    /**
     * 保存
     *
     * @param scClass
     */
    void saveClass(ScClass scClass);

    /**
     * 根据班级id批量删除
     *
     * @param idList 班级id
     */
    void batchDeleteById(List<String> idList);

    /**
     * 导入班级
     *
     * @param classList 班级信息集合
     * @return
     */
    void importData(List<ScClass> classList);

    /**
     * 根据Map条件读取班级信息
     *
     * @param map
     * @return
     */
    ScClass getClassByMap(Map<String, Object> map);

    /**
     * 根据班级id查询详情
     *
     * @param classId 班级id
     * @return
     */
    Map queryByClassId(String classId);

    /**
     * 根据Map条件读取班级信息
     *
     * @param grade    年级
     * @param classNum 班级名称
     * @return
     */
    ScClass queryByClassNum(int grade, String classNum);

    /**
     * 解绑班主任
     *
     * @param classIdList 班级id集合
     */
    void unbind(List<String> classIdList);

    /**
     * 根据班级id查询
     *
     * @param classId 班级id
     * @return
     */
    ScClass queryBasicByClassId(String classId);

    /**
     * 根据班级id查询可用的班级信息
     *
     * @param classId 班级id
     * @return
     */
    ScClass queryEnableExtentByClassId(String classId);

    /**
     * 升学
     */
    void upgrade();

    /**
     * 毕业
     */
    void graduate();

    /**
     * 获取毕业信息
     *
     * @return
     */
    GraduateInfoResponse selectGraduateInfo();
}
