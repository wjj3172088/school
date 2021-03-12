package com.qh.basic.api;

import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.factory.RemoteTeacherFallbackFactory;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: 黄道权
 * @Date: 2020/11/17 21:44
 * @Description:
 */
@FeignClient(contextId = "staffService", value = ServiceNameConstants.BASIC_SERVICE, fallbackFactory = RemoteTeacherFallbackFactory.class)
public interface StaffService {

    /**
     * 根据OrgId staffName获取职工信息
     *
     * @param orgId    学校id
     * @param staffName 职工姓名
     * @param jobNumber 工号
     * @return
     */
    @GetMapping(value = "/staffInfo/selectByStaffName")
    ScStaffInfo selectByStaffName(@RequestParam("orgId") String orgId, @RequestParam("staffName") String staffName, @RequestParam("jobNumber") String jobNumber);

    /**
     * 根据条件获取所有在职职工信息列表
     * @param request
     * @return
     */
    @GetMapping(value = "/staffInfo/findAllData")
    List<Map> findAllData(@RequestParam("request") StaffInfoSearchRequest request);

    /**
     * 同步职工健康码状态
     * @param staffId
     * @param healthState
     * @return
     */
    @GetMapping(value = "/staffInfo/syncStaffHealthState")
    Integer syncStaffHealthState(@RequestParam("staffId") String staffId,@RequestParam("healthState")  int healthState);
}
