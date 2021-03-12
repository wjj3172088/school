package com.qh.basic.mapper;

import com.qh.basic.domain.ScMoveAccOther;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 移动用户扩展信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface ScMoveAccOtherMapper extends BaseMapper<ScMoveAccOther> {
    /**
     * 根据账号查询
     *
     * @param accId 帐户Id
     * @return
     */
    Long countByAccId(String accId);

    /**
     * 修改
     *
     * @param moveAccOther 移动用户扩展信息
     */
    void modify(ScMoveAccOther moveAccOther);
}
