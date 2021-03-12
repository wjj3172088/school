package com.qh.basic;

import com.qh.basic.controller.ScClassController;
import com.qh.basic.domain.ScClass;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 15:21
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ScClassTest {
    @Autowired
    private ScClassController scClassController;

    @Test
    public void getInfo() {
        AjaxResult r = scClassController.getInfo("362881966f63c252016f63ddedbc0035");
        System.out.println(r);
    }

    @Test
    public void add() {
        ScClass scClass = new ScClass();
        scClass.setClassId("4028801475d8f1490175d8f149860000");
        scClass.setGrade(16);
        scClass.setClassNum("6");
        scClass.setOrgId("362881966f63c252016f63ddedbc0035");
        scClass.setTeachId("11");
        scClassController.add(scClass);
    }

    @Test
    public void delete() {
        String[] str = new String[]{"4028801475d8f1490175d8f149860000"};
        scClassController.remove(str);
    }
}
