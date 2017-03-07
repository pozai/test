/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

import java.text.SimpleDateFormat;

/**
 * description:一些变量的常用操作
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 20112-7-10
 */
public class TypeUtil {

	public static boolean toBoolean(String str, boolean defvalue) {
		if (!StringUtil.hasText(str)) return defvalue;
		if (str.trim().compareToIgnoreCase("true") == 0) return true;
		else if (str.trim().compareToIgnoreCase("false") == 0) return false;
		return defvalue;
	}

	/** 转换为对应的CLASS */
	public static Class<?> toClass(String o) {
		try {
			return Class.forName(o);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回类，如果不存在用默认类替代
	 * 
	 * @param <T>
	 * @param o
	 * @param def
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> toClass(String o, Class<T> def) {
		try {
			return (Class<T>) Class.forName(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * string convert to class
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> toClass1(String o) {
		try {
			return (Class<T>) Class.forName(o);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * string convert to double
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static double toDouble(String str, double defval) {
		try {
			return Double.valueOf(str);
		} catch (Throwable e) {
			return defval;
		}
	}

	/**
	 * string convert to floats
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static float toFloat(String str, float defval) {
		try {
			return Float.valueOf(str);
		} catch (Throwable e) {
			return defval;
		}
	}

	/**
	 * string convert to int
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		if (str == null) return defValue;
		try {
			return Integer.valueOf(str);
		} catch (Throwable e) {
			return defValue;
		}
	}

	/**
	 * string covert to long
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static long toLong(String str, long defval) {
		try {
			return Long.valueOf(str);
		} catch (Throwable e) {
			return defval;
		}
	}

	/**
	 * string covert to short
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static short toShort(String str, short defval) {
		try {
			return Short.valueOf(str);
		} catch (Throwable e) {
			return defval;
		}
	}

	/**
	 * 将字符串的值转化为特定类型的值。
	 * 
	 * @param basic类型
	 * @param stringValue
	 * @return
	 */
	public static Object StringValueToTypeValue(Object stringValue, Class<?> basicType) throws Exception {
		Object result = null;
		String typeString = basicType.toString();
		if (stringValue == null) return null;
		if (typeString.indexOf("String") > -1) {
			result = stringValue.toString();
		}
		if (typeString.indexOf("Integer") > -1 || typeString.indexOf("int") > -1) {
			result = Integer.valueOf(stringValue == null ? "0" : stringValue.toString());
		}
		if (typeString.indexOf("Long") > -1 || typeString.indexOf("long") > -1) {
			result = Long.valueOf(stringValue == null ? "0" : stringValue.toString());
		}
		if (typeString.indexOf("Double") > -1 || typeString.indexOf("double") > -1) {
			result = Double.valueOf(stringValue == null ? "0.0" : stringValue.toString());
		}
		if (typeString.indexOf("Float") > -1 || typeString.indexOf("float") > -1) {
			result = Float.valueOf(stringValue == null ? "0.0" : stringValue.toString());
		}
		if (typeString.indexOf("Boolean") > -1 || typeString.indexOf("boolean") > -1) {
			result = Boolean.valueOf(stringValue.toString());
		}
		if (typeString.indexOf("Character") > -1 || typeString.indexOf("char") > -1) {
			result = (Character) stringValue;
		}
		if (typeString.indexOf("Date") > -1) {
			String dateStr = stringValue.toString();
			if (dateStr != null && !dateStr.equals("")) {
				if (dateStr.length() <= 10) {
					dateStr = dateStr + " 00:00:00";
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				} else {
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				}
			} else {
				result = null;
			}
		}
		return result;
	}

}
