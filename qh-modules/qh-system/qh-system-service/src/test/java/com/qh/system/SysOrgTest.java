package com.qh.system;

import com.qh.system.domain.SysOrg;
import com.qh.system.domain.vo.TreeSelect;
import com.qh.system.service.ISysOrgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/12 15:41
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SysOrgTest {
    @Autowired
    private ISysOrgService sysOrgService;

    @Test
    public void selectTest() {
        List<TreeSelect> list = sysOrgService.selectTree();
        System.out.println(list);
    }
}
