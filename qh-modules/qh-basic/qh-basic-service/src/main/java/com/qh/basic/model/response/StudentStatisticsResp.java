 package com.qh.basic.model.response;

 import com.qh.common.core.annotation.Excel;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
 import lombok.Setter;

 import java.io.Serializable;

 /**
  * 获取班级学生详细信息
  * @Title:
  * @Description:
  * @Copyright:
  * @Company:
  * @author:fangjiatao
  * @version:Neon.3 Release (4.6.3)
  * @Create Date Time: 2020年11月7日 上午10:53:10
  * @Update Date Time: 2020年11月7日 上午10:53:10
  * @see
  */
 @Setter
  @Getter
  @NoArgsConstructor
  public class StudentStatisticsResp implements Serializable{/**
      * @Fields serialVersionUID : TODO
      */
     private static final long serialVersionUID = 5443074411538706591L;

     @Excel(name = "学生姓名")
     private String stuName;

     @Excel(name = "学生姓名")
     private String orgName;

     @Excel(name = "班级")
     private String className;

     @Excel(name = "学生卡标签")
     private String tagNum;

     @Excel(name = "家长手机号")
     private String guardianMobile;

     @Excel(name = "监护人称谓")
     private String relation;

     @Excel(name = "监护人手机号")
     private String accNum;

     @Excel(name = "是否激活", readConverterExp = "-1=未激活,1=已激活",defaultValue = "已激活")
     private int lastTime;


 }
