package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.StaffHealthVo;
import com.qh.basic.domain.vo.PushMoveTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.basic.model.request.teacher.TeacherImportRequest;
import com.qh.basic.api.model.request.teacher.TeacherSaveRequest;

import java.util.List;
import java.util.Map;

/**
 * 教师信息Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
public interface IScTeacherService extends IService<ScTeacher> {


    /**
     * 查询教师信息集合
     *
     * @param page      分页信息
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    IPage<Map> selectScTeacherListByPage(IPage<ScTeacher> page, ScTeacher scTeacher);

    /**
     * 查询教职工健康状态信息集合
     *
     * @param page      分页信息
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    IPage<StaffHealthVo> selectHealthListByPage(IPage<ScTeacher> page, ScTeacher scTeacher);


    /**
     * 健康码导出
     *
     * @param scTeacher 操作教师信息对象
     * @return 操作教师信息集合
     */
    List<StaffHealthVo> findHealthExcel(ScTeacher scTeacher);

    /**
     * 导出
     *
     * @param request
     * @return
     */
    List<TeacherExportVo> findAllExcelData(TeacherExportSearchRequest request);

    /**
     * 保存教师信息
     *
     * @param request 教师信息
     */
    void saveTeacher(TeacherSaveRequest request);

    /**
     * 根据教师id批量删除
     *
     * @param idList 班级id
     */
    void batchDeleteById(List<String> idList);

    /**
     * 根据教师id查询
     *
     * @param teacId 教师id
     * @return
     */
    Map queryByTeacId(String teacId);

    /**
     * 根据老师id查找
     *
     * @param techId 老师id
     * @return
     */
    ScTeacher selectByTechId(String techId);

    /**
     * 通过classId查询班主任的信息
     *
     * @param classIdList 班级id
     * @return
     */
    List<ScTeacher> queryHeadmasterByClassId(List<String> classIdList);

    /**
     * 导入
     *
     * @param teacherList 教师信息集合
     * @return
     */
    void importData(List<TeacherImportRequest> teacherList);

    /**
     * 根据姓名或手机号查询
     *
     * @param type      教师/职工
     * @param subType   子类型
     * @param paraValue 姓名或手机号
     * @return
     */
    List<Map> selectList(String type, String subType, String paraValue);


    /**
     * 获取学校总体教职工通讯录
     * @param paraValue 姓名或手机号
     * @return
     */
    List<Map> mailAllList(String paraValue);

    /**
     * 需要推送的老师
     *
     * @param orgId  学校id
     * @param teacId 老师id
     * @return
     */
    List<PushMoveTeacher> findMovePushTeacher(String orgId, String teacId);

    /**
     * 根据教师姓名查询
     *
     * @param orgId    学校id
     * @param teacName 老师姓名
     * @param jobNumber 工号
     * @return
     */
    ScTeacher selectByTeacName(String orgId, String teacName, String jobNumber);

    /**
     * 根据手机号查看是否存在
     *
     * @param mobile 手机号
     * @return
     */
    boolean exsitByMobile(String mobile);


    /**
     * 获取所有教师信息
     *
     * @param request
     * @return
     */
    List<TeacherExportVo> findAllData(TeacherExportSearchRequest request);

    /**
     * 同步教师健康码状态
     * @param teacId
     * @param healthState
     * @return
     */
    Integer syncTeacherHealthState(String teacId,int healthState);
}
