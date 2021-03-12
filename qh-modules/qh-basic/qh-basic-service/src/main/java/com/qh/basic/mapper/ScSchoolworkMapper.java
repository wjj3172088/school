package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScSchoolwork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 作业管理Mapper接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface ScSchoolworkMapper extends BaseMapper<ScSchoolwork> {

    /**
     * 根据条件分页查询列表
     *
     * @param page         分页信息
     * @param scSchoolwork 安全宣传传入信息
     * @return 安全宣传信息集合信息
     */
    IPage<ScSchoolwork> selectListByPage(IPage<ScSchoolwork> page, @Param("scSchoolwork") ScSchoolwork scSchoolwork);
}
