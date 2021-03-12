package com.qh.basic.api;

import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.factory.RemoteTeacherFallbackFactory;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import com.qh.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 黄道权
 * @Date: 2020/11/17 21:44
 * @Description:
 */
@FeignClient(contextId = "teacherService", value = ServiceNameConstants.BASIC_SERVICE, fallbackFactory = RemoteTeacherFallbackFactory.class)
public interface TeacherService {

    /**
     * 根据OrgId teacName获取教师信息
     *
     * @param orgId    学校id
     * @param teacName 老师名称
     * @param jobNumber 工号
     * @return
     */
    @GetMapping(value = "/teacher/selectByTeacName")
    ScTeacher selectByTeacName(@RequestParam("orgId") String orgId, @RequestParam("teacName") String teacName, @RequestParam("jobNumber") String jobNumber);

    /**
     * 根据条件获取所有在职教师信息列表
     * @param request
     * @return
     */
    @GetMapping(value = "/teacher/findAllData")
    List<TeacherExportVo> findAllData(@RequestParam("request") TeacherExportSearchRequest request);

    /**
     * 同步教师健康码状态
     * @param teacId
     * @param healthState
     * @return
     */
    @GetMapping(value = "/teacher/syncTeacherHealthState")
    Integer syncTeacherHealthState(@RequestParam("teacId") String teacId,@RequestParam("healthState")  int healthState);
}
