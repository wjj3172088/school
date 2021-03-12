package com.qh.basic.service;

import com.qh.basic.domain.ScSchoolwork;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 作业管理Service接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface IScSchoolworkService extends IService<ScSchoolwork> {


    /**
     * 查询作业管理集合
     *
     * @param page         分页信息
     * @param scSchoolwork 操作作业管理对象
     * @return 操作作业管理集合
     */
    IPage<ScSchoolwork> selectScSchoolworkListByPage(IPage<ScSchoolwork> page, ScSchoolwork scSchoolwork);

}
