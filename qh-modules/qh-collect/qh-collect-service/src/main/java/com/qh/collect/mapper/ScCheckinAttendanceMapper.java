package com.qh.collect.mapper;

import com.qh.collect.domain.ScCheckinAttendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 考勤总每日记录Mapper接口
 *
 * @author huangdaoquan
 * @date 2020-12-28
 */
public interface ScCheckinAttendanceMapper extends BaseMapper<ScCheckinAttendance> {

    /**
     * 获取指定条件一条考勤记录信息
     * @param recorder
     * @param orgId
     * @param attendanceDate
     * @param recorderType
     * @return
     */
    ScCheckinAttendance findOneSchoolCheckinAttendanceInMap(@Param("recorder") String recorder,@Param("orgId") String orgId,
                                                            @Param("attendanceDate") int attendanceDate, @Param("recorderType") int recorderType);
}
