package com.qh.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.gen.domain.GenTable;
import com.qh.system.api.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务 数据层
 * 
 * @author 
 */
public interface GenTableMapper extends BaseMapper<GenTable>
{

    /**
     * 查询查询据库数据
     *
     * @param genTable 部门信息
     * @return 部门信息集合
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询据库列表
     * @param page 分页信息
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    IPage<GenTable> selectDbTableList(IPage<GenTable> page, @Param("genTable")  GenTable genTable);

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询表ID业务信息
     * 
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Long id);

    /**
     * 查询表名称业务信息
     * 
     * @param tableName 表名称
     * @return 业务信息
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * 新增业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    int insertGenTable(GenTable genTable);

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    int updateGenTable(GenTable genTable);

    /**
     * 批量删除业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGenTableByIds(Long[] ids);
}
