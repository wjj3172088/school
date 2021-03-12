package com.qh.collect.controller;

import com.qh.collect.model.response.HwSocketStatusResp;
import com.qh.collect.service.IHwAttendanceService;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 汉王考勤Controller
 * 
 * @author 黄道权
 * @date 2020-12-23
 */
@RestController
@RequestMapping("/hwattendance" )
@Component
public class HwAttendanceController extends BaseController {

    @Autowired
    IHwAttendanceService hwAttendanceServiceImpl;

    /**
     * 启动/停止汉王考勤机侦听服务
     */
    @GetMapping("/startListenerAction")
    @PostConstruct
    public R<HwSocketStatusResp> startListenerAction()
    {
        System.out.println("(♥◠‿◠)ﾉﾞ  启动/停止汉王考勤机侦听服务   ლ(´ڡ`ლ)ﾞ ");
        HwSocketStatusResp hwSocketStatusResp= hwAttendanceServiceImpl.startListenerAction();
        return R.ok(hwSocketStatusResp);
    }


    /**
     * 读取查看汉王侦听服务
     */
    @GetMapping("/getListenerAction")
    public R<HwSocketStatusResp> getListenerAction()
    {
        HwSocketStatusResp hwSocketStatusResp= hwAttendanceServiceImpl.getListenerAction();
        return R.ok(hwSocketStatusResp);
    }

}
