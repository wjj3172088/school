package com.qh.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScApply;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.ScStudent;
import com.qh.basic.domain.vo.JoinClassesBean;
import com.qh.basic.enums.SchoolApplyEnum;
import com.qh.basic.mapper.ScApplyMapper;
import com.qh.basic.service.IScApplyService;
import com.qh.basic.service.IScClassService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信息数据申请Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-24
 */
@Service
@Slf4j
public class ScApplyServiceImpl extends ServiceImpl<ScApplyMapper, ScApply> implements IScApplyService {
    @Autowired
    private ScApplyMapper applyMapper;
    @Autowired
    private IScClassService classService;

    /**
     * 查询信息数据申请集合
     *
     * @param page    分页信息
     * @param scApply 操作信息数据申请对象
     * @return 操作信息数据申请集合
     */
    @Override
    public IPage<ScApply> selectScApplyListByPage(IPage<ScApply> page, ScApply scApply) {
        return this.page(page, getQuery(scApply));
    }

    /**
     * 新增申请
     *
     * @param targetClassId 转班班级id
     * @param studentList   学生列表
     */
    @Override
    public void add(String targetClassId, List<ScStudent> studentList) {
        ScClass targetClass = classService.queryEnableExtentByClassId(targetClassId);
        if (targetClass == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "转班班级");
        }
        List<ScApply> applyList = new ArrayList<>();
        //用于暂存需要转班的学生的班级信息，在循环中，如果已经做过查询，则从暂存map中取，不用重复去数据库拿，提高性能
        Map<String, ScClass> classMap = new HashMap<>(1);
        for (ScStudent student : studentList) {
            String oldClassId = student.getClassId();
            String oldClassName;
            if (oldClassId.equals(targetClassId)) {
                log.info("新增申请：调班的班级一致：" + targetClassId);
                continue;
            }

            //如果map中有，则从map中取，不然就从数据库中，这里是防止同个classId多次从数据库去请求的情况
            if (classMap.containsKey(oldClassId)) {
                ScClass oldClass = classMap.get(oldClassId);
                oldClassName = oldClass.getClassName();
            } else {
                ScClass oldClass = classService.queryEnableExtentByClassId(oldClassId);
                if (oldClass == null) {
                    throw new BizException(CodeEnum.NOT_EXIST, "原班级");
                }
                oldClassName = oldClass.getClassName();
                classMap.put(oldClassId, oldClass);
            }

            ScApply apply = new ScApply();
            apply.setApplyAppellation(StringUtils.join(student.getStuName(), "-", student.getGuardianMobile()));
            apply.setApplyState(SchoolApplyEnum.StateEnum.agree.getCode());
            apply.setApplyTarget(SchoolApplyEnum.TargetEnum.manage.getCode());
            apply.setApplyTargetValue(targetClassId);
            apply.setApplyTitle(SchoolApplyEnum.TypeEnum.CHANGE_CLASS.getMsg());
            apply.setApplyTag(SchoolApplyEnum.TagEnum.JOIN.getCode());
            apply.setApplyType(SchoolApplyEnum.TypeEnum.CHANGE_CLASS.getCode());
            apply.setApplyUserId(SecurityUtils.getUserId().toString());
            apply.setApplyName(student.getStuName());

            //拼接json
            JoinClassesBean joinClasses = JoinClassesBean.builder().
                    oldClassId(oldClassId).
                    studentName(student.getStuName()).
                    studentId(student.getStuId()).
                    targetClassId(targetClassId).
                    idCard(student.getIdCard()).
                    oldClassName(oldClassName).
                    targetClassName(targetClass.getClassName()).build();
            apply.setApplyValue(JSON.toJSONString(joinClasses));
            apply.setCreateTime(DateUtil.getSystemSeconds());
            apply.setModifyTime(DateUtil.getSystemSeconds());
            apply.setActionTime(DateUtil.getSystemSeconds());
            apply.setOrgId(SecurityUtils.getOrgId());
            applyList.add(apply);
        }
        if (!CollectionUtils.isEmpty(applyList)) {
            applyMapper.batchInsert(applyList);
        }
    }

    /**
     * 查询信息数据申请参数拼接
     */
    private QueryWrapper<ScApply> getQuery(ScApply scApply) {
        QueryWrapper<ScApply> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(scApply.getApplyName()), "apply_name", scApply.getApplyName());
        queryWrapper.eq(StringUtils.isNotBlank(scApply.getApplyTitle()), "apply_title", scApply.getApplyTitle());
        queryWrapper.eq(scApply.getApplyTag() != null, "apply_tag", scApply.getApplyTag());
        queryWrapper.eq(scApply.getApplyType() != null, "apply_type", scApply.getApplyType());
        queryWrapper.eq(scApply.getApplyState() != null, "apply_state", scApply.getApplyState());
        queryWrapper.eq(scApply.getApplyTarget() != null, "apply_target", scApply.getApplyTarget());
        queryWrapper.eq(StringUtils.isNotBlank(scApply.getApplyTargetValue()), "apply_target_value", scApply.getApplyTargetValue());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }
}