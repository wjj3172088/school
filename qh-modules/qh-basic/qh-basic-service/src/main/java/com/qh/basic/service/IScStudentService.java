package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScStudent;
import com.qh.basic.domain.vo.StudentExportVo;
import com.qh.basic.model.request.student.StudentImportRequest;
import com.qh.basic.model.request.student.StudentModifyRequest;
import com.qh.basic.model.request.student.StudentSearchRequest;

import java.util.List;
import java.util.Map;

/**
 * 学生信息Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
public interface IScStudentService extends IService<ScStudent> {


    /**
     * 查询学生信息集合
     *
     * @param page    分页信息
     * @param request 操作学生信息对象
     * @return 操作学生信息集合
     */
    IPage<Map> selectScStudentListByPage(IPage<ScStudent> page, StudentSearchRequest request);

    /**
     * 根据班级、姓名或手机号查询
     *
     * @param classId   班级
     * @param paraValue 姓名或手机号
     * @return
     */
    List<Map> selectList(String classId, String paraValue);

    /**
     * 判断是否存在
     *
     * @param classId 班级id
     * @return
     */
    boolean existByClassId(String classId);

    /**
     * 转班
     *
     * @param stuIdList 学生id集合
     * @param classId   转入的班级id
     */
    void transfer(List<String> stuIdList, String classId);

    /**
     * 根据年级查询
     *
     * @param orgId       学校id
     * @param orgName     学校名称
     * @param gradeList   年级列表
     * @param classIdList 班级id
     * @return 学生列表
     */
    void graduate(String orgId, String orgName, List<Integer> gradeList, List<String> classIdList);

    /**
     * 导入
     *
     * @param studentImportRequestList 学生导入请求
     */
    void importData(List<StudentImportRequest> studentImportRequestList);

    /**
     * 修改学生
     *
     * @param request 学生修改请求
     */
    void modify(StudentModifyRequest request);

    /**
     * 根据条件查询学生导出列表
     *
     * @param request 导出筛选条件
     * @return
     */
    List<StudentExportVo> selectExportList(StudentSearchRequest request);

    /**
     * 批量删除学生
     *
     * @param stuIdList 学生id集合
     */
    void deleteByStuIdList(List<String> stuIdList);

    /**
     * 毕业人数
     *
     * @param orgId     所属学校id
     * @param gradeList 毕业年级列表
     * @return
     */
    Integer countGraduate(String orgId, List<Integer> gradeList);
}
