package com.qh.system;

import com.qh.system.api.domain.SmsResponseResult;
import com.qh.system.domain.vo.TreeSelect;
import com.qh.system.enums.SmsTemplateEnum;
import com.qh.system.service.ISmsService;
import com.qh.system.service.ISysOrgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: 黄道权
 * @Date: 2020/11/29 15:41
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SMSTest {
    @Autowired
    private ISmsService smsService;

    @Test
    public void selectTest() {
        //,15805795603,15905899849
        smsService.smsSendMsg("15858905825","", SmsTemplateEnum.getSmsTemplateEnum("家长账号激活引导"));
        //System.out.print(smsResponseResult);
    }
}
