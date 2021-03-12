package com.qh.basic;

import com.qh.basic.controller.ScStaffInfoController;
import com.qh.basic.model.request.staffinfo.StaffInfoSaveRequest;
import com.qh.basic.api.model.request.staff.StaffInfoSearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/21 17:11
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class StaffInfoTest {
    @Autowired
    private ScStaffInfoController scStaffInfoController;

    @Test
    public void add() {
        StaffInfoSaveRequest request = new StaffInfoSaveRequest();
        request.setIdCard("330781198606085918");
        request.setJobTitle(1);
        request.setMobile("13555555555");
        request.setMotorNum("浙G45645");
        request.setMotorTagNum("45645");
        request.setNotMotorNum("浙G12345");
        request.setNotMotorTagNum("12345");
        request.setTrueName("wwwww");
        scStaffInfoController.add(request);
    }

    @Test
    public void search(){
        StaffInfoSearchRequest request=new StaffInfoSearchRequest();
        request.setIdCard("11");
        request.setMobile("144");
        request.setTrueName("ww");
        scStaffInfoController.list(request);
    }
}
