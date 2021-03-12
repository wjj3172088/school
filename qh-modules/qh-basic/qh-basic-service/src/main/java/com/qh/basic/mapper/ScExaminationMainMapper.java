package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScExaminationMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 考试成绩Mapper接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface ScExaminationMainMapper extends BaseMapper<ScExaminationMain> {

    /**
     * 根据条件分页查询列表
     *
     * @param page              分页信息
     * @param scExaminationMain 考试成绩传入信息
     * @return 考试成绩信息集合信息
     */
    IPage<ScExaminationMain> selectListByPage(IPage<ScExaminationMain> page, @Param("scExaminationMain") ScExaminationMain scExaminationMain);

}
