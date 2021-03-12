package com.qh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.system.domain.SysOrg;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/12 15:29
 * @Description: 学校
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    List<SysOrg> selectOrgAll();
}
