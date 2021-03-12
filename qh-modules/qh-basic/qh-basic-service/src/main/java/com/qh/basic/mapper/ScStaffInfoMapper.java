package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.domain.vo.PushMoveStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 职工信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface ScStaffInfoMapper extends BaseMapper<ScStaffInfo> {
    /**
     * 根据手机号查询
     *
     * @param mobile 手机号
     * @return
     */
    Long countByMobile(String mobile);

    /**
     * 根据手机号查询不是该身份证号的笔数
     *
     * @param mobile 手机号
     * @param idCard 身份证
     * @return
     */
    Long countByMobileNotIdCard(@Param("mobile") String mobile, @Param("idCard") String idCard);

    /**
     * 根据移动账户id查询
     *
     * @param accId 移动账户id
     * @return
     */
    ScStaffInfo selectByAccId(String accId);

    /**
     * 根据手机号查询
     *
     * @param mobile 手机号
     * @return
     */
    ScStaffInfo selectByMobile(String mobile);

    /**
     * 修改
     *
     * @param scStaffInfo 职工信息
     */
    void modify(ScStaffInfo scStaffInfo);

    /**
     * 根据条件分页查询列表
     *
     * @param page 分页信息
     * @param map  教师信息
     * @return 职工信息集合信息
     */
    IPage<Map> selectListByPage(IPage<ScStaffInfo> page, @Param("map") Map<String, Object> map);

    /**
     * 根据accId删除
     *
     * @param accIdList 帐户Id集合
     */
    void batchDelByAccId(List<String> accIdList);

    /**
     * 根据身份证查询
     *
     * @param idCard 身份证
     * @return
     */
    ScStaffInfo selectByIdCard(String idCard);

    /**
     * 根据职工id查询
     *
     * @param staffId 职工id
     * @return
     */
    Map queryByStaffId(String staffId);

    /**
     * 根据条件查询列表
     *
     * @param map 教师信息
     * @return 教师信息集合信息
     */
    List<Map> selectList(@Param("map") Map<String, Object> map);

    /**
     * 需要推送的职工
     *
     * @param orgId    学校id
     * @param staffId  职工id
     * @param jobTitle 职称
     * @return
     */
    List<PushMoveStaff> findMovePushStaff(@Param("orgId") String orgId, @Param("staffId") String staffId, @Param("jobTitle") String jobTitle);

    /**
     * 根据学校Id查询职工最大的工号
     *
     * @param orgId 学校Id
     * @return
     */
    Long selectMaxJobNumberByOrgId(String orgId);

    /**
     * 根据职工姓名和工号查询
     *
     * @param orgId    学校id
     * @param staffName 职工姓名
     * @param jobNumber 工号
     * @return
     */
    ScStaffInfo selectByStaffName(@Param("orgId") String orgId, @Param("staffName") String staffName, @Param("jobNumber") String jobNumber);

    /**
     * 查询列表
     *
     * @param map  教师信息
     * @return 职工信息集合信息
     */
    List<Map> selectListByPage( @Param("map") Map<String, Object> map);

    /**
     * 同步职工健康码状态
     *
     * @param healthState  健康状态
     * @param staffId 职工id
     * @return
     */
    Integer syncStaffHealthState( @Param("staffId") String staffId , @Param("healthState") int healthState);
}
