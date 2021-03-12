package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScMoveAcc;
import com.qh.basic.domain.ScMoveAccOther;
import com.qh.basic.enums.AccTypeEnum;
import com.qh.basic.enums.Status;
import com.qh.basic.enums.SysEnableEnum;
import com.qh.basic.mapper.ScMoveAccMapper;
import com.qh.basic.service.IScMoveAccOtherService;
import com.qh.basic.service.IScMoveAccService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.MD5Utils;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.UUIDG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 移动账号表Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-20
 */
@Service
public class ScMoveAccServiceImpl extends ServiceImpl<ScMoveAccMapper, ScMoveAcc> implements IScMoveAccService {
    @Autowired
    private ScMoveAccMapper moveAccMapper;
    @Autowired
    private IScMoveAccOtherService moveAccOtherService;

    /**
     * 修改移动账号的状态
     *
     * @param accId
     * @param mobile
     * @param stateMark
     */
    @Override
    public void updateStateMark(String accId, String mobile, String stateMark) {
        //判断当前手机号是否存在
        ScMoveAcc dbMoveAcc = moveAccMapper.selectByAccNum(mobile);
        if (dbMoveAcc == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该手机号");
        }
        dbMoveAcc.setStateMark(stateMark);
        dbMoveAcc.setAccId(accId);
        moveAccMapper.modifyAccId(dbMoveAcc);
    }

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
    @Override
    public String saveMoveAcc(String accId, String trueName, String mobile, String accType, String orgId,
                              ScMoveAccOther moveAccOther, String direct) {
        String status = direct.equals(SysEnableEnum.YES.getValue()) ? Status.normal.value() : Status.init.value();
        //判断当前手机号是否存在
        ScMoveAcc dbMoveAcc = moveAccMapper.selectByAccNum(mobile);
        if (StringUtils.isEmpty(accId)) {
            if (dbMoveAcc != null) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该手机号已存在");
            }
        } else {
            if (dbMoveAcc != null && !dbMoveAcc.getAccId().equals(accId)) {
                throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "该手机号已存在");
            }
            dbMoveAcc = moveAccMapper.selectByAccId(accId);
        }
        if (dbMoveAcc == null) {
            accId = UUIDG.generate();
            String pwd = MD5Utils.encryptPassword("123456");

            //保存账号相关信息
            ScMoveAcc moveAcc = new ScMoveAcc();
            moveAcc.setAccPwd(pwd);
            moveAcc.setAccId(accId);
            moveAcc.setAccNum(mobile);
            moveAcc.setNickname(mobile);
            moveAcc.setTrueName(trueName);
            moveAcc.setAlias(mobile);
            // 声音
            moveAcc.setVoice("on");
            // 振动关
            moveAcc.setVibrate("on");
            moveAcc.setAccType(accType);
            moveAcc.setStateMark(status);
            moveAcc.setOrgId(orgId);
            moveAcc.setCreateDate(new Date());
            moveAccMapper.insert(moveAcc);
        } else {
            accId = dbMoveAcc.getAccId();
            dbMoveAcc.setAccId(accId);
            dbMoveAcc.setAccNum(mobile);
            dbMoveAcc.setTrueName(trueName);
            dbMoveAcc.setStateMark(status);
            moveAccMapper.modifyAccId(dbMoveAcc);
        }

        //保存移动端账号扩展信息
        moveAccOther.setAccId(accId);
        moveAccOtherService.saveMoveAccOther(moveAccOther);
        return accId;
    }

    /**
     * 新增家长账户
     *
     * @param orgId  学校id
     * @param mobile 手机号
     * @return
     */
    @Override
    public String saveMoveAcc(String orgId, String mobile) {
        String accId;
        ScMoveAcc dbMoveAcc = moveAccMapper.selectByAccNum(mobile);
        if (dbMoveAcc == null) {
            accId = UUIDG.generate();
            //保存账号相关信息
            ScMoveAcc moveAcc = new ScMoveAcc();
            moveAcc.setAccPwd(null);
            moveAcc.setAccId(accId);
            moveAcc.setAccNum(mobile);
            moveAcc.setNickname(mobile);
            moveAcc.setAlias(mobile);
            // 声音
            moveAcc.setVoice("on");
            // 振动关
            moveAcc.setVibrate("on");
            moveAcc.setAccType(AccTypeEnum.PARENT.getCode());
            moveAcc.setStateMark(Status.normal.value());
            moveAcc.setOrgId(orgId);
            moveAcc.setCreateDate(new Date());
            moveAccMapper.insert(moveAcc);
        } else {
            accId = dbMoveAcc.getAccId();
        }
        return accId;
    }

    /**
     * 批量删除
     *
     * @param accIdList 帐户Id集合
     */
    @Override
    public void batchDeleteById(List<String> accIdList) {
        moveAccMapper.batchDelByAccId(accIdList);
    }

    /**
     * 根据accid查询
     *
     * @param accId 移动用户id
     * @return
     */
    @Override
    public ScMoveAcc selectByAccId(String accId) {
        return moveAccMapper.selectByAccId(accId);
    }
}