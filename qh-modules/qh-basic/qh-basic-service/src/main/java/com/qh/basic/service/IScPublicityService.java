package com.qh.basic.service;

import com.qh.basic.domain.ScPublicity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 安全宣传Service接口
 *
 * @author 黄道权
 * @date 2020-11-17
 */
public interface IScPublicityService extends IService<ScPublicity> {


    /**
     * 查询安全宣传集合
     *
     * @param page        分页信息
     * @param scPublicity 操作安全宣传对象
     * @return 操作安全宣传集合
     */
    IPage<ScPublicity> selectScPublicityListByPage(IPage<ScPublicity> page, ScPublicity scPublicity);

}
