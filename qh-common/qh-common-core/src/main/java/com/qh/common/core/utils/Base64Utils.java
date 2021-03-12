package com.qh.common.core.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * base 64 工具类
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2018年8月10日 上午11:44:13
 */
public class Base64Utils {
	/**
	 * @throws UnsupportedEncodingException 
	 * 中文字符串截取 String[] encodes = new String[] { "GB2312", "GBK", "GB18030", "CP936",
                "CNS11643", "UTF-8" };
	 * @Title:
	 * @Description: TODO
	 * @param text
	 * @param length
	 * @param encode
	 * @return  
	 * @return String
	 * @throws
	 */
	public static String subString(String text, int length, String encode)  {
		if (text == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : text.toCharArray()) {
			try {
				currentLength += String.valueOf(c).getBytes(encode).length;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (currentLength <= length) {
				sb.append(c);
			} else {
				break;
			}
		}
		return sb.toString();
	}
	
	public static String subString(String text, int length) {
		return subString(text, length, "UTF-8");
	}

	/**
	 * base64编码输出小写形式
	* @author Loren 
	* @DateTime 2018年8月10日 上午11:43:16 
	*
	* @param data
	* @return
	 */
	public static String encode(String data) {
		byte[] binaryData = data.getBytes();
		String encodeBase64String = Base64.encodeBase64String(binaryData);
		return encodeBase64String;
	}

	/**
	 * base64 编码输出大写形式
	* @author Loren 
	* @DateTime 2018年8月10日 上午11:44:46 
	*
	* @param data
	* @return
	 */
	public static String encodeUpper(String data) {
		String upperCase = encode(data).toUpperCase();
		return upperCase;
	}

}
