package com.qh.job.task;

import com.qh.job.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 疫情日报每日生成记录任务
 * 
 * @author huangdaoquan
 */
@Component("epidemicDailyTask")
public class EpidemicDailyTask
{
    @Autowired
    ISysJobService sysJobService;

    /**
     * 每日创建初始化疫情日报
     */
    public void batchTodayAddByOrg()
    {
        sysJobService.batchTodayAddByOrg();
    }
}
