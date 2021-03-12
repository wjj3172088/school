package com.qh.basic.service;

import com.qh.basic.domain.ScSignIn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 到校签到Service接口
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
public interface IScSignInService extends IService<ScSignIn> {


    /**
     * 查询到校签到集合
     *
     * @param page         分页信息
     * @param scSignIn 操作到校签到对象
     * @return 操作到校签到集合
     */
    IPage<ScSignIn> selectScSignInListByPage(IPage<ScSignIn> page, ScSignIn scSignIn);

    /**
     * 查询到校签到集合
     *
     * @param scSignIn 操作到校签到对象
     * @return 操作到校签到集合
     */
    List<ScSignIn> selectScSignInList(ScSignIn scSignIn);

}
