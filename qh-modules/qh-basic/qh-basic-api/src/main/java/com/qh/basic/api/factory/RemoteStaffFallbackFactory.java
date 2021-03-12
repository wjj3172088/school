package com.qh.basic.api.factory;

import com.qh.basic.api.StaffService;
import com.qh.basic.api.TeacherService;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.vo.TeacherExportVo;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import com.qh.basic.api.model.request.teacher.TeacherExportSearchRequest;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: huangdaoquan
 * @Date: 2020/01/27 15:45
 * @Description:
 */
@Component
public class RemoteStaffFallbackFactory implements FallbackFactory<StaffService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteStaffFallbackFactory.class);

    @Override
    public StaffService create(Throwable throwable) {
        log.error("职工服务调用失败:{}", throwable.getMessage());
        return new StaffService() {
            @Override
            public ScStaffInfo selectByStaffName(String orgId, String staffName, String jobNumber) {
                return null;
            }

            @Override
            public List<Map> findAllData(StaffInfoSearchRequest request) {
                return null;
            }

            @Override
            public Integer syncStaffHealthState( String staffId, int healthState) {
                return 0;
            }
        };
    }
}
