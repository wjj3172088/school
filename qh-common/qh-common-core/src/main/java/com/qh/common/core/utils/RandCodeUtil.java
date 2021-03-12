package com.qh.common.core.utils;

import com.qh.common.core.utils.http.ResourceUtil;

import java.util.Random;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 16:31
 * @Description:
 */
public class RandCodeUtil {

    /**
     *  @author duosheng.mo
     *	@DateTime 2016年5月17日 下午3:47:39
     *  @serverComment 获取数字验证码
     *
     *  @return
     */
    public static String randNumberCode(){
        int randCodeLength = Integer.parseInt(ResourceUtil.getRandCodeLength());
        return extractRandCode("1",randCodeLength);
    }

    /**
     *  @author yuhang.weng
     *	@DateTime 2016年6月3日 上午11:19:42
     *  @serverComment 自定义随机码生成函数
     *
     *  @param randCodeType '1'数字 '2'小写字母 '3'大写字母 '4'字符 '5'混合字符串
     *  @param length 随机码长度
     *  @return
     */
    public static String randNumberCodeByCustom(String randCodeType, int length){
        return extractRandCode(randCodeType, length);
    }

    /**
     *  @author duosheng.mo
     *	@DateTime 2016年5月17日 下午3:47:39
     *  @serverComment 根据配置生成验证码
     *
     *  @return
     */
    public static String randCode(){
        String randCodeType = ResourceUtil.getRandCodeType();
        int randCodeLength = Integer.parseInt(ResourceUtil.getRandCodeLength());
        return extractRandCode(randCodeType,randCodeLength);
    }

    /**
     * @return 随机码
     */
    private static String extractRandCode(String randCodeType,int randCodeLength) {
        if (randCodeType == null) {
            return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
        } else {
            switch (randCodeType.charAt(0)) {
                case '1':
                    return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
                case '2':
                    return RandCodeImageEnum.LOWER_CHAR.generateStr(randCodeLength);
                case '3':
                    return RandCodeImageEnum.UPPER_CHAR.generateStr(randCodeLength);
                case '4':
                    return RandCodeImageEnum.LETTER_CHAR.generateStr(randCodeLength);
                case '5':
                    return RandCodeImageEnum.ALL_CHAR.generateStr(randCodeLength);
                default:
                    return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
            }
        }
    }
}

/**
 *  版权归
 *  验证码辅助类
 *  @author duosheng.mo
 *  @DateTime 2016年5月17日 下午3:26:41
 */
enum RandCodeImageEnum {
    /**
     * 混合字符串
     */
    ALL_CHAR("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    /**
     * 字符
     */
    LETTER_CHAR("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    /**
     * 小写字母
     */
    LOWER_CHAR("abcdefghijklmnopqrstuvwxyz"),
    /**
     * 数字
     */
    NUMBER_CHAR("0123456789"),
    /**
     * 大写字符
     */
    UPPER_CHAR("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    /**
     * 待生成的字符串
     */
    private String charStr;

    /**
     * @param charStr
     */
    private RandCodeImageEnum(final String charStr) {
        this.charStr = charStr;
    }

    /**
     * 生产随机验证码
     *
     * @param codeLength
     *            验证码的长度
     * @return 验证码
     */
    public String generateStr(final int codeLength) {
        final StringBuffer sb = new StringBuffer();
        final Random random = new Random();
        final String sourseStr = getCharStr();
        for (int i = 0; i < codeLength; i++) {
            sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
        }
        return sb.toString();
    }

    /**
     * @return the {@link #charStr}
     */
    public String getCharStr() {
        return charStr;
    }

}