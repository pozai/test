/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * description:时间的一些常用类操作
 * 
 * @author huaye
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public abstract class DateUtil {

	public static String default_date_format = "yyyy-MM-dd HH:mm:ss";
	
	// -------------------------------------------------------------- Constants

	// ----------------------------------------------------- Instance Variables

	// ------------------------------------------------------------ Constructor

	// --------------------------------------------------------- Public Methods

	
	/**
	 * 将Date类型转换为指定格式转换
	 * 
	 * @param date 时间
	 * @param format 时间格式
	 * @return
	 */
	public static String toStr(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(default_date_format);
			return dateFormat.format(date);
		} catch (Exception e) {
			return "1979-1-1";
		}
	}
	
	/**
	 * 将Date类型转换为指定格式转换
	 * 
	 * @param date 时间
	 * @param format 时间格式
	 * @return
	 */
	public static String toStr(Date date, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			return "1979-1-1";
		}
	}

	/**
	 * 将Date类型转换为指定格式转换
	 * 
	 * @param date 时间
	 * @param format 时间格式
	 * @param defval 默认
	 * @return
	 */
	public static String toStr(Date date, String format, String defval) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			return defval;
		}
	}

	/**
	 * 根据格式转换为Date类型
	 * 
	 * @param str 时间字符串
	 * @param format 时间格式
	 * @param defval 默认
	 * @return
	 */
	public static Date toDate(String str, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.parse(str);
		} catch (ParseException e) {
		    return null;
		}
		
	}

	/**
	 * 根据格式转换为Date类型
	 * 
	 * @param str 时间字符串
	 * @return
	 */
	public static Date toDate(String str) {
	    Date newDate = null;
	    newDate = toDate(str, "yyyy-MM-dd HH:mm:ss");
	    if(newDate == null){
	        newDate = toDate(str, "yyyy-MM-dd");
	    }
		return newDate;
	}
	
	/**
	 * 根据格式转换为Date类型
	 * 
	 * @param str 时间字符串
	 * @return
	 */
	public static Date formatDate(Date date,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return toDate(dateFormat.format(date), format);
	}
	
	/**
	 * 只有long的类型，才可以转换为任意时间格式
	 * 
	 * @param time 系统中一般都用long进行时间存储,为毫秒数
	 * @param format 时间格式
	 * @return
	 */
	public static String getFormatDate(long secondsTime, String format) {
		Date date = new Date(secondsTime * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 根据相应的格式时间转换为长整形
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static long getDateStringLong(String dateString, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(dateString);
			return (date.getTime()) / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return -1L;
	}
	
	/**
	 * 获取当日的秒数
	 * @param format
	 * @return
	 */
	public static long getCurrentSeconds(String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		Date date_;
		try {
			date_ = dateFormat.parse(dateStr);
			return date_.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1L;
	}
	
	/**
     * 将date时间转换为长整形
     * 
     * @param date
     * @return
     */
    public static long getDateLong(Date date) {
        if(date == null){
            return 0;
        }
        return (date.getTime()) / 1000;
    }

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前指定格式时间
	 * 
	 * @return
	 */
	public static String getCurrentFormatDate(String format) {
		Date date = new Date(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间秒数
	 * 
	 * @return
	 */
	public static long getCurrentDateSeconds() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取年数
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.YEAR);
	}

	/**
	 * 获取月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.MONTH);
	}

	/**
	 * 获取天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取小时数
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.HOUR);
	}
	
	/**
     * 获取当前时间的下一个小时
     * 
     * @param date
     * @return
     */
    public static Date getNextHour() {
        Date date = new Date();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, 1);    //得到下一个小时
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }
	
	/**
     * 获取指定时间的下一个小时
     * 
     * @param date
     * @return
     */
    public static Date getNextHour(Date date) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.HOUR, 1);    //得到下一个小时
        return ca.getTime();
    }

	/**
	 * 获取分数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(Calendar.SECOND);
	}
	
	/**
     * 获取间隔n天
     * 
     * @param date
     * @param n 
     * @return
     */
    public static Date getDate(Date date, int n) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.DATE, n);
        return ca.getTime();
    }
    
    /**
     * 获取间隔n天最早时间
     * 
     * @param date
     * @param n 
     * @return
     */
    public static Date getDateFirstSecond(Date date, int n) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.DATE, n);
        ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }
    
    /**
     * 获取间隔n天最早时间
     * 
     * @param date
     * @param n 
     * @return
     */
    public static Date getDateLastSecond(Date date, int n) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.DATE, n);
        ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
        return ca.getTime();
    }
	
	/**
     * 获取前一天
     * 
     * @param date
     * @return
     */
    public static Date getPreDate(Date date) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.DATE, -1);    //得到前一天
        return ca.getTime();
    }
    
    /**
     * 获取间隔n个月
     * 
     * @param date
     * @return
     */
    public static Date getMonth(Date date, int n) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.MONTH, n);
        return ca.getTime();
    }
    
    /**
     * 获取前一个月
     * 
     * @param date
     * @return
     */
    public static Date getPreMonth(Date date) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.MONTH, -1);    //得到前一个月
        return ca.getTime();
    }
    
    /**
     * 获取间隔n个年
     * 
     * @param date
     * @return
     */
    public static Date getYear(Date date, int n) {
        if (date == null) throw new java.lang.NullPointerException();
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.YEAR, n);
        return ca.getTime();
    }

	/**
	 * 获取周的一些操作
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeek(Date date) {
		if (date == null) throw new java.lang.NullPointerException();
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		return getWeek(ca.get(Calendar.DAY_OF_WEEK));
	}

	public static String getWeek(Integer id) {
		if (id == null) return "";
		for (Week week : Week.values()) {
			if (week.id.equals(id)) return week.getName();
		}
		return "";
	}

	/**
	 * 判断是否润年 详细设计：<br>
	 * 1.被400整除是闰年，否则：<br>
	 * 2.不能被4整除则不是闰年<br>
	 * 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
	 */
	public static boolean isLeapYear(int year) {

		if ((year % 400) == 0) return true;

		else if ((year % 4) == 0) {
			if ((year % 100) == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 通过传入年份和月份，计算某一月份的最大天数
	 */
	public static int geMaxDateofMonth(int month, int year) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year);
		time.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		return day;
	}

	// ------------------------------------------------------- Protected Methods

	// --------------------------------------------------------- Private Methods

	// @formatter:off
	/**
	 * enum 周
	 * 
	 * @author luoxj
	 */
	private static enum Week {
		SUNDAY(Calendar.SUNDAY, "星期日"),

		MONDAY(Calendar.MONDAY, "星期一"),

		TUESDAY(Calendar.TUESDAY, "星期二"),

		WEDNESDAY(Calendar.WEDNESDAY, "星期三"),

		THURSDAY(Calendar.THURSDAY, "星期四"),

		FRIDAY(Calendar.FRIDAY, "星期五"),

		SATURDAY(Calendar.SATURDAY, "星期六");

		private Integer	id;
		private String	name;

		Week(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
	
	/**
	 * 获取该天的最后一刻
	 * @return
	 */
	public static Date getLastSecondByDay(Date date){
		java.util.Calendar ca = java.util.Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		return ca.getTime();
	}
	
	/**
	 * 根据时间获取第二天的0点
	 * @return
	 */
	public static Date getNextDayFirstSecondByDay(Date date){
		java.util.Calendar ca = java.util.Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca.getTime();
	}
	
	
	// @formatter:on

	public static void main(String[] args) {
//		Date firstTime = DateUtil.toDate("2012-9-10 12:00:12");
//		Date newDay = DateUtil.formatDate(DateUtil.getNextDayFirstSecondByDay(firstTime),"yyyy-MM-dd");
//		Date taday = DateUtil.toDate(DateUtil.getCurrentFormatDate("yyyy-MM-dd"),"yyyy-MM-dd");
//		System.out.println(newDay.compareTo(taday));
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//	Date date = new Date();2012/12/23  2:13:11
//		System.out.println(getDateFirstSecond(new Date(), -10));
//		System.out.println(DateUtil.getFormatDate(1396337355, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.getDateStringLong("2014-03-31 23:59:59","yyyy-MM-dd HH:mm:ss"));
//	    Calendar cal = Calendar.getInstance();
//	    System.out.println(Calendar.MONDAY);
//		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//	    System.out.println((float)(Math.round(43.330002*100))/100);
		
		
//		System.out.println(DateUtil.getCurrentDateSeconds());
//		System.out.println(DateUtil.getDay(DateUtil.toDate("2012-08-31", "yyy-MM-dd")));
//		System.out.println(DateUtil.getPreDate(DateUtil.toDate("2012-08-01", "yyy-MM-dd")));
		
//		System.out.println(DateUtil.getDateStringLong("2013-11-28", "yyy-MM-dd"));
	}
}
