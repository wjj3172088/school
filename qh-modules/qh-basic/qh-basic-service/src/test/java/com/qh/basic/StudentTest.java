package com.qh.basic;

import com.qh.basic.controller.ScStudentController;
import com.qh.basic.domain.ScStudent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/18 15:49
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class StudentTest {
    @Autowired
    private ScStudentController studentController;
}
