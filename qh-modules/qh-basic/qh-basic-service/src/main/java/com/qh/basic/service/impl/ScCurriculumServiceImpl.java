package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qh.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.mapper.ScCurriculumMapper;
import com.qh.basic.domain.ScCurriculum;
import com.qh.basic.service.IScCurriculumService;

/**
 * 课程Service业务层处理
 *
 * @author 黄道权
 * @date 2020-11-13
 */
@Service
public class ScCurriculumServiceImpl extends ServiceImpl<ScCurriculumMapper, ScCurriculum> implements IScCurriculumService {

    @Autowired
    private ScCurriculumMapper curriculumMapper;

    @Autowired
    private PicUtils picUtils;
    /**
     * 查询课程集合
     *
     * @param scCurriculum 操作课程对象
     * @return 操作课程集合
     */
    @Override
    public IPage<ScCurriculum> selectScCurriculumListByPage(IPage<ScCurriculum> page, ScCurriculum scCurriculum){
        scCurriculum.setOrgId(SecurityUtils.getOrgId());
        //根据自定义SQL语句拼装查询
        IPage<ScCurriculum> pageScCurriculum = curriculumMapper.selectListByPage(page, scCurriculum);
        for (ScCurriculum curriculumInfoBean : pageScCurriculum.getRecords()) {
            curriculumInfoBean.setPList(picUtils.imageListFristDomainApp(curriculumInfoBean.getCumiculumPic()));
        }
        return pageScCurriculum;
    }


    /**
     * 查询课程参数拼接
     */
    private QueryWrapper<ScCurriculum> getQuery(ScCurriculum scCurriculum) {
        QueryWrapper<ScCurriculum> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(scCurriculum.getClassId()), "class_id", scCurriculum.getClassId());
        queryWrapper.eq(StringUtils.isNotBlank(scCurriculum.getOrgId()), "org_id", scCurriculum.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(scCurriculum.getCurriculumMark()), "curriculum_mark", scCurriculum.getCurriculumMark());
        queryWrapper.eq(StringUtils.isNotBlank(scCurriculum.getStateMark()), "state_mark", scCurriculum.getStateMark());
        queryWrapper.orderByDesc("curriculum_id");
        return queryWrapper;

    }
}
