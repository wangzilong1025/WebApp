package com.sandi.web.common.utils;

import com.sandi.web.common.persistence.dao.CommonDao;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具
 *
 * @version 1.0
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
    public static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMdd"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd HHmmss");
        }
        return formatDate;
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek(Date date) {
        return formatDate(date, "E");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 在一个时间上加上对应的年
     *
     * @param ti long
     * @param i  int
     * @return Date
     * @throws Exception
     */
    public static Date addYear(long ti, int i) throws Exception {
        if (i < 0) {
            throw new Exception("只能加上年");
        }
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.YEAR, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上对应的月份数
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addMonth(long ti, int i) throws Exception {
        if (i < 0) {
            throw new Exception("只能加上月份");
        }
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.MONTH, i);
        rtn = cal.getTime();
        return rtn;
    }

    /***
     * 在一个时间上加上或减去月份
     */
    public static Date addOrMinusMonth(long ti, int i) throws Exception {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.MONTH, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上或减去周
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addOrMinusWeek(long ti, int i) {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.WEEK_OF_YEAR, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上或减去天数
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addOrMinusDays(long ti, int i) {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.DAY_OF_MONTH, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上或减去小时
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addOrMinusHours(long ti, int i) {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.HOUR, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上或减去分钟
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addOrMinusMinutes(long ti, int i) {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.MINUTE, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 在一个时间上加上或减去秒数
     *
     * @param ti long
     * @param i  int
     * @return Date
     */
    public static Date addOrMinusSecond(long ti, int i) {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(GregorianCalendar.SECOND, i);
        rtn = cal.getTime();
        return rtn;
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获得时间的字符串
     *
     * @param ts Timestamp
     * @return String
     * @throws Exception
     */
    public static String getYYYYMMDDHHMMSS(Timestamp ts) throws Exception {
        if (ts == null) {
            return null;
        }
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateformat.format(ts);
        return str;
    }

    /**
     * 获得时间的字符串
     *
     * @param ts      Timestamp
     * @param pattern String
     * @return String
     * @throws Exception
     */
    public static String getYYYYMMDDHHMMSS(Timestamp ts, String pattern) throws Exception {
        if (ts == null) {
            return null;
        }
        DateFormat dateformat = new SimpleDateFormat(pattern);
        String str = dateformat.format(ts);
        return str;
    }

    /**
     * 根据时间串获得时间对象
     *
     * @param time    String
     * @param pattern String yyyy-MM-dd HH:mm:ss
     * @return Timestamp
     * @throws Exception
     */
    public static Timestamp getTimestampByYYYYMMDDHHMMSS(String time, String pattern) throws Exception {
        Timestamp rtn = null;
        DateFormat dateformat2 = new SimpleDateFormat(pattern);
        rtn = new Timestamp(dateformat2.parse(time.trim()).getTime());
        return rtn;
    }

    /**
     * date转化成Timestamp
     *
     * @param date Date
     * @return Timestamp
     */
    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 取下个月一号
     *
     * @return Timestamp
     * @throws Exception
     */
    public static Timestamp getNextMonthFirstDay() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);// 下个月
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 1号
        Timestamp timestamp1 = new Timestamp(calendar.getTimeInMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp timestamp2 = new Timestamp(df.parse(timestamp1.toString()).getTime());
        return timestamp2;
    }

    /**
     * 获取当月一号
     */
    public static Timestamp getCurMonthFirstDay() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);//1号
        Timestamp timestamp1 = new Timestamp(calendar.getTimeInMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp timestamp2 = new Timestamp(df.parse(timestamp1.toString()).getTime());
        return timestamp2;
    }

    /**
     * 取得指定日期后afterDays的日期
     *
     * @param date
     * @param afterDays
     * @return
     * @throws ParseException
     */
    public static Date getNextDate(Date date, int afterDays) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, afterDays);
        return c.getTime();
    }

    /**
     * 获得系统当前时间
     */
    public static Date getCurrentDate() throws Exception {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * 获取最大时间
     * */
    public static Date getMaxDate() throws Exception{
        String maxDate = "2099-12-31 23:59:59";
        return parseDate(maxDate);
    }

    /**
     * 返回当前时间的指定格式字符串
     */

    public static String getCurrentDateFormat(String format) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = getCurrentDate();
        return dateFormat.format(date);
    }

    /**
     * 获取制定格式的数据库时间串
     *
     * @param format
     * @return
     * @throws Exception
     */
    public static String getCurrentDateFromDB(String format) throws Exception {
        CommonDao dao = SpringContextHolder.getBean(CommonDao.class);
        Date returnDate = dao.getSysDate();
        String sysDate = "";
        if (returnDate != null) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            sysDate = dateFormat.format(returnDate);
        }
        return sysDate;
    }

    public static Timestamp getCurrentDateFromDB() throws Exception {
        CommonDao commonDao = SpringContextHolder.getBean(CommonDao.class);
        Date returnDate = commonDao.getSysDate();
        if (returnDate != null) {
            return dateToTimestamp(returnDate);
        }
        return dateToTimestamp(getCurrentDate());
    }

    /**
     * 获取日期之间的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDaysBetween(Calendar d1, Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR)
                - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取工作日
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getWorkingDay(Calendar d1, Calendar d2) {
        int result = -1;
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        // int betweendays = getDaysBetween(d1, d2);
        // int charge_date = 0;
        int charge_start_date = 0;// 开始日期的日期偏移量
        int charge_end_date = 0;// 结束日期的日期偏移量
        // 日期不在同一个日期内
        int stmp;
        int etmp;
        stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
        etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
        if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
            charge_start_date = stmp - 1;
        }
        if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
            charge_end_date = etmp - 1;
        }
        // }
        result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7) * 5 + charge_start_date - charge_end_date;
        return result;
    }

    public static String getChineseWeek(Calendar date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
    }

    /**
     * 获得日期的下一个星期一的日期
     *
     * @param date
     * @return
     */
    public static Calendar getNextMonday(Calendar date) {
        Calendar result = null;
        result = date;
        do {
            result = (Calendar) result.clone();
            result.add(Calendar.DATE, 1);
        } while (result.get(Calendar.DAY_OF_WEEK) != 2);
        return result;
    }

    /**
     * 获取休息日
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getHolidays(Calendar d1, Calendar d2) {
        return getDaysBetween(d1, d2) - getWorkingDay(d1, d2);
    }

    /**
     * 根据表达式获取下次生效时间
     * 表达式格式  * * * * * ? (秒 分 时 天 月 周)
     */
    public static Date getNextValidTimeAfter(Date date,String cronExpression){
        String[] temps = cronExpression.split(" ");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        if(temps.length>1){//秒
            String temp1 = temps[0];
            if(!StringUtils.equals("*",temp1)){
                cal.add(GregorianCalendar.SECOND, Integer.valueOf(temp1));
            }
        }
        if(temps.length>2){//分
            String temp1 = temps[1];
            if(!StringUtils.equals("*",temp1)){
                cal.add(GregorianCalendar.MINUTE,Integer.valueOf(temp1));
            }
        }
        if(temps.length>3){//时
            String temp1 = temps[2];
            if(!StringUtils.equals("*",temp1)){
                cal.add(GregorianCalendar.HOUR_OF_DAY,Integer.valueOf(temp1));
            }
        }
        if(temps.length>4){//天
            String temp1 = temps[3];
            if(!StringUtils.equals("*",temp1)){
                cal.add(GregorianCalendar.DAY_OF_MONTH,Integer.valueOf(temp1));
            }
        }
        if(temps.length>5){//月
            String temp1 = temps[4];
            if(!StringUtils.equals("*",temp1)){
                cal.add(GregorianCalendar.MONTH,Integer.valueOf(temp1));
            }
        }
        if(temps.length>=6){
            String temp1 = temps[5];
            if(!StringUtils.equals("?",temp1)){
                cal.add(GregorianCalendar.WEEK_OF_YEAR,Integer.valueOf(temp1));
            }
        }
        return cal.getTime();
    }
}

