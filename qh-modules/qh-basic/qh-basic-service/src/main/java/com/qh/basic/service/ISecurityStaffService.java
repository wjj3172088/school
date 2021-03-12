package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.SecurityStaff;
import com.qh.basic.domain.vo.SecurityStaffExportVo;

import java.util.List;

/**
 * 三防保安信息Service接口
 *
 * @author 汪俊杰
 * @date 2021-01-21
 */
public interface ISecurityStaffService extends IService<SecurityStaff> {
    /**
     * 查询三防保安信息集合
     *
     * @param page          分页信息
     * @param securityStaff 操作三防保安信息对象
     * @return 操作三防保安信息集合
     */
    IPage<SecurityStaff> selectSecurityStaffListByPage(IPage<SecurityStaff> page, SecurityStaff securityStaff);

    /**
     * 查询需要导出的数据
     *
     * @param securityStaff 查询条件
     * @return
     */
    List<SecurityStaffExportVo> selectExportList(SecurityStaff securityStaff);

    /**
     * 新增
     *
     * @param securityStaff 新增三防保安信息
     */
    void add(SecurityStaff securityStaff);

    /**
     * 修改
     *
     * @param securityStaff 修改三防保安信息
     */
    void modify(SecurityStaff securityStaff);

    /**
     * 导入
     *
     * @param securityStaffExportVoList 三防保安导入请求
     */
    void importData(List<SecurityStaffExportVo> securityStaffExportVoList);
}
