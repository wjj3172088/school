package com.qh.common.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 15:15
 * @Description:
 */
public class ParamCheckUtil {
    /**
     * @param number
     * @return
     * @author duosheng.mo
     * @DateTime 2016-2-2 上午11:36:16
     * @serverComment 验证手机号码
     */
    public static boolean verifyPhone(String number) {
        if (number == null || "".equals(number)) {
            return false;
        }
        //String regExp = "^[1][3,4,5,7,8][0-9]{9}$"; // 验证手机号
        String regExp = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(number);
        return m.find();//boolean
    }

    /**
     * @param @param  idCard
     * @param @return 参数
     * @Title: validIdCard
     * @Description:校验身份证号
     * @author zhangzhf
     * @date 2019年10月31日
     */
    public static boolean validIdCard(String idCard) {
        int idCardLength = idCard.length();
        if (idCardLength == 18) {
            if (!ValidateIdCardUtils.validateIdCard18(idCard)) {
                return false;
            }
        } else if (idCardLength == 10) {
            String[] valArray = ValidateIdCardUtils.validateIdCard10(idCard);
            if (valArray != null) {
                if (valArray[2] != null) {
                    if ("true".equals(valArray[2])) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
