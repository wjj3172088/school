package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScMoveAccOtherMapper;
import com.qh.basic.domain.ScMoveAccOther;
import com.qh.basic.service.IScMoveAccOtherService;

/**
 * 移动用户扩展信息Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Service
public class ScMoveAccOtherServiceImpl extends ServiceImpl<ScMoveAccOtherMapper, ScMoveAccOther> implements IScMoveAccOtherService {
    @Autowired
    private ScMoveAccOtherMapper moveAccOtherMapper;

    /**
     * 保存移动端账号扩展信息
     *
     * @param moveAccOther 移动端账号扩展信息
     */
    @Override
    public void saveMoveAccOther(ScMoveAccOther moveAccOther) {
        //判断当前账号id是否存在，如果存在则不做处理
        long count = moveAccOtherMapper.countByAccId(moveAccOther.getAccId());
        if (count > 0) {
            //修改
            moveAccOtherMapper.modify(moveAccOther);
        } else {
            //新增
            moveAccOtherMapper.insert(moveAccOther);
        }
    }
}