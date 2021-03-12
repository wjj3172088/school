package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScParentStudent;
import com.qh.basic.domain.ScStudent;
import com.qh.basic.mapper.ScParentStudentMapper;
import com.qh.basic.service.IScParentStudentService;
import com.qh.common.core.utils.http.UUIDG;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 家长学生关系Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-31
 */
@Service
public class ScParentStudentServiceImpl extends ServiceImpl<ScParentStudentMapper, ScParentStudent> implements IScParentStudentService {
    /**
     * 保存家长和学生的关系
     *
     * @param accId      移动账号id
     * @param expireTime 过期时间
     * @param student    学生信息
     */
    @Override
    public void save(String accId, Date expireTime, ScStudent student) {
        //根据账号id获取是否有绑定默认学生
        boolean haveDefaultStudent = this.baseMapper.countDefaultByAccId(accId) > 0;

        //新增家长和学生的关系
        ScParentStudent parentStudent = new ScParentStudent();
        parentStudent.setPsId(UUIDG.generate());
        parentStudent.setStuId(student.getStuId());
        parentStudent.setAccId(accId);
        parentStudent.setDef(!haveDefaultStudent);
        parentStudent.setPrtAccType("main");
        parentStudent.setRelation(student.getGuardianRelation());
        parentStudent.setCreateDate(new Date());
        parentStudent.setModifyDate(new Date());
        parentStudent.setExpireTime(expireTime);
        this.baseMapper.insert(parentStudent);
    }

    /**
     * 删除学生和家长的关系
     *
     * @param stuIdList 学生id列表
     */
    @Override
    public void deleteByStuIdList(List<String> stuIdList) {
        this.baseMapper.deleteByStuIdList(stuIdList);
    }

    /**
     * 设置家长的默认孩子
     *
     * @param stuIdList 学生id列表
     */
    @Override
    public void setParentDefaultChild(List<String> stuIdList) {
        this.baseMapper.setParentDefaultChild(stuIdList);
    }
}