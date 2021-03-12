package com.qh.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/21 15:33
 * @Description:
 */
public class MD5Utils {

    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    /**
     *  @author duosheng.mo
     *	@DateTime 2016年4月29日 下午5:24:42
     *  @serverCode 服务代码
     *  @serverComment 用户名密码进行加密
     *
     *  @param username
     *  @param password
     *  @return
     */
    public static String encryptPassword(String username, String password) {
        String inStr = username + password;
        if(org.apache.commons.lang3.StringUtils.isNotBlank(inStr)){
            return encrypt(inStr);
        }
        return "";
    }

    /**
     *  @author duosheng.mo
     *	@DateTime 2016年4月29日 下午5:26:25
     *  @serverCode 服务代码
     *  @serverComment 对密码加密
     *
     *  @param password
     *  @return
     */
    public static String encryptPassword(String password) {
        if(StringUtils.isNotBlank(password)){
            return encrypt(password);
        }
        return "";
    }

    /**
     *  @author duosheng.mo
     *	@DateTime 2016年5月3日 下午2:11:19
     *  @serverCode 服务代码
     *  @serverComment 加密
     *
     *  @param param
     *  @return
     */
    private static String encrypt(String param){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
        char[] charArray = param.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
