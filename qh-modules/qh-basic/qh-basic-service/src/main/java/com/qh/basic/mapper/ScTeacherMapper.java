package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.StaffHealthVo;
import com.qh.basic.domain.vo.PushMoveTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 教师信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
public interface ScTeacherMapper extends BaseMapper<ScTeacher> {

    /**
     * 根据条件分页查询列表
     *
     * @param page      分页信息
     * @param scTeacher 教师信息
     * @return 教师信息集合信息
     */
    IPage<Map> selectListByPage(IPage<ScTeacher> page, @Param("scTeacher") ScTeacher scTeacher);

    /**
     * 查询教职工健康状态信息集合
     *
     * @param page      分页信息
     * @param scTeacher 教师信息
     * @return 教师信息集合信息
     */
    IPage<StaffHealthVo> selectHealthListByPage(IPage<ScTeacher> page, @Param("scTeacher") ScTeacher scTeacher);

    /**
     * 导出教职工健康状态信息集合
     *
     * @param scTeacher 教师信息
     * @return 教师信息集合信息
     */
    List<StaffHealthVo> selectHealthListByPage( @Param("scTeacher") ScTeacher scTeacher);


    /**
     * 查询需要导出的数据
     *
     * @param request
     * @return
     */
    List<TeacherExportVo> findAllByExport(@Param("scTeacher") TeacherExportSearchRequest request);

    /**
     * 修改
     *
     * @param scTeacher 教师信息
     */
    void modify(ScTeacher scTeacher);

    /**
     * 根据身份证号和教师编号查询信息
     *
     * @param teacher
     * @return
     */
    Long countByIdCardAndTeacId(ScTeacher teacher);

    /**
     * 根据身份证号查询不是当前教师编号的信息
     *
     * @param teacher
     * @return
     */
    Long countByIdCardAndNotTeacId(ScTeacher teacher);

    /**
     * 根据身份证查询
     *
     * @param idCard 身份证
     * @return
     */
    ScTeacher selectByIdCard(String idCard);

    /**
     * 根据教师编号查询信息
     *
     * @param teachId
     * @return
     */
    ScTeacher queryInfoByTeacId(String teachId);

    /**
     * 通过teacId查询老师信息
     *
     * @param teacherIdList 教师id集合
     * @param direct        老师类型 Y=:是，N:否
     * @return
     */
    List<ScTeacher> queryListByTeacId(@Param("teacherIdList") List<String> teacherIdList, @Param("direct") String direct);

    /**
     * 通过classId查询班主任的信息
     *
     * @param classIdList
     * @return
     */
    List<ScTeacher> queryHeadmasterByClassId(@Param("classIdList") List<String> classIdList);

    /**
     * 根据教师id删除
     *
     * @param idList 教师id
     */
    void batchDelByTeachId(List<String> idList);

    /**
     * 根据教师id查询
     *
     * @param teacId 教师id
     * @return
     */
    Map queryByTeacId(String teacId);

    /**
     * 根据条件查询列表
     *
     * @param map 教师信息
     * @return 教师信息集合信息
     */
    List<Map> selectList(@Param("map") Map<String, Object> map);

    /**
     * 根据教师姓名查询
     *
     * @param orgId    学校id
     * @param teacName 教师姓名
     * @param jobNumber 工号
     * @return
     */
    ScTeacher selectByTeacName(@Param("orgId") String orgId, @Param("teacName") String teacName, @Param("jobNumber") String jobNumber);

    /**
     * 需要推送的老师
     *
     * @param orgId  学校id
     * @param teacId 老师id
     * @return
     */
    List<PushMoveTeacher> findMovePushTeacher(@Param("orgId") String orgId, @Param("teacId") String teacId);


    /**
     * 根据手机号查询信息
     *
     * @param mobile 手机号
     * @return
     */
    Long countByMobile(String mobile);

    /**
     * 根据手机号查询不是当前教师编号的信息
     *
     * @param teacher
     * @return
     */
    Long countByMobileAndNotTeacId(ScTeacher teacher);

    /**
     * 根据学校Id查询教师最大的工号
     *
     * @param orgId 学校Id
     * @return
     */
    Long selectMaxJobNumberByOrgId(String orgId);


    /**
     * 同步教师健康码状态
     *
     * @param healthState  健康状态
     * @param teacId 老师id
     * @return
     */
    Integer syncTeacherHealthState( @Param("teacId") String teacId , @Param("healthState") int healthState);
}
