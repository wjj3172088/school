package com.qh.basic.api.factory;

import com.qh.basic.api.TeacherService;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 黄道权
 * @Date: 2020/12/28 15:45
 * @Description:
 */
@Component
public class RemoteTeacherFallbackFactory implements FallbackFactory<TeacherService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteTeacherFallbackFactory.class);

    @Override
    public TeacherService create(Throwable throwable) {
        log.error("教师服务调用失败:{}", throwable.getMessage());
        return new TeacherService() {
            @Override
            public ScTeacher selectByTeacName(String orgId, String teacName, String jobNumber) {
                return null;
            }

            @Override
            public List<TeacherExportVo> findAllData(TeacherExportSearchRequest request) {
                return null;
            }

            @Override
            public Integer syncTeacherHealthState( String teacId, int healthState) {
                return 0;
            }
        };
    }
}
