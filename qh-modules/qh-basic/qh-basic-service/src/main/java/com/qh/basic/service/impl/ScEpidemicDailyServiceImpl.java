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
import com.qh.basic.mapper.ScEpidemicDailyMapper;
import com.qh.basic.api.domain.ScEpidemicDaily;
import com.qh.basic.service.IScEpidemicDailyService;

import java.text.ParseException;
import java.util.List;

/**
 * 疫情日报Service业务层处理
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Service
public class ScEpidemicDailyServiceImpl extends ServiceImpl<ScEpidemicDailyMapper, ScEpidemicDaily> implements IScEpidemicDailyService {

    @Autowired
    ScEpidemicDailyMapper epidemicDailyMapper;

    /**
     * 分页查询疫情日报集合
     *
     * @param page         分页信息
     * @param scEpidemicDaily 操作疫情日报对象
     * @return 操作疫情日报集合
     */
    @Override
    public IPage<ScEpidemicDaily> selectScEpidemicDailyListByPage(IPage<ScEpidemicDaily> page, ScEpidemicDaily scEpidemicDaily) {
        try {
            //创建时间入参 时间转换为时间戳
            if(StringUtils.isNotBlank(scEpidemicDaily.getBeginTime()) ) {
                scEpidemicDaily.setBeginTime(DateUtil.dateToStamp(scEpidemicDaily.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(scEpidemicDaily.getEndTime()) ) {
                scEpidemicDaily.setEndTime(DateUtil.dateToStamp(scEpidemicDaily.getEndTime()+" 23:59:59"));
            }
            scEpidemicDaily.setOrgId(SecurityUtils.getOrgId());
            return this.page(page, getQuery(scEpidemicDaily));
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
    }

    /**
     * 查询疫情日报集合
     *
     * @param scEpidemicDaily 操作疫情日报对象
     * @return 操作疫情日报集合
     */
    @Override
    public List<ScEpidemicDaily> selectScEpidemicDailyList(ScEpidemicDaily scEpidemicDaily) {
        try {
            //创建时间入参 时间转换为时间戳
            if(StringUtils.isNotBlank(scEpidemicDaily.getBeginTime()) ) {
                scEpidemicDaily.setBeginTime(DateUtil.dateToStamp(scEpidemicDaily.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(scEpidemicDaily.getEndTime()) ) {
                scEpidemicDaily.setEndTime(DateUtil.dateToStamp(scEpidemicDaily.getEndTime()+" 23:59:59"));
            }
            scEpidemicDaily.setOrgId(SecurityUtils.getOrgId());
            return this.list(getQuery(scEpidemicDaily));
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
    }


    /**
     * 查询疫情日报参数拼接
     */
    private QueryWrapper<ScEpidemicDaily> getQuery(ScEpidemicDaily scEpidemicDaily) {
        QueryWrapper<ScEpidemicDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(StringUtils.isNotBlank(scEpidemicDaily.getBeginTime())&& StringUtils.isNotBlank(scEpidemicDaily.getEndTime())
                ,"create_date",scEpidemicDaily.getBeginTime(),scEpidemicDaily.getEndTime());
        queryWrapper.eq(StringUtils.isNotBlank(scEpidemicDaily.getOrgId()), "org_id", scEpidemicDaily.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scEpidemicDaily.getPublisherId()), "publisher_id", scEpidemicDaily.getPublisherId());
        queryWrapper.like(StringUtils.isNotBlank(scEpidemicDaily.getPublisherName()), "publisher_name", scEpidemicDaily.getPublisherName());
        queryWrapper.eq(scEpidemicDaily.getStateMark() != null, "state_mark", scEpidemicDaily.getStateMark());
        queryWrapper.eq(StringUtils.isNotBlank(scEpidemicDaily.getOrgId()), "org_id", scEpidemicDaily.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(scEpidemicDaily.getOrgName()), "org_name", scEpidemicDaily.getOrgName());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }

    /**
     * 根据所有学校批量 创建生成当天的疫情日报
     * @return
     */
    @Override
    public int batchTodayAddByOrg(){
       return epidemicDailyMapper.batchTodayAddByOrg();
    }
}