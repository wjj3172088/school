package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.SecurityStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 三防保安信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2021-01-21
 */
public interface SecurityStaffMapper extends BaseMapper<SecurityStaff> {
    /**
     * 批量新增
     *
     * @param securityStaffList
     * @return
     */
    int batchInsert(@Param("list") List<SecurityStaff> securityStaffList);

    /**
     * 批量修改
     *
     * @param securityStaffList
     * @return
     */
    int batchUpdate(@Param("list") List<SecurityStaff> securityStaffList);
}
