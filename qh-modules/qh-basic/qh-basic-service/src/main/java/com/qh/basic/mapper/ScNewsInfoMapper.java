package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScNewsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新闻资讯Mapper接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface ScNewsInfoMapper extends BaseMapper<ScNewsInfo> {

    /**
     * 根据条件分页查询列表
     *
     * @param page       分页信息
     * @param scNewsInfo 安全宣传传入信息
     * @return 新闻资讯信息集合信息
     */
    IPage<ScNewsInfo> selectListByPage(IPage<ScNewsInfo> page, @Param("scNewsInfo") ScNewsInfo scNewsInfo);

    /**
     * 批量新增
     *
     * @param newsInfoList
     * @return
     */
    int batchInsert(@Param("list") List<ScNewsInfo> newsInfoList);

    /**
     * 批量修改
     *
     * @param newsInfoList
     * @return
     */
    int batchUpdate(@Param("list") List<ScNewsInfo> newsInfoList);

    /**
     * 批量删除
     *
     * @param newsInfoList
     * @return
     */
    int batchDelete(@Param("list") List<ScNewsInfo> newsInfoList);
}
