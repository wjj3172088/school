package com.qh.basic.service;

import com.qh.basic.domain.ScMoveAccOther;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 移动用户扩展信息Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
public interface IScMoveAccOtherService extends IService<ScMoveAccOther> {
    /**
     * 保存移动端账号扩展信息
     *
     * @param moveAccOther 移动端账号扩展信息
     */
    void saveMoveAccOther(ScMoveAccOther moveAccOther);
}
