 package com.qh.collect.model.response;

 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.Setter;

 import java.io.Serializable;

/**
 * @Description: 汉王考勤Socket启动状态返回情况Resp
 * @Author: huangdaoquan
 * @Date: 2020/12/24 16:02
 *
 * @return
 */
  @Setter
  @Getter
  @AllArgsConstructor
  public class HwSocketStatusResp implements Serializable{

    /**
     * 汉王考勤Socket侦听总开关
     */
    private Boolean serverRunswitch=true;

    /**
     * 当前侦听服务是否开启
     */
    private Boolean serverRunning=false;

    /**
     * 当前侦听服务配置的端口
     */
    private int serverPort;

    /**
     * 当前侦听服务配置的IP地址
     */
    private String serverIP;

    /**
     * 消息
     */
    private String msg;

 }
