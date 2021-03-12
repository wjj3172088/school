package com.qh.basic.mapper;

import com.qh.basic.domain.ScMoveAcc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 移动账号表Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface ScMoveAccMapper extends BaseMapper<ScMoveAcc> {
    /**
     * 根据账号查询
     *
     * @param accNum 账号
     * @return
     */
    ScMoveAcc selectByAccNum(String accNum);

    /**
     * 根据账号id查询
     *
     * @param accId 账号id
     * @return
     */
    ScMoveAcc selectByAccId(String accId);

    /**
     * 根据教职工姓名查询
     *
     * @param trueName 教师姓名
     * @param orgId    学校id
     * @return
     */
    ScMoveAcc selectByTrueName(@Param("trueName") String trueName, @Param("orgId") String orgId);


    /**
     * 根据accId删除
     *
     * @param accIdList 帐户Id集合
     */
    void batchDelByAccId(List<String> accIdList);

    /**
     * 修改
     *
     * @param scMoveAcc 移动账户信息
     */
    void modifyAccId(ScMoveAcc scMoveAcc);
}
