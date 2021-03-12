package com.qh.basic.service;

import com.qh.basic.domain.ScCurriculum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程Service接口
 *
 * @author 黄道权
 * @date 2020-11-13
 */
public interface IScCurriculumService extends IService<ScCurriculum> {


    /**
     * 查询课程集合
     *
     * @param page         分页信息
     * @param scCurriculum 操作课程对象
     * @return 操作课程集合
     */
    IPage<ScCurriculum> selectScCurriculumListByPage(IPage<ScCurriculum> page, ScCurriculum scCurriculum);

}
