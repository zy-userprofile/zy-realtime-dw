package cn.zy.userprofile.realtime.dw.common.utils.date;


import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期相关工具类
 */
public class DateUtils {

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat DATE_SIGN_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat TIME_FORMAT = FastDateFormat.getInstance("yyyyMMdd HH:mm:ss");
    public static final FastDateFormat TIME_SIGN_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取无分隔符格式日期字符串
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMAT.format(date);
    }


    /**
     * 获取有分隔符格式日期字符串
     *
     * @param date
     * @return
     */
    public static String getDateSignFormat(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_SIGN_FORMAT.format(date);
    }


    /******** 日期对象和字符串互转 ********/

    /**
     * 日期格式转换--日期转字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return
     */
    public static String parseDateStr(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null || "".equals(format)) {
            throw new RuntimeException("This date format error!");
        }
        switch (format) {
            case "yyyyMMdd":
                return DATE_FORMAT.format(date);
            case "yyyy-MM-dd":
                return DATE_SIGN_FORMAT.format(date);
            default:
                return "";
        }
    }


    /**
     * 日期格式转换--字符串转日期
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        if (format == null || "".equals(format)) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 日期格式转换--不同格式之间转换
     *
     * @param oldDate
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String parseDateStr(String oldDate, String oldFormat, String newFormat) {
        if (oldFormat == null || "".equals(oldFormat) || newFormat == null || "".equals(newFormat)) {
            return null;
        }
        Date date = parseDate(oldDate, oldFormat);
        return parseDateStr(date, newFormat);
    }


    /**
     * 获取当前系统日期
     *
     * @return 日期字符串
     */
    public static String nowDate() {
        Date now = new Date();
        return DATE_FORMAT.format(now);
    }


    /**
     * 获取当前系统日期()
     *
     * @return 日期字符串
     */
    public static String nowDate(String format) {
        Date now = new Date();
        switch (format) {
            case "yyyyMMdd":
                return DATE_FORMAT.format(now);
            case "yyyy-MM-dd":
                return DATE_SIGN_FORMAT.format(now);
            default:
                return null;
        }
    }


    /**
     * 获取当前系统时间戳
     *
     * @return 时间戳(java.lang.Long类型)
     */
    public static Long nowUnixtime() {
        return new Date().getTime();
    }


    /**
     * 获取指定日期对应N天前(后)日期
     *
     * @param DateStr
     * @param format
     * @param amount
     * @return
     */
    public static String getNDay(String DateStr, String format, int amount) {
        if (format == null || "".equals(format)) {
            throw new RuntimeException("This date format error!");
        }
        Date date = parseDate(DateStr, format);
        Date resultDate = org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
        if (resultDate == null) {
            return null;
        }
        return parseDateStr(resultDate, format);
    }


    /**
     * 获取昨日日期
     *
     * @param format
     * @return
     */
    public static String yesterday(String format) {
        String today = nowDate(format);
        return getNDay(today, format, -1);
    }


    /**
     * 获取N天前(后)日期
     *
     * @param n n < 0  提前n天前   n > 0  提前n天后
     * @return
     */
    public static String getNDaySign(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, n);
        return DATE_SIGN_FORMAT.format(cal.getTime());
    }


    /******** 时间戳和字符串互转 ********/

    /**
     * 时间戳转时间字符串
     *
     * @param timestamp 时间戳毫秒值
     * @return 日期字符串值
     */
    public static String fromUnixtime(Long timestamp) {
        return TIME_SIGN_FORMAT.format(timestamp);
    }


    /**
     * 时间戳转时间字符串
     *
     * @param timestamp 时间戳毫秒值
     * @param format    日期格式
     * @return          日期字符串值
     */
    public static String fromUnixtime(Long timestamp, String format) {

        String result = "";

        try {
            FastDateFormat fastDateFormat = FastDateFormat.getInstance(format);
            result = fastDateFormat.format(timestamp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * 时间戳转时间字符串
     *
     * @param timestamp 时间戳毫秒值
     * @param format    日期格式
     * @return          日期字符串值
     */
    public static String fromUnixtime(Long timestamp, DateTimeFormat format) {

        String result = "";

        try {
            FastDateFormat fastDateFormat = FastDateFormat.getInstance(format.value());
            result = fastDateFormat.format(timestamp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * 字符串转时间戳
     * 默认时间格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr   日期字符串
     * @return          时间戳毫秒值
     */
    public static Long unixTimestamp(String dateStr) {

        FastDateFormat fastDateFormat = FastDateFormat.getInstance(DateTimeFormat.TIME_SIGN_FORMAT.value());
        Date date = new Date();
        try {
            date = fastDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 字符串转时间戳
     *
     * @param dateStr   数据日期
     * @param format    DateTimeFormat对象
     * @return          时间戳毫秒值
     */
    public static Long unixTimestamp(String dateStr, DateTimeFormat format) {

        FastDateFormat fastDateFormat = FastDateFormat.getInstance(format.value());
        Date date = new Date();
        try {
            date = fastDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 字符串转时间戳
     *
     * @param dateStr   数据日期
     * @param format    日期格式
     * @return          时间戳毫秒值
     */
    public static Long unixTimestamp(String dateStr, String format) {

        FastDateFormat fastDateFormat = FastDateFormat.getInstance(format);
        Date date = new Date();
        try {
            date = fastDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 测试主函数
     */
    public static void main(String[] args) {
        System.out.println(fromUnixtime(1575602732000L));
    }

}
