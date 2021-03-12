package com.qh.collect;


import Com.FirstSolver.Splash.FaceId;
import Com.FirstSolver.Splash.FaceIdAnswer;
import Com.FirstSolver.Splash.FaceId_ErrorCode;
import com.qh.collect.service.impl.HwAttendanceServiceImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 黄道权
 * @Date: 2020/12/23 15:41
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class HanvonTest {

    @Test
    public void getRecordTest() {
        try(FaceId tcpClient = new FaceId("192.168.0.112", 9922)) {
            //设置通信密码 注意：密码和设备通信密码一致
            //tcpClient.setSecretKey("10092");

            List<String> RecordList = new LinkedList<>();
            // 获取考勤记录
            String Command = "GetRecord(end_time=\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\")";
            FaceIdAnswer output = new FaceIdAnswer();
            FaceId_ErrorCode ErrorCode = tcpClient.Execute(Command, output, "GBK");
            if (ErrorCode.equals(FaceId_ErrorCode.Success))
            {
                // 提取单条考勤记录
                Pattern p = Pattern.compile("\\b(time=.+\\R(?:photo=\"[^\"]+\")*)");
                Matcher m = p.matcher(output.answer);
                while(m.find())
                {
                    RecordList.add(m.group(1));
                }
                //listViewRecord.setItems(FXCollections.observableList(RecordList));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "获取考勤记录失败！", ButtonType.OK);
                alert.setTitle("GetRecord");
                alert.setHeaderText("错误");
                alert.showAndWait();
            }
        }
        catch (RuntimeException | IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "连接设备失败！", ButtonType.OK);
            alert.setTitle("GetRecord");
            alert.setHeaderText("错误");
            alert.showAndWait();
        }
    }

    @Autowired
    HwAttendanceServiceImpl hwAttendanceServiceImpl;

    @Test
    public void hwAttendanceTest() {
        //启动/停止侦听服务
        hwAttendanceServiceImpl.startListenerAction();
    }


    @Test
    public void insertHwTest() {
        hwAttendanceServiceImpl.saveHanVonAttendance(hwAttendanceServiceImpl.getHanvonAttendanceLog("Record(id=\"10002\" name=\"测试工号2\" time=\"2021-01-07 15:22:02\" type=\"face\")"
                ,"8152020070000088"));
    }
}
