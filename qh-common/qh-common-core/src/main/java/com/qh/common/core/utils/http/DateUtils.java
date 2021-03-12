package com.qh.common.core.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化（线程安全）
 *
 * @author mds
 * @DateTime 2017年5月9日 下午6:12:25
 */
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    // 各种时间格式
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String MM_dd = "MM-dd";
    public static final String MMdd = "MMdd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String cn_yyyy_MM_dd = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String HH_mm = "HH:mm";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String yy_MM_dd_HH_mm_ss = "yy-MM-dd HH:mm:ss";

    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String MM_dd_HH_mm = "MM-dd HH:mm";

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<String, SimpleDateFormat>();
        }
    };

    /**
     * @param date
     * @return int
     * @throws
     * @Title: 时间转为秒时间戳
     * @Description: TODO
     */
    public static int dateToInt(Date date) {
        return (int) (date.getTime() / 1000);
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
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return (sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param endDate
     * @param nowDate
     * @return
     * @author mds
     * @DateTime 2019年6月1日 下午3:25:00
     * @serverComment 两个时间相差的分钟数
     */
    public static long findDateDiff(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        if (endDate == null || nowDate == null) {
            return 0;
        }
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        return min;
    }

    /**
     * 计算出startDate至endDate的日期范围
     *
     * @param startDate 开始日期，格式yyyy-MM-dd
     * @param endDate   结束日期，格式yyyy-MM-dd
     * @return 返回 日期范围集合
     * @author Loren
     * @DateTime 2018年8月3日 下午2:57:26
     */
    public static Set<Date> dateRange(String startDate, String endDate) {
        Set<Date> dates = new HashSet<>();
        Date start = parse(startDate, DateUtils.yyyy_MM_dd);
        Date end = parse(endDate, DateUtils.yyyy_MM_dd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (start.getTime() <= end.getTime()) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            start = calendar.getTime();
        }
        return dates;
    }


    /**
     * @param specifiedDay 输入格式  2019-01-01
     * @return
     * @author zhangzhf
     * @DateTime 2019年4月26日 上午17:18:58
     * @serverComment 返回特定某天的前一天
     */
    public static String getOneDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }


    /**
     * @param specifiedDay 输入格式  2019-01-01
     * @return
     * @author zhangzhf
     * @DateTime 2019年4月26日 上午17:18:58
     * @serverComment 返回特定某天的前n天的那一天
     */
    public static String getDayBeforeNum(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - num);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }


    /**
     * @param specifiedDay 输入格式  2019-01-01
     * @return
     * @author zhangzhf
     * @DateTime 2019年4月26日 上午17:18:58
     * @serverComment 返回特定某天的后一天
     */
    public static String getOneDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }


    /**
     * @param specifiedDay 输入格式  2019-01-01
     * @return
     * @author zhangzhf
     * @DateTime 2019年4月26日 上午17:18:58
     * @serverComment 获取一个星期的第几天     0 星期天  1星期1 ***
     */
    public static int getWeek(String strDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK) - 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *      * 返回当前时间到今天结束还有多少秒
     *      *
     *      * @return 秒
     *     
     */
    public static int subSecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        long d1 = cal.getTime().getTime();
        long d2 = new Date().getTime();
        int second = (int) ((d1 - d2) / 1000);

        if (second <= 0) {
            second = 1;
        }

        return second;
    }


    /**
     * @param specifiedDay 输出格式  2019-01-01
     * @return
     * @author zhangzhf
     * @DateTime 2019年5月14日 上午17:18:58
     * @serverComment 获取当前月的第一天
     */
    public static String getThisMonthFirstDay() {
        Calendar cale = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }


    /**
     * @param beginDate endDate
     * @return
     * @author zhangzhf
     * @DateTime 2019年5月14日 上午17:18:58
     * @serverComment 获取两个日期之间的分钟差
     */
    public static long getMinutesBetweenTwoDate(Date beginDate, Date endDate) {
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        long diff = (endTime - beginTime) / 1000 / 60;
        return diff;
    }

    /**
     * 获得相对于今天的0点
     *
     * @param offset 相对于今天的偏移
     * @return
     * @author ljs
     * @DateTime 2019年8月27日 下午9:56:19
     */
    public static Date getDayStart(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

    /**
     * 获得相对于今天的的23:59
     *
     * @return
     * @author ljs
     * @DateTime 2019年8月27日 下午9:56:25
     */
    public static Date getDayEnd(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(getDayStart(0)));
        System.out.println(sdf.format(getDayEnd(0)));
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(getDayTime(sdft.parse("09:22:00"), -1)));
        System.out.println(getYesterdayStr("2019-08-08"));
        System.out.println(getLastWeekStratStr("2019-08-08"));
        System.out.println(getLastWeekEndStr("2019-08-08"));
        System.out.println(getLastMonthStartStr("2019-08-08"));
        System.out.println(getLastMonthEndStr("2019-08-08"));

        System.out.println(dateAddTime(new Date(), sdft.parse("13:33:00")));
    }

    /**
     * 只有time的部分,合并成datetime
     *
     * @param time
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午2:59:06
     */
    public static Date getDayTime(Date time, int offset) {
        Calendar old = Calendar.getInstance();
        old.setTime(time);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, old.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, old.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, old.get(Calendar.SECOND));

        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

    public static Date getDateTime(Date day, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);

        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

    /**
     * 获取昨天字符串
     *
     * @param now
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午2:51:35
     */
    public static String getYesterdayStr(String now) {
        Date today = parse(now, yyyy_MM_dd);
        Date yesterday = getDateTime(today, -1);
        return format(yesterday, yyyy_MM_dd);
    }

    /**
     * 上周开始日期
     *
     * @param tagDay
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午4:07:48
     */
    public static String getLastWeekStratStr(String tagDay) {
        Date today = parse(tagDay, yyyy_MM_dd);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_WEEK, 2);
        c.add(Calendar.DATE, -7);

        return format(c.getTime(), yyyy_MM_dd);
    }

    /**
     * 上周结束日期
     *
     * @param tagDay
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午4:11:35
     */
    public static String getLastWeekEndStr(String tagDay) {
        Date today = parse(tagDay, yyyy_MM_dd);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_WEEK, 2);
        c.add(Calendar.DATE, -1);

        return format(c.getTime(), yyyy_MM_dd);
    }

    /**
     * 上月开始时间
     *
     * @param tagDay
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午4:12:11
     */
    public static String getLastMonthStartStr(String tagDay) {
        Date today = parse(tagDay, yyyy_MM_dd);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, -1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

        return format(c.getTime(), yyyy_MM_dd);
    }

    /**
     * 上月结束时间
     *
     * @param tagDay
     * @return
     * @author ljs
     * @DateTime 2019年8月28日 下午4:16:28
     */
    public static String getLastMonthEndStr(String tagDay) {
        Date today = parse(tagDay, yyyy_MM_dd);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, -1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH));

        return format(c.getTime(), yyyy_MM_dd);
    }

    /**
     * 合并日期和时间
     *
     * @param date
     * @param time
     * @return
     * @author ljs
     * @DateTime 2019年8月30日 下午5:58:40
     */
    public static Date dateAddTime(Date date, Date time) {
        Calendar timec = Calendar.getInstance();
        timec.setTime(time);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, timec.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, timec.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, timec.get(Calendar.SECOND));

        return cal.getTime();
    }

    /**
     * @param timeMillis
     * @return
     * @author mds
     * 转换为时间（天,时:分:秒.毫秒）
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }


    /**
     * @param @param smdate
     * @param @param bdate
     * @Title: daysBetween
     * @Description: 获取两个日期的时间差多少天
     * @author zhangzhf
     * @date 2019年9月30日
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 将val月 格式化为 x年y月
     *
     * @param val
     * @return
     * @author ljs
     * @DateTime 2019年10月11日 下午12:23:16
     */
    public static String formatMonth(int val) {
        StringBuffer sb = new StringBuffer();
        if (val >= 12) {
            int year = val / 12;
            int month = val % 12;
            sb.append(year).append("年");
            if (month != 0) {
                sb.append(month).append("个月");
            }
        } else {
            sb.append(val).append("个月");
        }
        return sb.toString();
    }

    /**
     * toDate - fromDate 差多少个月
     *
     * @param fromDate
     * @param toDate
     * @return
     * @author ljs
     * @DateTime 2019年10月17日 下午4:52:13
     */
    public static int monthsBetween(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth) + 1;
        return month;
    }

    /**
     * 结束时间
     * 例子：2020-08-12 23:59:59
     *
     * @param dateTime 日期
     * @return
     */
    public static long dateLastTime(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
            Date date = sdf.parse(dateTime);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            cal.set(Calendar.MILLISECOND, 999);
            return cal.getTime().getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 开始时间
     * 例子：2020-08-12 00:00:00
     *
     * @param dateTime 日期
     * @return
     */
    public static long dateFirstTime(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
            Date date = sdf.parse(dateTime);

            // 获取当前日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime().getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}