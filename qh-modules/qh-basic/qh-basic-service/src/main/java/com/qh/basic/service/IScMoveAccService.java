package com.qh.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScMoveAcc;
import com.qh.basic.domain.ScMoveAccOther;

import java.util.List;

/**
 * 移动账号表Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface IScMoveAccService extends IService<ScMoveAcc> {
    /**
     * 修改移动账号的状态
     *
     * @param accId
     * @param mobile
     * @param stateMark
     */
    void updateStateMark(String accId, String mobile, String stateMark);

    /**
     * 保存移动端账号
     *
     * @param accId        移动用户id
     * @param trueName     姓名
     * @param mobile       手机号
     * @param accType      用户类型
     * @param orgId        所属学校
     * @param moveAccOther 移动账号扩展信息
     * @param direct       是否班主任
     * @return
     */
    String saveMoveAcc(String accId, String trueName, String mobile, String accType, String orgId,
                       ScMoveAccOther moveAccOther, String direct);

    /**
     * 新增家长账户
     *
     * @param orgId  学校id
     * @param mobile 手机号
     * @return
     */
    String saveMoveAcc(String orgId, String mobile);

    /**
     * 批量删除
     *
     * @param accIdList 帐户Id集合
     */
    void batchDeleteById(List<String> accIdList);

    /**
     * 根据accid查询
     *
     * @param accId 移动用户id
     * @return
     */
    ScMoveAcc selectByAccId(String accId);
}
