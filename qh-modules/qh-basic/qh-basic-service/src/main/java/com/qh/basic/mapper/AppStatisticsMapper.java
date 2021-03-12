package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.model.request.statistics.RegisterStatisticsReq;
import com.qh.basic.model.response.StudentStatisticsResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app统计信息
 * @author huangdaoquan
 */
public interface AppStatisticsMapper {

	/**
	 * 获取指定条件的有效学生数量
	 * @param orgId 学校Id
	 * @param classId 班级Id
	 * @param createTime 创建时间
	 * @param endTime 结束时间
	 * @return
	 */
	long getStudentStateMarkCount(@Param("orgId") String orgId, @Param("classId") String classId, @Param("createTime") String createTime, @Param("endTime")String endTime);

	/**
	 * 根据条件分页查询列表
	 * @param pageStudentStatisticsResp
	 * @param registerReq 学生传入信息
	 * @return
	 */
	IPage<StudentStatisticsResp> selectListByPage(IPage<StudentStatisticsResp> pageStudentStatisticsResp, @Param("RegisterStatisticsReq") RegisterStatisticsReq registerReq);

	/**
	 * 根据条件分页查询列表
	 *
	 * @param registerReq 学生传入信息
	 * @return 课表信息集合信息
	 */
	List<StudentStatisticsResp> selectListByPage(@Param("RegisterStatisticsReq") RegisterStatisticsReq registerReq);
}
