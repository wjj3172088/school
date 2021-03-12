package com.qh.collect.service.impl;

import Com.FirstSolver.Security.Utils;
import Com.FirstSolver.Splash.FaceId_Item;
import Com.FirstSolver.Splash.ISocketServerThreadTask;
import Com.FirstSolver.Splash.NetworkStreamPlus;
import Com.FirstSolver.Splash.TcpListenerPlus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qh.basic.api.StaffService;
import com.qh.basic.api.TeacherService;
import com.qh.basic.api.domain.ScStaffInfo;
import com.qh.basic.api.domain.ScTeacher;
import com.qh.collect.config.SystemHanvonConfig;
import com.qh.collect.domain.HanvonAttendanceLog;
import com.qh.collect.domain.HanvonDevice;
import com.qh.collect.domain.ScCheckinAttendance;
import com.qh.collect.model.response.HwSocketStatusResp;
import com.qh.collect.service.IHanvonAttendanceLogService;
import com.qh.collect.service.IHanvonDeviceService;
import com.qh.collect.service.IHwAttendanceService;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtils;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 汉王考勤机业务层处理
 *
 * @author 黄道权
 * @date 2020-11-13
 */
@Service("hwAttendanceServiceImpl")
@Slf4j
public class HwAttendanceServiceImpl  implements IHwAttendanceService, ISocketServerThreadTask {

    private static final Logger log = LoggerFactory.getLogger(HwAttendanceServiceImpl.class);

    @Autowired
    private SystemHanvonConfig systemHanvonConfig;

    @Autowired
    private IHanvonAttendanceLogService iHanvonAttendanceLogService;

    @Autowired
    private IHanvonDeviceService hanvonDeviceService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StaffService staffService;

    @Autowired
    private ScCheckinAttendanceServiceImpl scCheckinAttendanceServiceImpl;

    /**
     * 当前侦听服务是否开启
     */
    private static Boolean serverRunning=false;

    public static TcpListenerPlus TcpServer = null;

    private final String DeviceCharset = "GBK";

    /**
     * 读取侦听服务状态
     * @return
     */
    @Override
    public HwSocketStatusResp getListenerAction(){
        Boolean serverRunSwitch=Boolean.parseBoolean( systemHanvonConfig.getServerRunswitch()) ;
        int serverPort=Integer.parseInt(systemHanvonConfig.getServerPort());
        String serverIp=systemHanvonConfig.getServerIP();
        if(!serverRunSwitch){
            return new HwSocketStatusResp(serverRunSwitch,serverRunning,serverPort
                    ,serverIp,"汉王考勤数据同步总开关已经关闭，请联系管理员开启");
        }
        if(null != TcpServer )
        {
            serverRunning = true;
        }else{
            serverRunning = false;

        }
        return new HwSocketStatusResp(serverRunSwitch,serverRunning,serverPort
                ,serverIp,"汉王考勤机侦听服务为："+serverRunning.toString());
    }

    /**
     * 启动/停止侦听服务
     * @return
     */
    @Override
    public HwSocketStatusResp startListenerAction(){
        Boolean serverRunswitch=Boolean.parseBoolean( systemHanvonConfig.getServerRunswitch()) ;
        int serverPort=Integer.parseInt(systemHanvonConfig.getServerPort());
        String serverIp=systemHanvonConfig.getServerIP();
        try {
            if(!serverRunswitch){
                return new HwSocketStatusResp(serverRunswitch,serverRunning,serverPort
                        ,serverIp,"汉王考勤数据同步总开关已经关闭，请联系管理员开启");
            }
            if(serverRunning)
            {
                System.out.print("汉王考勤机侦听服务停止侦听begin");
                if(TcpServer != null)
                {
                    TcpServer.close();
                    TcpServer = null;
                }
                serverRunning = false;
                return new HwSocketStatusResp(serverRunswitch,serverRunning,serverPort
                        ,serverIp,"汉王考勤机侦听服务停止侦听成功");
            }
            else
            {
                System.out.print("汉王考勤机侦听服务开始侦听");
                // 创建侦听服务器
                TcpServer = new TcpListenerPlus(Integer.parseInt(systemHanvonConfig.getServerPort()), 0
                        , InetAddress.getByName(systemHanvonConfig.getServerIP()), false);
                // 设置通信线程任务委托
                TcpServer.ThreadTaskDelegate = this;
                TcpServer.StartListenThread(null, 0, 0);
                serverRunning = true;
                return new HwSocketStatusResp(serverRunswitch,serverRunning,serverPort
                        ,serverIp,"汉王考勤机侦听服务开始侦听成功");
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
            return new HwSocketStatusResp(serverRunswitch,serverRunning,serverPort
                    ,serverIp,"汉王考勤机侦听服务异常;"+e.getMessage());
        }
    }

    /**
     * 系统侦听回调
     * @param stream
     * @throws Exception
     */
    @Override
    public void OnServerTaskRequest(NetworkStreamPlus stream) throws Exception {
        // 设备特殊通信密码 注意：密码要和设备通信密码一致
        // stream.setSecretKey(systemHanvonConfig.getSecretKey());
        // 设备序列号
        String serialNumber="";
        while(true)
        {
            try {
                // 读取数据
                String Answer = stream.Read(DeviceCharset);
                // 显示读取信息
                System.out.print(Answer + "\r\n");
                if(Answer.startsWith("PostRecord"))
                {   // 提取序列号并保存
                    serialNumber = FaceId_Item.GetKeyValue(Answer, "sn");
                    //答复已准备好接收考勤记录
                    stream.Write("Return(result=\"success\" postphoto=\"false\")", DeviceCharset);
                }
                else if(Answer.startsWith("Record"))
                {   // 读取考勤记录
                    stream.Write("Return(result=\"success\")", DeviceCharset);
                    //保存所有考勤信息
                    saveHanVonAttendance(getHanvonAttendanceLog(Answer,serialNumber));
                }
                else if(Answer.startsWith("PostEmployee"))
                {   // 准备上传人员信息
                    stream.Write("Return(result=\"success\")", DeviceCharset);
                }
                else if(Answer.startsWith("Employee"))
                {   // 读取人员信息
                    stream.Write("Return(result=\"success\")", DeviceCharset);
                }
                else if (Answer.startsWith("GetRequest"))
                {   // 下发命令
                    Platform.runLater(() -> {
                        String Command = "GetDeviceInfo()";
                        if (!Utils.IsNullOrEmpty(Command))
                        {
                            try
                            {
                                stream.Write(Command, DeviceCharset);
                            }
                            catch (Exception ex)
                            {
                            }
                        }
                    });
                }
                else if(Answer.startsWith("Quit"))
                {   // 结束会话
                    break;
                }
            }
            catch (Exception ex)
            {
                System.out.print("ex:"+ex.getMessage()+ "\r\n");
                break;  // 连接断开
            }
        }
    }

    /**
     * 解析侦听返回 Record记录数据 正则表达式
     */
    Pattern pattern = Pattern.compile("id\\s*=\\s*\"([^\"]*)\"\\s*" +
            "name\\s*=\\s*\"([^\"]*)\"\\s*" +
            "time\\s*=\\s*\"([^\"]*)\"\\s*" +
            "type\\s*=\\s*\"([^\"]*)\"");

    /**
     * 解析考勤返回数据，并赋值到HanvonAttendanceLog实体
     * @param cardInfo 侦听到的字符串
     * @param serialNumber 设备Id
     * @return
     */
    public HanvonAttendanceLog getHanvonAttendanceLog(String cardInfo,String serialNumber){
        cardInfo = cardInfo.replace("Record(","").replace(")","");
        Matcher matcher = pattern.matcher(cardInfo);
        HanvonAttendanceLog hanvonAttendanceLog = new HanvonAttendanceLog();
        while (matcher.find()) {
            hanvonAttendanceLog.setPeopleNumber(matcher.group(1));
            hanvonAttendanceLog.setPeopleName(matcher.group(2));
            hanvonAttendanceLog.setCheckTime(DateUtils.date2TimeStamp(matcher.group(3),DateUtils.yyyy_MM_dd_HH_mm_ss) );
            hanvonAttendanceLog.setAttendanceType(matcher.group(4));
        }
        //获取该设备对应的学校Id
        QueryWrapper<HanvonDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "device_id", serialNumber);
        HanvonDevice hanvonDevice = hanvonDeviceService.getOne(queryWrapper);
        if(null != hanvonDevice && StringUtils.isNotBlank( hanvonDevice.getOrgId())){
            hanvonAttendanceLog.setOrgId(hanvonDevice.getOrgId());
        }else{
            hanvonAttendanceLog.setOrgId(systemHanvonConfig.getDefaultOrgId());
        }
        hanvonAttendanceLog.setCreateDate(System.currentTimeMillis() / 1000);
        hanvonAttendanceLog.setRecorderType(2);
        hanvonAttendanceLog.setDeviceId(serialNumber);
        hanvonAttendanceLog.setAttendanceDate(Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd)));
        return  hanvonAttendanceLog;
    }

    /**
     * 保存所有考勤信息
     * @param hanvonAttendanceLog
     * @return
     */
    public Boolean saveHanVonAttendance(HanvonAttendanceLog hanvonAttendanceLog){
        //保存数据到汉王考勤记录日志表
        Boolean isSuccess = iHanvonAttendanceLogService.save(hanvonAttendanceLog);
        ScTeacher scTeacher = teacherService.selectByTeacName(hanvonAttendanceLog.getOrgId()
                ,hanvonAttendanceLog.getPeopleName(),hanvonAttendanceLog.getPeopleNumber());
        if(isSuccess && null != scTeacher
                && StringUtils.isNotBlank(scTeacher.getAccId()) && StringUtils.isNotBlank(hanvonAttendanceLog.getOrgId())){
            //保存数据至公共考勤记录表中
            calculationAttendance(hanvonAttendanceLog.getOrgId(),scTeacher.getAccId(),2
                    ,scTeacher.getTeacName(),hanvonAttendanceLog.getCheckTime());
            return isSuccess;
        }
        ScStaffInfo staffInfo = staffService.selectByStaffName(hanvonAttendanceLog.getOrgId()
                ,hanvonAttendanceLog.getPeopleName(),hanvonAttendanceLog.getPeopleNumber());
        if(isSuccess && null != staffInfo
                && StringUtils.isNotBlank(staffInfo.getAccId()) && StringUtils.isNotBlank(hanvonAttendanceLog.getOrgId())){
            //保存数据至公共考勤记录表中
            calculationAttendance(hanvonAttendanceLog.getOrgId(),staffInfo.getAccId(),2
                    ,staffInfo.getStaffName(),hanvonAttendanceLog.getCheckTime());
            return isSuccess;
        }
        return isSuccess;
    }


    /**
     * 保存考勤记录信息 t_sc_checkin_attendance
     * @param orgId
     * @param accOrStuId
     * @param recorderType 1、学生  2、教职工
     * @return
     */
    private synchronized boolean calculationAttendance(String orgId, String accOrStuId, int recorderType,String teacName,Long checkTime) {
        int day = Integer.parseInt(DateUtils.format(DateUtils.yyyyMMdd));
        try {
            ScCheckinAttendance attendance = scCheckinAttendanceServiceImpl.queryAttendanceByDay(accOrStuId, orgId, day, recorderType);
            // 当天没有考勤,直接记录
            if (null == attendance ) {
                log.info(teacName+"的考勤记录插入！日期:"+day);
                return scCheckinAttendanceServiceImpl.saveCheckinAttendance(orgId, accOrStuId,  recorderType, checkTime);
            }else{
                //判断当前打卡时间是否小于第一次打开5分钟，则不予插入
                Long diffTime = (checkTime-attendance.getIntoTime())/60;
                if(diffTime<5){
                    log.info(teacName+"的考勤记录5分钟内反复插入！不插入至考勤表中。日期:"+day);
                    return false;
                }
                attendance.setOuttoTime(checkTime);
                attendance.setStateMark(5);
                log.info(teacName+"的考勤记录更新！日期:"+day);
                return scCheckinAttendanceServiceImpl.updateById(attendance);
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return false;
    }

}
