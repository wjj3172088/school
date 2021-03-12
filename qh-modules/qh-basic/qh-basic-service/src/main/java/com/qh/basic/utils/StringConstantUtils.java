package com.qh.basic.utils;

import com.qh.common.core.utils.http.ResourceUtil;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 16:33
 * @Description:
 */
public class StringConstantUtils {

    /**
     * REDIS前缀
     * 报警基站 学生停留时间和次数
     * +学生标签+基站id
     */
    public static final String REDIS_AREA_WARN = "scs:cache:areaWarn:";

    /**
     * 学生轨迹
     */
    public static final String REDIS_STUDENT_TRACK = ResourceUtil.getConfigByName("tracks.safety");

    /**
     * 基站状态
     */
    public static final String REDIS_STATION_OFFONLINES = ResourceUtil.getConfigByName("offonlines.safety");


    /**
     * 烟感报警
     */
    public static final String REDIS_SMOKE_ALARM = ResourceUtil.getConfigByName("smokealarm.safety");

    /**
     * 文件系统公网域名
     */
    public static final String MINIOIP = ResourceUtil.getConfigByName("minio.service.ip");
    /**
     * 人脸识别系统
     */
    public static final String FACEIP = ResourceUtil.getConfigByName("face.service.ip");


    /**
     * 百度秘钥
     */
    public static final String BAIDU_AK = ResourceUtil.getConfigByName("baidu.ak");


//    //手机短信验证码
//    //登录验证码
//    public static final String REDIS_APP_LOGINVAL_CODE = "scs:app:user:loginval:code:";//+手机
//    //修改绑定手机验证码
//    public static final String REDIS_APP_MODPHOVAL_CODE = "scs:app:user:modphoval:code:";//+手机
//    //修改密码验证码
//    public static final String REDIS_APP_MODPWDVAL_CODE = "scs:app:user:modpwdval:code:";//+手机
//
//    //添加子帐号验证码
//    public static final String REDIS_APP_ADDACC_CODE = "scs:app:user:addacc:code:";//+手机

    /**
     * 登录验证码LOGINVAL_CODE_%s
     */
    public static final String SSCL_LOGINVAL_CODE = "LOGINVAL_CODE_%s";
    /**
     * 修改绑定手机验证码MODPHOVAL_CODE_%s
     */
    public static final String SSCL_MODPHOVAL_CODE = "MODPHOVAL_CODE_%s";
    /**
     * 修改密码验证码MODPWDVAL_CODE_%s
     */
    public static final String SSCL_MODPWDVAL_CODE = "MODPWDVAL_CODE_%s";
    /**
     * 添加子帐号验证码ADDACC_CODE_%s
     */
    public static final String SSCL_ADDACC_CODE = "ADDACC_CODE_%s";


    /**
     * 登录模块
     * token
     * +手机
     */
    public static final String REDIS_APP_USER_TOKEN = "scs:app:user:token:";

    /**
     * 登录用户信息
     * +token
     */
    public static final String REDIS_APP_TOKEN_USER = "scs:app:token:user:";

    /**
     * 学生最新轨迹
     * +stuId
     */
    public static final String REDIS_APP_STU_LASTTRACK = "scs:app:stu:lasttrack:";

    /**
     * 火灾短信通知
     * +stuId
     */
    public static final String REDIS_SMS_FIRE = "sms:fire:";

    /**
     * 老师邀请码
     * +六位号
     */
    public static final String SSCL_TEAC_CODE = "SCS_TEAC_CODE_%S";

    /**
     * 班级邀请码
     * +六位号
     */
    public static final String SSCL_CLASS_CODE = "SCS_CLASS_CODE_%s";


    /**
     * 自动离校判断是否最后一次跑
     */
    public static final String REDIS_APP_AUTO_LEAVE_SCHOOL = "scs:app:autoleave:school:";

    /**
     * 自动任务统计当前在校人数
     * +orgId
     */
    public static final String REDIS_APP_STATIC_IN_SCHOOL_NUM = "scs:app:static:school:";

    public static final String REDIS_APP_STATIC_OUT_SCHOOL_NUM = "scs:app:static:outschool:";

    /**
     * 轨迹处理中，缓存以下信息
     */
    public static final String SSCL_STUDENT_INFO = "INFO_STUDENT";
    /**
     *家长对应的学生列表(1天)ACC_TO_SETUDENT_LIST_%s_%s
     */
    public static final String SSCL_ACC_TO_SETUDENT_LIST="ACC_TO_SETUDENT_LIST_%s_%s";

    public static final String STATION_INFO = "scs:info:station";

    public static final String TEACHER_INFO = "scs:info:teacher";

    public static final String NEWEST_TRACK = "scs:info:newtrack";

    public static final String SCHOOL_INFO = "scs:info:school";

    public static final String SSCL_DICT_CACHE_CODE = "DICT_CACHE_CODE_%s";

    /**
     * 系统登录验证码
     */
    public static final String SYS_LOGIN_CODE = "scs:sys:login:";
}
