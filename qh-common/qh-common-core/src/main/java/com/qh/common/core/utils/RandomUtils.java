/**
 * 
 */
package com.qh.common.core.utils;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import com.qh.common.core.utils.http.DateUtils;

/**
 * 随机数
 * @date 2012-1-30 上午06:53:56
 * @author fcj2593@163.com
 * @version v 1.0
 */
public class RandomUtils {
	private static final String StrChar = "xlzxhxjnyj5u0evam0cmc8zkpxvg28okmugfdsnhvtdyosafa2t0dxqz";
	private static final String NumChar = "12434345667456890698767890";
	
	/**
	 * 获取随机字符串
	 * @param length 随机数长度
	 * @param RandomChar 随机串，最终从中提取字符组成结果
	 * @return 随机字符串
	 */
	public static String generateString(int length, String RandomChar){ 
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int CharLen=RandomChar.length();
		for (int i = 0; i < length; i++) 
			sb.append(RandomChar.charAt(random.nextInt(CharLen)));
		return sb.toString(); 
	}
	
	/**
	 * 返回一个数字随机数，0-9的出现概率可能不相同
	 * @param length 随机数长度
	 * @return 返回由数字组成的随机字符串
	 */
    public static String getNumberRandom(int length) {
        return generateString(length, NumChar);
    }
    
    /**
     * 返回一个字符随机数，种子可能只包含小写英数字，各英数字字符出现的概率可能不相同
     * @param length 随机数长度
     * @return 随机字符串
     */
    public static String getLetterRandom(int length){
    	return generateString(length,StrChar);
    }
    /**
     * 获取UUID
     * @Title: getUUID 
     * @Description: 
     * @param @return    
     * @return String   
     * @throws
     */
    public static String getUUID(){
    		return UUID.randomUUID().toString();
    }
    /**
     * 获取按时间排列+随机数
     * @Title: getDateFormateRandom 
     * @Description: 
     * @param @param length
     * @param @return    
     * @return String   
     * @throws
     */
    public static String getDateFormateRandom(int length){
    	return DateUtils.format("yyyyMMddHHmmss")+(length>0?getNumberRandom(length):"");
    }
    
	public static void main(String[] args) {
		String aString="2101.1";
		//System.out.println(String.format("ad%1$0", 0.1));
		DecimalFormat   fmt   =   new   DecimalFormat("#############0.00");    
        String outStr = null;  
        double d;  
        try {  
            d = Double.parseDouble(aString);  
            outStr = fmt.format(d);  
            System.out.println(outStr);
        } catch (Exception e) {  
        }  
        
		
//		System.out.println(BigDecimalUtils.yuanFormat(aString));
	}
}
