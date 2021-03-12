package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScExaminationMainMapper;
import com.qh.basic.domain.ScExaminationMain;
import com.qh.basic.service.IScExaminationMainService;

import java.text.ParseException;

/**
 * 考试成绩Service业务层处理
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Service
public class ScExaminationMainServiceImpl extends ServiceImpl<ScExaminationMainMapper, ScExaminationMain> implements IScExaminationMainService {

    @Autowired
    ScExaminationMainMapper scExaminationMainMapper;

    /**
     * 查询考试成绩集合
     *
     * @param scExaminationMain 操作考试成绩对象
     * @return 操作考试成绩集合
     */
    @Override
    public IPage<ScExaminationMain> selectScExaminationMainListByPage(IPage<ScExaminationMain> page, ScExaminationMain scExaminationMain) {
        try {
            //考试时间入参 时间转换为时间戳
            if(StringUtils.isNotBlank(scExaminationMain.getBeginTime()) ) {
                scExaminationMain.setBeginTime(DateUtil.dateToStamp(scExaminationMain.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(scExaminationMain.getEndTime()) ) {
                scExaminationMain.setEndTime(DateUtil.dateToStamp(scExaminationMain.getEndTime()+" 23:59:59"));
            }

            scExaminationMain.setOrgId(SecurityUtils.getOrgId());
            //根据自定义SQL语句拼装查询
            IPage<ScExaminationMain> pageExaminationMain = scExaminationMainMapper.selectListByPage(page, scExaminationMain);
            return pageExaminationMain;
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }

    }


    /**
     * 查询考试成绩参数拼接
     */
    private QueryWrapper<ScExaminationMain> getQuery(ScExaminationMain scExaminationMain) {
        QueryWrapper<ScExaminationMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(scExaminationMain.getOrgId()), "org_id", scExaminationMain.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scExaminationMain.getClassId()), "class_id", scExaminationMain.getClassId());
        queryWrapper.like(StringUtils.isNotBlank(scExaminationMain.getSubjectId()), "subject_id", scExaminationMain.getSubjectId());
        queryWrapper.eq(scExaminationMain.getExaminationDate() != null, "examination_date", scExaminationMain.getExaminationDate());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("examination_id");
        return queryWrapper;

    }
}