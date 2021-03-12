package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScCurriculum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程Mapper接口
 *
 * @author 黄道权
 * @date 2020-11-13
 */
public interface ScCurriculumMapper extends BaseMapper<ScCurriculum> {

    /**
     * 根据条件分页查询列表
     *
     * @param page         分页信息
     * @param scCurriculum 课表传入信息
     * @return 课表信息集合信息
     */
    IPage<ScCurriculum> selectListByPage(IPage<ScCurriculum> page, @Param("scCurriculum") ScCurriculum scCurriculum);

    /**
     * 根据条件分页查询列表
     *
     * @param scCurriculum 课表传入信息
     * @return 课表信息集合信息
     */
    List<ScCurriculum> selectListByPage(@Param("scCurriculum") ScCurriculum scCurriculum);

}
