package com.qh.common.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.qh.common.core.utils.http.DateUtils;

/**
 * @title Bases
 * @description
 * @copyright
 * @company
 * @author fcj fcj2593@163.com
 * @version 1.0
 * @created on 2011-1-28, 11:01:18
 */

public class Bases {

	/**
	 * 是否都是中文
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkChinaStr(String name) {
		int n = 0;
		for (int i = 0; i < name.length(); i++) {
			n = (int) name.charAt(i);
			if (!(19968 <= n && n < 40869)) {
				return false;
			}
		}
		return true;
	}

	private static final String StrChar = "xlzxhxjnyj5u0evam0cmc8zkpxvg28okmugfdsnhvtdyosafa2t0dxqz";

	/**
	 * 获取系统秒时间 @Title: getSystemSeconds @Description: TODO @param: @return @return:
	 * long @throws
	 */
	public static int getSystemSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 时间截取 @Title: timeLengthIntercept @Description: @param @param
	 * time @param @return @return int @throws
	 */
	public static int timeLengthIntercept(long time) {
		if (time == 0l) {
			time = System.currentTimeMillis();
		}
		String dateline = time + "";
		dateline = dateline.substring(0, 10);
		return Integer.parseInt(dateline);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String dateTime = df.format(1294890859000L);
	}

	public static boolean isValidEmail(String paramString) {

		String regex = "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValidNumber(String paramString) {
		String regex = "/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0-9]|18[0-9])\\d{8}$/";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}

	/**
	 * unicode转字符串
	 * 
	 * @Title: unicode2Str @Description: @param @param str @param @return @return
	 *         String @throws
	 */
	public static String unicode2Str(String str) {
		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("\\\\u");
		int len = arr.length;
		sb.append(arr[0]);
		for (int i = 1; i < len; i++) {
			String tmp = arr[i];
			char c = (char) Integer.parseInt(tmp.substring(0, 4), 16);
			sb.append(c);
			sb.append(tmp.substring(4));
		}
		return sb.toString();
	}

	/**
	 * 字符串转Unicode
	 * 
	 * @Title: str2Unicode @Description: @param @param str @param @return @return
	 *         String @throws
	 */
	public static String str2Unicode(String str) {
		StringBuffer sb = new StringBuffer();
		char[] charArr = str.toCharArray();
		for (char ch : charArr) {
			if (ch > 128) {
				sb.append("\\u" + Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	private static Map<String, String> numMap = new HashMap<String, String>();
	static {
		numMap.put("1", "一");
		numMap.put("2", "二");
		numMap.put("3", "三");
		numMap.put("4", "四");
		numMap.put("5", "五");
		numMap.put("6", "六");
		numMap.put("7", "七");
		numMap.put("8", "八");
		numMap.put("9", "九");
		numMap.put("0", "零");
	}

	/**
	 * 数字转中文
	 * 
	 * @Title: numToStr @Description: @param @param num @param @return @return
	 *         String @throws
	 */
	public static String numToStr(String num) {
		if (!IsStringNotNull(num)) {
			return null;
		}
		if (numMap.containsKey(num)) {
			return numMap.get(num);
		}
		return "";
	}

	/**
	 * NULL转成“”
	 * 
	 * @Title: nullToString @Description: @param @param text @param @return @return
	 *         String @throws
	 */
	public static String nullToString(String text) {
		if (IsStringNotNull(text))
			return text.toString();
		return "";
	}

	public static String urlEncode(String text) {
		return urlEncode(text, "utf-8");
	}

	public static String urlEncode(String text, String code) {
		try {
			return URLEncoder.encode(text, code);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 集合MAP URLENCODE
	 * 
	 * @Title: mapUrlEncode @Description: @param @param
	 *         source @param @return @return Map<String,String> @throws
	 */
	public static Map<String, String> mapUrlEncode(Map<String, String> source) {
		return mapUrlEncode(source, "utf-8");
	}

	public static Map<String, String> mapUrlEncode(Map<String, String> source, String code) {
		if (source == null || source.size() == 0)
			return source;
		Iterator<Entry<String, String>> it = source.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			source.put(urlEncode(entry.getKey(), code), entry.getValue());
		}
		return source;
	}

	/**
	 * 字符串是否空<br>
	 * 如果为null或长度为0，返回false；否则返回true
	 * 
	 * @param text
	 *                 要测试的文本
	 * @return 是否为空
	 */
	public static boolean IsStringNotNull(String text) {
		return IsStringNotNull(text, false);
	}

	/**
	 * 字符串是否空<br>
	 * 如果为null或长度为0，返回false；否则返回true
	 * 
	 * @param text
	 *                   要测试的文本
	 * @param isTrim
	 *                   如果是true 对text进行去空
	 * @return 是否为空
	 */

	public static boolean IsStringNotNull(String text, boolean isTrim) {
		if (text == null || text.isEmpty()) {
			return false;
		}
		if (isTrim)
			return !text.trim().isEmpty();
		return true;
	}

	/**
	 * 字符串处理，返回去除空格后的字符串；如果字符串对象为null或长度为0,返回空字符串
	 * 
	 * @param text
	 *                 要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String ValidString(String text) {
		if (IsStringNotNull(text))
			return text.trim();
		return "";
	}

	/**
	 * 获取HASHTABLE中KEY对应的值。 如果找不到对应的Key对象，返回null。
	 * 
	 * @param hashtable
	 *                      HashTable集合，可以为null或无项目
	 * @param key
	 *                      key对象
	 * @return 对应的数值
	 */
	public static Object GetHashtableObjectValue(Map<?, ?> hashtable, Object keyI) {
		if (hashtable == null || hashtable.size() == 0) {
			return null;
		}
		if (hashtable.containsKey(keyI)) {
			return hashtable.get(keyI);
		}
		return null;
	}

	/**
	 * 取出数据库返回的HASHTABLE中key="out"的值。 如果找不到对应的Key对象，返回null。
	 * 
	 * @param hashtable
	 *                      HashTable集合，可以为null或无项目
	 * @return
	 */
	public static Map<?, ?> GetHashtableInProcedureHashtable(Map<?, ?> hashtable) {
		String keyS = "out";
		if (hashtable == null || hashtable.size() == 0) {
			return null;
		}
		if (hashtable.containsKey(keyS)) {
			return (Map<?, ?>) hashtable.get(keyS);
		}
		return null;
	}

	/**
	 * 取出数据库返回的HASHTABLE中key="rs"的值。 如果找不到对应的Key对象，返回null。
	 * 
	 * @param hashtable
	 *                      HashTable集合，可以为null或无项目
	 * @return
	 */
	public static Vector<?> GetVectorInProcedureHashtable(Map<?, ?> hashtable) {
		String keyS = "rs";
		if (hashtable == null || hashtable.size() == 0) {
			return null;
		}
		if (hashtable.containsKey(keyS)) {
			return (Vector<?>) hashtable.get(keyS);
		}
		return null;
	}

	/**
	 * 取出数据库返回的HASHTABLE中第一项的值。 如果HashTable为null或没有项目对象，返回null。
	 * 
	 * @param hashtable
	 *                      HashTable集合，可以为null或无项目
	 * @return
	 */
	public static Object GetObjectInVecter(Vector<?> vector) {
		if (vector == null || vector.size() == 0) {
			return null;
		}
		return vector.get(0);
	}

	/**
	 * 编码转换，如果参数不足，返回原串；如果无法转换，返回""(空串)
	 * 
	 * @param string
	 *                    要转换的字符串
	 * @param source
	 *                    源字符串的字符集
	 * @param newcode
	 *                    目标字符串的字符集
	 * @return 转换后的字符串
	 */
	public static String StringCodeing(String string, String source, String newcode) {
		if (!IsStringNotNull(string)) {
			return "";
		}
		if (!IsStringNotNull(source) || !IsStringNotNull(newcode)) {
			return string;
		}
		try {
			return new String(string.getBytes(source), newcode);
		} catch (UnsupportedEncodingException ex) {

		}
		return "";
	}

	/**
	 * 处理Properties
	 * 
	 * @param config
	 *                   配置项集合
	 * @return 返回key-value集合
	 */
	public static Map<String, String> getProperties(Properties config) {
		Map<String, String> hashtable = new HashMap<String, String>();
		if (config != null) {
			for (Enumeration<Object> e = config.keys(); e.hasMoreElements();) {
				Object obj = e.nextElement();
				if (hashtable.containsKey(obj.toString())) {
					hashtable.remove(obj.toString());
				}
				hashtable.put(obj.toString(), config.getProperty(obj.toString()));
				// log.info(obj.toString() + " ... " +
				// config.getProperty(obj.toString()));
			}
		}
		return hashtable;
	}

	/**
	 * 获取URL中的域名
	 * 
	 * @param url地址
	 * @return 域名
	 */
	public static String GetDomain(String url) {
		if (!IsStringNotNull(url)) {
			return "";
		}
		try {
			int i = url.indexOf("://");
			if (i >= 0) {
				url = url.substring(i + 3, url.length());
			}
			i = url.indexOf("/");
			if (i >= 0) {
				url = url.substring(0, i);
			}
			if (!IsStringNotNull(url)) {
				return "";
			}
			if (url.split("[.]").length == 2) {
				i = url.indexOf(".");
				if (i >= 0) {
					url = url.substring(i + 1, url.length());
				}
			}
		} catch (Exception e) {

			return "";
		}
		return url;
	}

	/**
	 * 截取字符 一个中文为2个字节 其他1个字节
	 * 
	 * @param source
	 *                       原始字符串
	 * @param maxByteLen
	 *                       截取的字节数
	 * @return 截取后的字符串
	 */
	public static String leftStr(String source, int maxByteLen) {
		if (!IsStringNotNull(source) || maxByteLen <= 0) {
			return "";
		}
		source = source.trim();
		if (source.getBytes().length <= maxByteLen) {
			return source;
		}
		int k = 0;
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			byte[] b = (source.charAt(i) + "").getBytes();
			k += b.length;
			if (k > maxByteLen) {
				break;
			}
			temp.append(source.charAt(i));
		}
		return temp.toString();
	}

	/**
	 * 同leftStr
	 * 
	 * @param source
	 * @param maxByteLen
	 * @return
	 * @see leftStr
	 * @deprecated
	 */
	public static String SubleftStr(String source, int maxByteLen) {
		return leftStr(source, maxByteLen);
	}

	/**
	 * 返回的值中 html状态是否是200，检查hash中是否含有key="msg"，并且值="200"
	 * 
	 * @param hash
	 * @return 是否为200 private static final String MSG = "msg"; private static final
	 *         String RESULT = "result"; private static final String STATUS =
	 *         "status";
	 */
	public static boolean HtmlReturnIsdel(Map<String, String> hash) {
		if (hash == null || hash.size() == 0) {
			return false;
		}
		if (hash.containsKey("STATUS") && "1".equals(hash.get("STATUS")) && hash.containsKey("MSG")) {
			return "200".equals(hash.get("MSG").toString().trim());
		}
		return false;
	}

	/**
	 * 验证参数是否合法 长度按字节计算
	 * 
	 * @param valueS
	 *                       要验证的值
	 * @param minlength
	 *                       最小长度
	 * @param maxlengthI
	 *                       最大长度
	 * @param must
	 *                       true 必填
	 * @param istrim
	 *                       true 需要去空
	 * @param isbytes
	 *                       true 计算字节长度
	 * @return 是否在长度范围内
	 */
	public static boolean ValidParameterValueLength(String valueS, int minlength, int maxlengthI, boolean must, boolean istrim, boolean isbytes) {
		if (istrim ? !IsStringNotNull(valueS, true) : !IsStringNotNull(valueS)) {// 空
			return must ? false : true;
		}
		if (istrim)
			valueS = valueS.trim();
		int lengthI = isbytes ? valueS.getBytes().length : valueS.length();
		if (minlength > 0 && lengthI < minlength)
			return false;
		return lengthI <= maxlengthI;
	}

	/**
	 * 验证参数是否合法
	 * 
	 * @param valueS
	 *                       要验证的值
	 * @param maxlengthI
	 *                       最大长度
	 * @param must
	 *                       true 必填
	 * @param istrim
	 *                       true 需要去空
	 * @return 是否合法
	 */
	public static boolean ValidParameterValueLength(String valueS, int minlength, int maxlengthI, boolean must, boolean istrim) {
		return ValidParameterValueLength(valueS, minlength, maxlengthI, must, istrim, false);
	}

	/**
	 * 验证参数是否合法
	 * 
	 * @param valueS
	 *                       要验证的值
	 * @param maxlengthI
	 *                       最大长度
	 * @param must
	 *                       true 必填
	 * @param istrim
	 *                       true 需要去空
	 * @return 是否合法
	 */
	public static boolean ValidParameterValueLength(String valueS, int maxlengthI, boolean must, boolean istrim) {
		return ValidParameterValueLength(valueS, 0, maxlengthI, must, istrim);
	}

	/**
	 * 验证参数长度
	 * 
	 * @param value
	 *                   参数值
	 * @param len
	 *                   允许长度
	 * @param isnull
	 *                   是否允许空
	 * @return
	 */
	public static boolean ValidParameterValueLength(String value, int len, boolean isnull) {
		return ValidParameterValueLength(value, len, isnull, true);

	}

	/**
	 * 字符串转成int整数，如果值为空，或转换失败，返回0
	 * 
	 * @param value
	 *                  源字符串
	 * @return 数字
	 */
	public static int StringToInt(String value) {
		if (!IsStringNotNull(value))
			return 0;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException num) {
		}
		return 0;
	}

	//
	// /**
	// * 生成随机字符串(英数字)
	// *
	// * @param len
	// * 长度
	// * @return 随机串
	// */
	// public static String getRandomStr(int len) {
	// StrRandom rand = new StrRandom();
	// rand.setCharset("a-zA-Z0-9");
	// rand.setLength(len);
	// try {
	// rand.generateRandomObject();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return rand.getRandom().toString();
	// }

	/**
	 * 按分隔符分割字符串
	 * 
	 * @param s
	 *                      待分割的字符串
	 * @param delimiter
	 *                      分隔符
	 * @return 分割后的字符数组
	 */
	public static String[] split(String s, String delimiter) {
		if (s == null) {
			return null;
		}
		int delimiterLength;
		int stringLength = s.length();
		if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {
			return new String[] { s };
		}

		// a two pass solution is used because a one pass solution would
		// require the possible resizing and copying of memory structures
		// In the worst case it would have to be resized n times with each
		// resize having a O(n) copy leading to an O(n^2) algorithm.

		int count;
		int start;
		int end;

		// Scan s and count the tokens.
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			count++;
			start = end + delimiterLength;
		}
		count++;

		// allocate an array to return the tokens,
		// we now know how big it should be
		String[] result = new String[count];

		// Scan s again, but this time pick out the tokens
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			result[count] = (s.substring(start, end));
			count++;
			start = end + delimiterLength;
		}
		end = stringLength;
		result[count] = s.substring(start, end);

		return (result);
	}

	/**
	 * 字符串按字节截取
	 * 
	 * @param str
	 *                原字符
	 * @param len
	 *                截取长度
	 * @return String
	 * @author kinglong
	 * @since 2006.07.20
	 */
	public static String subString_Bases(String str, int len) {
		return subString_Bases(str, len, "...");
	}

	public static String subStr(String str, int len) {
		if (str.length() < len)
			return str;
		return str.substring(0, len);
	}

	/**
	 * 字符串按字节截取
	 * 
	 * @param str
	 *                  原字符
	 * @param len
	 *                  截取长度
	 * @param elide
	 *                  省略符
	 * @return String
	 * @author kinglong
	 * @since 2006.07.20
	 */
	public static String subString_Bases(String str, int len, String elide) {
		if (str == null)
			return "";
		byte[] strByte = str.getBytes();
		int strLen = strByte.length;
		int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;
		if (len >= strLen || len < 1)
			return str;
		if (len - elideLen > 0)
			len = len - elideLen;
		int count = 0;
		for (int i = 0; i < len; i++) {
			int value = (int) strByte[i];
			if (value < 0)
				count++;
		}
		if (count % 2 != 0)
			len = (len == 1) ? len + 1 : len - 1;
		return new String(strByte, 0, len) + elide.trim();
	}

	// --------------------------------------------------------------
	/**
	 * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
	 * 
	 * @param str
	 *                               原始字符串
	 * @param srcPos
	 *                               开始位置
	 * @param specialCharsLength
	 *                               截取长度(汉、日、韩文字符长度为2)
	 * @return
	 */
	public static String cutMultibyte(String str, int specialCharsLength, String elide) {
		int srcPos = 0;
		if (str == null || "".equals(str) || specialCharsLength < 1) {
			return "";
		}
		if (srcPos < 0) {
			srcPos = 0;
		}
		if (specialCharsLength <= 0) {
			return "";
		}
		// 获得字符串的长度
		char[] chars = str.toCharArray();
		if (srcPos > chars.length) {
			return "";
		}
		int charsLength = getCharsLength(chars, specialCharsLength);
		return new String(chars, srcPos, charsLength) + (specialCharsLength > str.length() ? elide : "");
	}

	/**
	 * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
	 * 
	 * @param chars
	 *                               一段字符
	 * @param specialCharsLength
	 *                               输入长度，汉、日、韩文字符长度为2
	 * @return 输出长度，所有字符均长度为1
	 */
	private static int getCharsLength(char[] chars, int specialCharsLength) {
		int count = 0;
		int normalCharsLength = 0;
		for (int i = 0; i < chars.length; i++) {
			int specialCharLength = getSpecialCharLength(chars[i]);
			if (count <= specialCharsLength - specialCharLength) {
				count += specialCharLength;
				normalCharsLength++;
			} else {
				break;
			}
		}
		return normalCharsLength;
	}

	/**
	 * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
	 * 
	 * @param c
	 *              字符
	 * @return 字符长度
	 */
	private static int getSpecialCharLength(char c) {
		if (isLetter(c)) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param c
	 *              需要判断的字符
	 * @return 返回true,Ascill字符
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	public static String identityCenterReplaceStr(String value, int start, int length) {
		if (!IsStringNotNull(value))
			return "";
		int vlength = value.length();
		if (vlength - start < length)
			length = vlength - start;
		if (length <= 0)
			return value;
		if (start <= 0)
			start = 1;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value.substring(0, start));
		for (int i = 0; i < length; i++)
			stringBuilder.append("*");
		stringBuilder.append(value.subSequence(start + length, vlength));
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		System.out.println(identityCenterReplaceStr("330721197701122931", 8, 5));
		// 33072*****01122931
		// String aString="2101.1";
		// System.out.println(String.format("ad%1$0", 0.1));
		// DecimalFormat fmt = new DecimalFormat("#############0.00");
		// String outStr = null;
		// double d;
		// try {
		// d = Double.parseDouble(aString);
		// outStr = fmt.format(d);
		// System.out.println(outStr);
		// } catch (Exception e) {
		// }

		// System.out.println(BigDecimalUtils.yuanFormat(aString));
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	// public static String getRemoteAddr(HttpServletRequest request) {
	// String ip = request.getRemoteAddr();
	// if ("127.0.0.1".equals(ip)) {
	// String x = request.getHeader("x-forwarded-for");
	// ip = x == null ? ip : x;
	// }
	// return SplitIp(ip);
	// }

	/**
	 * 从IP列表中获取第1个IP
	 * 
	 * @param ip
	 * @return
	 */
	public static String SplitIp(String ip) {
		if (!IsStringNotNull(ip)) {
			return ip;
		}
		String[] ipString = ip.split("[,]");
		if (ipString.length > 1) {
			return ipString[0].toString();
		}
		return ip;
	}

	/**
	 * 获取时间在日期中的位置
	 * 
	 * @Title: parseValidDate @Description: @param @param
	 *         create @param @return @return String @throws
	 */
	public static String parseValidDate(long create) {
		try {
			String ret = "";
			Calendar now = Calendar.getInstance();
			long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));// 毫秒数
			long ms_now = now.getTimeInMillis();
			if (ms_now - create < ms) {
				return "今天";
			} else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
				return "昨天";
			} else if (ms_now - create < (ms + 24 * 3600 * 1000 * 2)) {
				return "前天";
			} else {
				return "更早";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String parseValidDate(String createTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			long create = sdf.parse(createTime).getTime();
			return parseValidDate(create);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *                   字符串日期
	 * @param format
	 *                   如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp(String date_str) {
		try {
			String format = "yyyyMMdd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return (sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: date2TimeStamp @Description: 日期格式字符串转换成时间戳 @param: @param dateFormat
	 *         时间戳格式 @param: @param date_str 字符串日期 @param: @return @return:
	 *         long @throws
	 */
	public static long date2TimeStamp(String dateFormat, String date_str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return (sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String intToIp(int i) {
		return ((i >> 24) & 0xFF) + "." +

				((i >> 16) & 0xFF) + "." +

				((i >> 8) & 0xFF) + "." +

				(i & 0xFF);

	}
	
	/**
	 * 
	 * @Title: ip2String
	 * @Description: ipv4,ipv6 的地址转换成 数字
	 * @param ip
	 * @return String
	 * @throws
	 */
	public static String ip2String(String ip) {
		String result = "";
		if (ip.indexOf(".") > -1 ) {
			result = ipToLong(ip) + "";
			return result;
		}
		
		if (ip.indexOf(":") == -1) {
			return "0";
		}
		
		result = completeIpv6(ip);
		return result;
	}
	
	/**将精简的ipv6地址扩展为全长度的ipv6地址
    *
    */
   public static String completeIpv6(String strIpv6){
       BigInteger big = ipv6ToInt(strIpv6);
       String str = big.toString(16);
       String completeIpv6Str = "";
       while(str.length() != 32){
           str = "0" + str;
       }
       for (int i = 0; i <= str.length(); i += 4){
           completeIpv6Str += str.substring(i, i + 4);
           if ((i + 4) == str.length()){
               break;
           }
//           completeIpv6Str += ":";
       }
       return completeIpv6Str;
   } 
   
   /**
    * 
    * @Title: ipv6ToInt
    * @Description: ipv6字符串转BigInteger数
    * @param ipv6
    * @return BigInteger
    * @throws
    */
   public static BigInteger ipv6ToInt(String ipv6) {
	   int compressIndex = ipv6.indexOf("::");
	   if (compressIndex != -1) {
		   String part1s = ipv6.substring(0, compressIndex);
		   String part2s = ipv6.substring(compressIndex + 1);
		   BigInteger part1 = ipv6ToInt(part1s);
		   BigInteger part2 = ipv6ToInt(part2s);
		   int part1hasDot = 0;
		   char ch[] = part1s.toCharArray();
		   for (char c : ch) {
			   if (c == ':') {
				   part1hasDot++;
			   }
		   }
		   return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
	   }
	   String[] str = ipv6.split(":");
	   BigInteger big = BigInteger.ZERO;
	   for (int i = 0; i < str.length; i++) {
			// ::1
		   if (str[i].isEmpty()) {
			   str[i] = "0";
		   }
		   big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16)).shiftLeft(16 * (str.length - i - 1)));
	   }
	   return big;
   }

	public static Long ipToLong(String addr) {
		String[] addrArray = addr.split("\\.");

		long num = 0;

		for (int i = 0; i < addrArray.length; i++) {

			int power = 3 - i;

			num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));

		}

		return num;

	}

	/**
	 * 将字符串型ip转成int型ip
	 * 
	 * @param strIp
	 * @return
	 */
	public static int Ip2Int(String strIp) {
		String[] ss = strIp.split("\\.");
		if (ss.length != 4) {
			return 0;
		}
		byte[] bytes = new byte[ss.length];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(ss[i]);
		}
		return byte2Int(bytes);
	}

	private static int byte2Int(byte[] bytes) {
		int n = bytes[0] & 0xFF;
		n |= ((bytes[1] << 8) & 0xFF00);
		n |= ((bytes[2] << 16) & 0xFF0000);
		n |= ((bytes[3] << 24) & 0xFF000000);
		return n;
	}

	/**
	 * 判断身份证号码格式是否正确
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isValidIdcard(String idcard) {
		String check = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		if (idcard.matches(check)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isRealname(String value) {
		String regex = "^[\\u4e00-\\u9fa5]+$";
		if (value.matches(regex)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: matcher @Description: 正则表达式匹配规则 @param: @param source @param: @param
	 *         pattern @param: @return @return: String @throws
	 */
	public static String matcher(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 
	 * @Title: interval_time @Description: 时间换算 @param: @param time
	 *         时间 @param: @param type 转换的类型 @param: @return @return: String @throws
	 */
	public static String interval_time(int time, int type) {
		int days = 0;
		int hour = (int) Math.floor((time - 86400 * days) / 3600);
		int minute = (int) Math.floor((time - 86400 * days - 3600 * hour) / 60);
		int second = time - 86400 * days - 3600 * hour - 60 * minute;
		String str = "";
		if (type == 1) {
			str += hour == 0 ? "00:" : fillingString(String.valueOf(hour), 2, "0") + ":";
			str += minute == 0 ? "00" : fillingString(String.valueOf(minute), 2, "0");
		} else {
			str += days == 0 ? "" : days + "天";
			str += hour == 0 ? "" : hour + "小时";
			str += minute == 0 ? "" : minute + "分";
			str += second == 0 ? "" : second + "秒";
		}

		return IsStringNotNull(str) ? str : "0";
	}

	/**
	 * 
	 * @Title: fillingString @Description: 填充字符串 @param: @param num
	 *         要填充的字符串 @param: @param length 填充后字符串的长度 @param: @param fillString
	 *         填充使用的字符串 @param: @return @return: String @throws
	 */
	public static String fillingString(String originalString, int length, String fillString) {
		String string = "";
		// 如果原字符串长度大于填充后的长度，使用原字符串
		if (originalString.length() > length) {
			return originalString;
		}
		for (int i = 0; i < length; i++) {
			string += fillString;
		}

		String result = string.subSequence(0, length - originalString.length()) + originalString;
		return result;
	}

	/**
	 * 
	 * @Title: replaceString @Description: 字符串指定位置替换指定字符串 @param: @param oriString
	 *         原始字符串 @param: @param replacement 要替换的字符串 @param: @param start
	 *         开始替换的位置 @param: @param stringSize 替换的长度 @param: @return @return:
	 *         String @throws
	 */
	public static String replaceString(String oriString, String replacement, int start, int stringSize) {
		StringBuffer buffer = new StringBuffer(oriString);
		return buffer.replace(start, start + stringSize, replacement).toString();
	}

	/**
	 * md5加密 http://blog.csdn.net/rongwenbin/article/details/42462441
	 * 
	 * @param inputStr
	 *                     需要加密的字符串
	 * @return
	 */
	public static String getMD5Str(String inputStr) {
		String md5Str = inputStr;
		try {
			if (inputStr != null) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(inputStr.getBytes());
				BigInteger hash = new BigInteger(1, md.digest());
				md5Str = hash.toString(16);
				// if ((md5Str.length() % 2) != 0) {
				// md5Str = "0" + md5Str;
				// }
				// 2018-08-18 加密修改
				while (md5Str.length() < 32) {
					md5Str = "0" + md5Str;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5Str;
	}
	
	private static String salt="哇哈哈";
	public static void  setSalt(String customSalt) {
		salt=customSalt;
	}
	/**
	 * 
	 * @Title:新MD5加密
	 * @Description: TODO
	 * @param password
	 * @param salt
	 * @return  
	 * @return String
	 * @throws
	 */
	public static String md5Encode(String password) {
		return md5Encode(password,salt);
	}
	/**
	 * 
	 * @Title:新MD5加密
	 * @Description: TODO
	 * @param password
	 * @param saltValue
	 * @return  
	 * @return String
	 * @throws
	 */
	public static String md5Encode(String password,String saltValue) {
		password = password + saltValue;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 生成指定范围的随机数 @Title: getRandom @Description: TODO @param: @param min
	 * 随机数对应的最小值 @param: @param max 随机数对应的最大值 @param: @return @return: int @throws
	 */
	public static int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * 获取20位时间随机数 @Title: getRandomCode @Description: TODO @param: @return @return:
	 * String @throws
	 */
	public static String getRandomCode() {
		String result = transferLongToDate("yyyyMMddHHmmss", System.currentTimeMillis()) + getRandom(111111, 999999);
		return result;
	}

	/**
	 * @param str
	 *                  the String to get the substring from, may be null
	 * @param start
	 *                  the position to start from, negative means count back from
	 *                  the end of the String by this many characters
	 * @return substring from start position, <code>null</code> if null String input
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return "";
		}

		return str.substring(start);
	}

	/**
	 * 昵称是否包含特殊字符 @Title: isUsableNickname @Description: TODO @param: @param
	 * nickname @param: @return @return: boolean @throws
	 */
	public static boolean isUsableNickname(String nickname) {
		boolean result = false;
		String regex = "^[\\x{4e00}-\\x{9fa5}A-Za-z0-9_]+$";
		if (nickname.matches(regex)) {
			result = true;
		}
		return result;

	}

	/**
	 * 获取当前日期对应的是星期几 @Title: getWeek @Description: TODO @param: @return @return:
	 * int @throws
	 */
	public static int getWeek() {
		int result = 7;
		try {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	/**
	 * 两数相除保留小数位数 @Title: division @Description: TODO @param: @param
	 * amount @param: @param divisor @param: @param format @param: @return @return:
	 * String @throws
	 */
	public static String division(double amount, double divisor, String format) {
		String result = "";
		try {
			double num = (double) amount / divisor;
			DecimalFormat decimalFormat = new DecimalFormat(format);
			result = decimalFormat.format(num);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回一个字符随机数，种子可能只包含小写英数字，各英数字字符出现的概率可能不相同 @Title: getLetterRandom @Description:
	 * TODO @param: @param length 随机数长度 @param: @return @return: String @throws
	 */
	public static String getLetterRandom(int length) {
		return generateString(length, StrChar);
	}

	/**
	 * 获取随机字符串 @Title: generateString @Description: TODO @param: @param length
	 * 随机数长度 @param: @param RandomChar 随机串，最终从中提取字符组成结果 @param: @return @return:
	 * String 随机串 @throws
	 */
	public static String generateString(int length, String RandomChar) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int CharLen = RandomChar.length();
		for (int i = 0; i < length; i++)
			sb.append(RandomChar.charAt(random.nextInt(CharLen)));
		return sb.toString();
	}

	/**
	 * 将url表单形式转为map @Title: Split @Description: TODO @param: @param urlparam
	 * url表单形式 @param: @return @return: Map<String,String> @throws
	 */
	public static Map<String, String> SplitUrl(String urlparam) {
		Map<String, String> map = new HashMap<String, String>();
		String[] param = urlparam.split("&");
		for (String keyvalue : param) {
			String[] pair = keyvalue.split("=");
			if (pair.length == 2) {
				map.put(pair[0], pair[1]);
			}
		}
		return map;
	}

	/**
	 * 根据文件名获取文件后缀 @Title: getFileSuffix @Description: TODO @param: @param fileName
	 * 文件名 @param: @return @return: String @throws
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : ".jpg";

	}

	/**
	 * 接收curl post请求 @Title: getRequestStr @Description: TODO @param: @param
	 * request @param: @return @param: @throws Exception @return: String @throws
	 */
	public static String getRequestStr(HttpServletRequest request) {
		String string = null;
		try {
			return dealResponseResult(request.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 获取REQUEST body @Title: dealResponseResult @Description: TODO @param: @param
	 * inputStream @param: @return @return: String @throws
	 */
	public static String dealResponseResult(InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (byteArrayOutputStream != null)
			return new String(byteArrayOutputStream.toByteArray());
		return null;
	}

	/**
	 * 将list转换成String @Title: listToString @Description: TODO @param: @param
	 * list @param: @return @return: String @throws
	 */
	public static String listToString(List<String> list) {
		if (list == null)
			return null;
		StringBuilder stringBuilder = new StringBuilder();
		boolean first = true;
		// 第一个前面不拼接","
		for (String result : list) {
			if (first) {
				first = false;
			} else {
				stringBuilder.append(",");
			}
			stringBuilder.append(result);
		}
		return stringBuilder.toString();
	}

	/**
	 * 判断数组下标是否溢出 @Title: isArrayOverflow @Description: TODO @param: @param
	 * arraySubscript 数组下标 @param: @param arraylength 数组长度 @param: @return @return:
	 * boolean @throws
	 */
	public static boolean isArrayOverflow(int arraySubscript, int arrayLength) {
		return arraySubscript >= arrayLength ? false : true;
	}

	/**
	 * 保留指定位数的小数 @Title: getDecimal @Description: TODO @param: @param number
	 * 输入数字 @param: @param digit 保留的位数 @param: @return @return: float @throws
	 */
	public static float getDecimal(float number, int digit) {
		BigDecimal bigDecimal = new BigDecimal(number);
		return bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	// 两个double 进行相加
	public static double add(String v1, String v2) {
		// BigDecimal b1 = new BigDecimal(Double.toString(v1));
		// BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b1 = new BigDecimal(Double.valueOf(v1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(v2));
		return b1.add(b2).doubleValue();
	}

//	public static String getActionTime(long time) {
//		if (time == 0)
//			return DateUtils.format(new Date(), "yyyyMMdd");
//		return DateUtils.parseLongToDate(time * 1000, "yyyyMMdd");
//	}
	
	/**
	 * 
	 * @Title: endLastWeek
	 * @Description: 获取上周开始时间
	 * @return long
	 * @throws
	 */
	public static long endLastWeek() {
		LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        long longtimestamp = monday.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        
        return longtimestamp;
	}
	
	/**
	 * 
	 * @Title: getBeginLastWeek
	 * @Description: 获取本周开始时间
	 * @return long
	 * @throws
	 */
	public static long getBeginLastWeek() {
		LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        monday = monday.minusWeeks(1);
        long longtimestamp = monday.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        
        return longtimestamp;
	}
	
	//判断当天日期 与以往日期相隔天数
    public static int distanceDay(Date largeDay, Date smallDay) {
        int day = (int) ((largeDay.getTime() - smallDay.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }
    
    /**
     * 
     * @Title: getTime
     * @Description: 获取当天的指定时间
     * @param hour
     * @param min
     * @param second
     * @return
     * @return: int
     * @throws
     */
    public static int getTime(int hour,int min,int second) {
        Calendar cal = new GregorianCalendar();
//      cal.set(Calendar.MONTH, 6);
//      cal.set(Calendar.DATE, 19);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        return (int)(cal.getTimeInMillis() / 1000);
    }
    
    /**
     * long 转 时间字符串
     * 
     * @Title: transferLongToDate @Description: @param @param
     *         millSec @param @return @return String @throws
     */
    public static String transferLongToDate(Long millSec) {
        if (millSec == 0)
            return "";
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        return transferLongToDate(dateFormat, millSec);
    }
    
    /**
     * 对应php date('Ymd', $starttime)
     * 
     * @Title: transferLongToDate
     * @Description: TODO
     * @param dateFormat
     * @param millSec
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec*1000);
        return sdf.format(date);
    }
    
    /**
     * 2019-08-25T00:00:00.000+0000 转为时间
     * @Title: covnDate
     * @Description: TODO
     * @param dateTime
     * @return String
     * @throws
     */
    public static String zoneToLocalTimeStr(Date date) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = simpleDateFormat.format(date);
        return dt;
    }
}
