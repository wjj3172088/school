package com.qh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.system.domain.SysOrg;
import com.qh.system.domain.vo.TreeSelect;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/12 15:27
 * @Description:学校
 */
public interface ISysOrgService extends IService<SysOrg> {
    /**
     * 获取省市地区和学校级联信息
     *
     * @return
     */
    List<TreeSelect> selectTree();

    /**
     * 根据学校id查询学校名称
     *
     * @param orgId 学校id
     * @return 学校名称
     */
    String selectNameById(String orgId);
}
