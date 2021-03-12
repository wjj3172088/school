package com.qh.basic.service;

import com.qh.basic.api.domain.ScEpidemicDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 疫情日报Service接口
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
public interface IScEpidemicDailyService extends IService<ScEpidemicDaily> {


    /**
     * 查询疫情日报集合
     *
     * @param page         分页信息
     * @param scEpidemicDaily 操作疫情日报对象
     * @return 操作疫情日报集合
     */
    IPage<ScEpidemicDaily> selectScEpidemicDailyListByPage(IPage<ScEpidemicDaily> page, ScEpidemicDaily scEpidemicDaily);

    /**
     * 查询疫情日报集合
     *
     * @param scEpidemicDaily 操作疫情日报对象
     * @return 操作疫情日报集合
     */
    List<ScEpidemicDaily> selectScEpidemicDailyList(ScEpidemicDaily scEpidemicDaily);

    /**
     * 根据所有学校批量 创建生成当天的疫情日报
     * @return
     */
    int batchTodayAddByOrg();

}
