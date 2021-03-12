package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.domain.vo.PushMoveStaff;
import com.qh.basic.model.request.staffinfo.StaffInfoImportRequest;
import com.qh.basic.model.request.staffinfo.StaffInfoSaveRequest;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;

import java.util.List;
import java.util.Map;

/**
 * 职工信息Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface IScStaffInfoService extends IService<ScStaffInfo> {
    /**
     * 查询职工信息集合
     *
     * @param page    分页信息
     * @param request 操作职工信息对象
     * @return 操作职工信息集合
     */
    IPage<Map> selectScStaffInfoListByPage(IPage<ScStaffInfo> page, StaffInfoSearchRequest request);

    /**
     * 保存
     *
     * @param request 职工信息
     */
    void saveStaffInfo(StaffInfoSaveRequest request);

    /**
     * 批量删除
     *
     * @param accIdList 帐户Id集合
     */
    void batchDeleteById(List<String> accIdList);

    /**
     * 导入
     *
     * @param teacherList 职工信息集合
     * @return
     */
    void importData(List<StaffInfoImportRequest> teacherList);

    /**
     * 根据职工id查询
     *
     * @param staffId 职工id
     * @return
     */
    Map queryByStaffId(String staffId);

    /**
     * 根据姓名或手机号查询
     *
     * @param subType   子类型
     * @param paraValue 姓名或手机号
     * @param stateMark 职工是否激活 Y：APP激活 ALL：职工不等于已删除就行
     * @return
     */
    List<Map> selectList(String subType, String paraValue,String stateMark);

    /**
     * 需要推送的职工
     *
     * @param orgId    学校id
     * @param staffId  职工id
     * @param jobTitle 职称
     * @return
     */
    List<PushMoveStaff> findMovePushStaff(String orgId, String staffId, String jobTitle);

    /**
     * 根据职工姓名查询
     *
     * @param orgId    学校id
     * @param staffName 职工姓名
     * @param jobNumber 职工工号
     * @return
     */
    ScStaffInfo selectByStaffName(String orgId, String staffName, String jobNumber);

    /**
     * 查询所有职工信息集合
     *
     * @param request 操作职工信息对象
     * @return 操作职工信息集合
     */
    List<Map> selectStaffInfoList(StaffInfoSearchRequest request);

    /**
     * 同步职工健康码状态
     * @param staffId
     * @param healthState
     * @return
     */
    Integer syncStaffHealthState(String staffId,int healthState );
}
