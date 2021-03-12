package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScSchoolworkMapper;
import com.qh.basic.domain.ScSchoolwork;
import com.qh.basic.service.IScSchoolworkService;

/**
 * 作业管理Service业务层处理
 *
 * @author 黄道权
 * @date 2020-11-17
 */
@Service
public class ScSchoolworkServiceImpl extends ServiceImpl<ScSchoolworkMapper, ScSchoolwork> implements IScSchoolworkService {

    @Autowired
    ScSchoolworkMapper schoolworkMapper;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询作业管理集合
     *
     * @param scSchoolwork 操作作业管理对象
     * @return 操作作业管理集合
     */
    @Override
    public IPage<ScSchoolwork> selectScSchoolworkListByPage(IPage<ScSchoolwork> page, ScSchoolwork scSchoolwork) {

        scSchoolwork.setOrgId(SecurityUtils.getOrgId());
        //根据自定义SQL语句拼装查询
        IPage<ScSchoolwork> pageScSchoolwork = schoolworkMapper.selectListByPage(page, scSchoolwork);

        for (ScSchoolwork scSchoolworkBean : pageScSchoolwork.getRecords()) {
            scSchoolworkBean.setPList(picUtils.imageListFristDomainApp(scSchoolworkBean.getPicurls()));
        }
        return pageScSchoolwork;
    }


    /**
     * 查询作业管理参数拼接
     */
    private QueryWrapper<ScSchoolwork> getQuery(ScSchoolwork scSchoolwork) {
        QueryWrapper<ScSchoolwork> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(scSchoolwork.getTitle()), "title", scSchoolwork.getTitle());
        queryWrapper.eq(StringUtils.isNotBlank(scSchoolwork.getOrgId()), "org_id", scSchoolwork.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scSchoolwork.getClassId()), "class_id", scSchoolwork.getClassId());
        queryWrapper.eq(scSchoolwork.getPublishDate() != null, "publish_date", scSchoolwork.getPublishDate());
        queryWrapper.eq(scSchoolwork.getStateMark() != null, "state_mark", scSchoolwork.getStateMark());
        queryWrapper.orderByDesc("school_work_id");
        return queryWrapper;

    }
}