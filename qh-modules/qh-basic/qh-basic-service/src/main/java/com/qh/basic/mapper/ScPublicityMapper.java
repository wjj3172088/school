package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScPublicity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 安全宣传Mapper接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface ScPublicityMapper extends BaseMapper<ScPublicity> {

    /**
     * 根据条件分页查询列表
     *
     * @param page        分页信息
     * @param scPublicity 安全宣传传入信息
     * @return 安全宣传信息集合信息
     */
    IPage<ScPublicity> selectListByPage(IPage<ScPublicity> page, @Param("scPublicity") ScPublicity scPublicity);

}
