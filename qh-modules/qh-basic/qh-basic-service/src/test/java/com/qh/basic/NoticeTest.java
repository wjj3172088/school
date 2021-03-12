package com.qh.basic;

import com.qh.basic.model.request.notice.NoticeAddRequest;
import com.qh.basic.service.IScNoticeService;
import com.qh.common.security.utils.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/30 15:44
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class NoticeTest {
    @Autowired
    private IScNoticeService noticeService;

    @Test
    public void testNotice() {
        NoticeAddRequest request = new NoticeAddRequest();
        request.setNoticeTitle("测试标题20201201");
        request.setNoticeContent("测试内容20201201");
//        request.setStateMark(StateMarkEnum.CONFIRMED.getCode());
        noticeService.addNotice(request, SecurityUtils.getLoginUser());
    }
}
