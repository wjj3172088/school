package com.qh.basic.service;

import com.qh.basic.domain.ScExaminationMain;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 考试成绩Service接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface IScExaminationMainService extends IService<ScExaminationMain> {


    /**
     * 查询考试成绩集合
     *
     * @param page              分页信息
     * @param scExaminationMain 操作考试成绩对象
     * @return 操作考试成绩集合
     */
    IPage<ScExaminationMain> selectScExaminationMainListByPage(IPage<ScExaminationMain> page, ScExaminationMain scExaminationMain);

}
