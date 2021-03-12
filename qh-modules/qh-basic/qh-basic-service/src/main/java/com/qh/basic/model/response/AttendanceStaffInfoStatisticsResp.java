 package com.qh.basic.model.response;

 import com.qh.common.core.annotation.Excel;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
 import lombok.Setter;

 import java.io.Serializable;

 /**
  * @Author: huangdaoquan
  * @Date: 2020/11/25 13:25
  * @Description:
  */
 @Setter
  @Getter
  @NoArgsConstructor
  public class AttendanceStaffInfoStatisticsResp implements Serializable{/**
      * @Fields serialVersionUID : TODO
      */
     private static final long serialVersionUID = 5443074411538706591L;



     /**
      * 主键Id
      */
     private String accId;

     /**
      * 教职工工号
      */
     @Excel(name = "工号")
     private Long jobNumber;

     /**
      * 教职工姓名
      */
     @Excel(name = "教职工姓名")
     private String name;

     /**
      * 车牌号
      */
     @Excel(name = "车牌号")
     private String motorNum;

     /**
      * 考勤日期
      */
     @Excel(name = "考勤日期")
     private String attendanceDate;

     /**
      * 岗位
      */
     @Excel(name = "岗位")
     private String jobTitle;

     /**
      * 进校时间
      */
     @Excel(name = "进校时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
     private Integer intoTime;

     /**
      * 离校时间
      */
     @Excel(name = "离校时间", dateFormat = "yyyy-MM-dd HH:mm:ss", dateType = "int")
     private Integer outTime;

 }
