 package com.qh.basic.model.response;

 import lombok.Getter;
 import lombok.NoArgsConstructor;
 import lombok.Setter;

 import java.io.Serializable;

 /**
   * 注册次数返回封装bean
   * @Title:
   * @Description:
   * @Copyright:
   * @Company:
   * @author:fangjiatao
   * @version:Neon.3 Release (4.6.3)
   * @Create Date Time: 2020年9月4日 下午4:28:40
   * @Update Date Time: 2020年9月4日 下午4:28:40
   * @see
   */
  @Setter
  @Getter
  @NoArgsConstructor
  public class RegisterStatisticsResp implements Serializable{
      /**
      * @Fields serialVersionUID : TODO
      */
     private static final long serialVersionUID = -5967913450419984936L;
     private String orgId;
     private String orgName;
     private String classId;
     private String className;
     /**
      * 总数
      */
     private int registerNum;
     /**
      * 每日新增
      */
     private int addNum;
     /**
      * 返回时间组合
      */
     private String time;

     /**
      * 总激活
      */
     private int activateNum;

     /**
      * 每日新增激活
      */
     private int addActivateNum;

 }
