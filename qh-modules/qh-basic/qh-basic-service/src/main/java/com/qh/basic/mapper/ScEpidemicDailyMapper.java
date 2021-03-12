package com.qh.basic.mapper;

import com.qh.basic.api.domain.ScEpidemicDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 疫情日报Mapper接口
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
public interface ScEpidemicDailyMapper extends BaseMapper<ScEpidemicDaily> {

    /**
     * 根据所有学校批量 创建生成当天的疫情日报
     * @return
     */
    int batchTodayAddByOrg();
}
