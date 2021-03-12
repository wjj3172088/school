package com.qh.common.core.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化（线程安全）
 *
 * @author mds
 * @DateTime 2017年5月9日 下午6:12:25
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    // 各种时间格式
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String MM_dd = "MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String cn_yyyy_MM_dd = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String HH_mm = "HH:mm";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yy_MM_dd_HH_mm_ss = "yy-MM-dd HH:mm:ss";

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static ThreadLocal<Map<String, SimpleDateFormat>> sdfMap =
            new ThreadLocal<Map<String, SimpleDateFormat>>() {
                @Override
                protected Map<String, SimpleDateFormat> initialValue() {
                    return new HashMap<String, SimpleDateFormat>();
                }
            };

    /**
     * 获取系统秒时间 @Title: getSystemSeconds @Description: TODO @param: @return @return: long @throws
     */
    public static int getSystemSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 返回一个SimpleDateFormat,每个线程只会new一次pattern对应的sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        Map<String, SimpleDateFormat> tl = sdfMap.get();
        SimpleDateFormat sdf = tl.get(pattern);
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            // 这个的功能是不把1996-13-3 转换为1997-1-3
            sdf.setLenient(false);
            tl.put(pattern, sdf);
        }
        return sdf;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @author mds
     * @DateTime 2017年5月10日 上午9:18:58
     * @serverComment 日期转string
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    /**
     * @param dateStr
     * @param pattern
     * @return
     * @author mds
     * @DateTime 2017年5月10日 上午9:18:23
     * @serverComment string 字符串转日期
     */
    public static Date parse(String dateStr, String pattern) {
        Date _date = null;
        try {
            _date = getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return _date;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @author mds
     * @DateTime 2017年5月10日 上午9:18:58
     * @serverComment 日期转string(当前时间)
     */
    public static String format(String pattern) {
        Date date = new Date();
        return getSdf(pattern).format(date);
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
        res = String.valueOf(ts);
        return res;
    }

    /**
     * @param pattern
     * @param day     前后天数
     * @return
     * @author mds
     * @DateTime 2017年6月8日 下午3:36:22
     * @serverComment 获取明天的日期
     */
    public static String frontAndBackDay(String pattern, int day) {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date = calendar.getTime();
        return format(date, pattern);
    }

    /**
     * @param pattern
     * @param day
     * @return
     * @author lin
     * @DateTime 2017年12月15日 上午10:10:27
     * @serverComment 根据传过来的日期转成字符串
     */
    public static String dateToStrDay(String date, int day, String pattern) {
        Date datetime = parse(date, pattern);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(datetime);
        calendar.add(Calendar.DATE, day);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        datetime = calendar.getTime();
        return format(datetime, pattern);
    }

    /**
     * @param pattern
     * @param year    前后年数
     * @return
     * @author mds
     * @DateTime 2017年6月8日 下午3:37:35
     * @serverComment
     */
    public static String frontAndBackYear(String pattern, int year) {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        date = calendar.getTime();
        return format(date, pattern);
    }

    /**
     * 获取年
     *
     * @return String
     * @throws
     * @Title: getSysYear
     * @Description: TODO
     */
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    /**
     * @param pattern
     * @param year    前后年数
     * @return
     * @author lin
     * @DateTime 2017年12月15日 上午10:16:32
     * @serverComment
     */
    public static String dateToStrYear(String date, int year, String pattern) {
        Date datetime = parse(date, pattern);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(datetime);
        calendar.add(Calendar.YEAR, year);
        datetime = calendar.getTime();
        return format(datetime, pattern);
    }

    /**
     * 获取当天的开始时间 @Title: getDayBegin @Description: TODO @return java.util.Date @throws
     */
    public static long getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String s=df.format(cal.getTime());

        return cal.getTimeInMillis() / 1000;
    }

    public static String getDayBeginString() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = df.format(cal.getTime());

        return s;
    }

    /**
     * 获取当天的结束时间 @Title: getDayEnd @Description: TODO @return java.util.Date @throws
     */
    public static long getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String s=df.format(cal.getTime());
        return cal.getTimeInMillis() / 1000;
    }

    // 获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    // 获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * 将时间Date格式转换为时间戳 @Title: getTime @Description: TODO @param date @return long @throws
     */
    public static long getTime(Date date) {
        long time =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date.toString(), new ParsePosition(0)).getTime() / 1000;
        return time;
    }

    /**
     * 时间戳转换成字符串
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /**
     * 获取指定某一天的开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getDailyStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取指定某一天的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getDailyEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() / 1000;
    }

}