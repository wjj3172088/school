package com.qh.basic;

import com.qh.basic.api.model.request.teacher.TeacherSaveRequest;
import com.qh.basic.controller.ScTeacherController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 17:12
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class TeacherTest {
    @Autowired
    private ScTeacherController teacherController;

    @Test
    public void add() {
        TeacherSaveRequest teacher = new TeacherSaveRequest();
        teacher.setTeacName("ww");
        teacher.setIdCard("330781198606085918");
        teacher.setSpecialtyName("1");
        teacher.setMobile("15555555555");
        teacherController.add(teacher);
    }
    @Test
    public void edit() {
        TeacherSaveRequest teacher = new TeacherSaveRequest();
        teacher.setTeacId("4028801475dfd5850175dfd5852a0000");
        teacher.setTeacName("ww");
        teacher.setIdCard("330781198606085918");
        teacher.setSpecialtyName("1");
        teacher.setMobile("15555555555");
        teacherController.edit(teacher);
    }

    @Test
    public void delete() {
        String[] str = new String[]{"4028801475dfd5850175dfd5852a0000"};
        teacherController.remove(str);
    }
}
